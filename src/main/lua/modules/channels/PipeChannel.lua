---------------------------------------------------------------------------------------
-- PipeChannel - wrapper for all windows named pipe stuf
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
--
---------------------------------------------------------------------------------------
local class = require "middleclass";
-- Lua to C interface (foreign function invocation)
local ffiLib = require "ffi";



-- Class declaration
local PipeChannel = class("PipeChannel");

    -- Static fields:

    -- Specifies pipe to be bi-directional. Both server and client processes can read from and write to pipe
    PipeChannel.static.PIPE_ACCESS_DUPLEX = 0x00000003;

    -- This flag forbids multiple instances of the pipe. If you try to create multiple instances of the pipe with this flag,
    -- creation of first instance succeeds, but creation of next instance fails with ERROR_ACCES_DENIDED
    PipeChannel.static.FILE_FLAG_FIRST_PIPE_INSTANCE = 0x00080000;

    -- This is very inportant flag!!!
    -- It helps to avoid deadlock while client and server read from or write to the same pipe simultaneously
    -- This flag enables overlapped mode for pipe:
    -- 1. Function performing read\write and connect operations, which may take a significant time to be completed, can return immediately
    -- 2. Thread that started operation to perform other operations while time-consuming operation exectutes in background:
    --    2.1. In overlapped mode thread can handle simultaneous IO operations on multiple instances of pipe
    --    2.2. In overlapped mode thread can perform simulteneous read\write operations on the same pipe handle
    -- 3. If the overlapped mode is not enabled, functions performing read, write and connect operations on the pipe handle
    --    do not return until the operation is finished
    PipeChannel.static.FILE_FLAG_OVERLAPPED = 0x40000000;

    -- Data is written to the pipe as steram of messages.
    -- Iportant!!!
    -- Pipe treats all bites, written during each write operation, as single message unit.
    -- This results in:
    -- The GetLastError() function reaturns ERROR_MORE_DATA when the messages not read completely.
    PipeChannel.static.PIPE_TYPE_MESSAGE = 0x00000004;

    -- Data is read from the pipe as stream of messages.
    PipeChannel.static.PIPE_READMODE_MESSAGE = 0x00000002;

    -- Connections from remote clients are automatically rejected
    PipeChannel.static.PIPE_REJECT_REMOTE_CLIENTS = 0x00000008;

    -- The max number of instances that can be created for this pipe
    PipeChannel.static.MAX_NUM_OF_INSTANCES = 1;

    -- The number of bytes to reserve for output buffer
    PipeChannel.static.OUT_BUFFER_SIZE = 10 * 1024 * 1024;

    -- The number of bytes to reserve for input buffer
    PipeChannel.static.IN_BUFFER_SIZE = 10 * 1024 * 1024;

    -- The default time-out in milliseconds. Zero means time-out in 50 ms.
    PipeChannel.static.DEFAULT_TIME_OUT = 0;

    -- The pointer to the structure, that specify security descripto for new named pipe and
    -- determine whether child process can inherite from return handle
    -- nil means defaults
    PipeChannel.static.SECURITTY_ATTRIBUTES = nil;

    -- Pipe error codes:
    PipeChannel.static.ERROR_PIPE_CONNECTED = 535;
    PipeChannel.static.STATUS_PENDING = 259;
    PipeChannel.static.ERROR_IO_PENDING = 997;
    PipeChannel.static.ERROR_BROKEN_PIPE = 109;


    -- Private fields:
    local _pipeName;
    local _logger;
    local _errorMessage;

    -- WinAPI declaration
    local _dllLibs;

    -- Descriptor for pipe
    local _pipeHandle;

    -- Local constant, that holds invalid value for pipe handle
    -- for comparison
    local INVALID_HANDLE_VALUE;

    -- OVERLAPPED structure
    local _lpOverlapped;

    -- Buffer for read \ write data
    local _buffer;
    local _bufferLength;
    local _countOfBytes;

    -- Suspend the termination of an asynchronous operation
    -- if server was stopped
    local _abortAsyncOperation;

    -- Does pipe have connected client
    local _isConnected;

    -- Class constructor:
    function PipeChannel : initialize(pipeName, logger)
        _pipeName = pipeName;
        _logger = logger;
        _abortAsyncOperation = false;
        _isConnected = false;
        _logger: debug("START INITIALIZE WINDOWS NAMED PIPE API");
        -- Init WinAPI
        _dllLibs = {};

        -- Load kernel dll
        _dllLibs.__cdecl = ffiLib.load("kernel32");

        -- Declare WinAPI's part, which allows you to work with pipes
        ffiLib.cdef[[
                        typedef unsigned long ULONG_PTR;
                        typedef unsigned long DWORD;
                        typedef DWORD * LPDWORD;
                        typedef void * PVOID;
                        typedef PVOID HANDLE;

                        typedef struct
                        {
                            ULONG_PTR Internal;
                            ULONG_PTR InternalHigh;
                            union
                            {
                                struct
                                {
                                      DWORD Offset;
                                      DWORD OffsetHigh;
                                };
                                PVOID Pointer;
                            };
                            HANDLE hEvent;
                        } OVERLAPPED;

                        int CreateNamedPipeA(const char *name, int openMode, int pipeMode, int maxInstances, int outBufferSize, int inBufferSize, int defTimeout, void *security);
                        int ConnectNamedPipe(HANDLE, OVERLAPPED*);
                        bool GetOverlappedResult(HANDLE hFile, OVERLAPPED * lpOverlapped, LPDWORD lpNumberOfBytesTransferred, bool bWait);
                        int FlushFileBuffers(HANDLE hFile);
                        int DisconnectNamedPipe(HANDLE);
                        int CloseHandle(HANDLE hObject);
                        int GetLastError();
                        int MessageBoxA(void *w, const char *txt, const char *cap, int type);
                        int WriteFile(HANDLE hFile, const char *lpBuffer, int nNumberOfBytesToWrite, int *lpNumberOfBytesWritten, OVERLAPPED* lpOverlapped);
                        int ReadFile(HANDLE hFile, PVOID lpBuffer, DWORD nNumberOfBytesToRead, LPDWORD lpNumberOfBytesRead, OVERLAPPED* lpOverlapped);
        ]];

        _lpOverlapped = ffiLib.new("OVERLAPPED[1]");
        INVALID_HANDLE_VALUE = ffiLib.cast("void*", -1);

        _buffer = ffiLib.new("char[?]", PipeChannel.IN_BUFFER_SIZE);
        _bufferLength = ffiLib.new("unsigned long[?]", 1);
        _countOfBytes = ffiLib.new("unsigned long[?]", 1);

        _logger: debug("END INITIALIZE WINDOWS NAMED PIPE API");
    end;

    -- Private methods:
    local _waitAsyncOperationEnd = function(self, overlappedStruct)
        _logger: debug("START WAITING FOR END OF ASYNC IO OPERATION!");
        while _abortAsyncOperation ~= true do
            if overlappedStruct[0].Internal ~= PipeChannel.STATUS_PENDING then
                break
            end;
        end;
        _logger: debug("END WAITING FOR END OF ASYNC IO OPERATION!");
    end;


    local _waitForClient = function(self)
        _logger: debug("PIPE CHANNEL " .._pipeName
                            .. "STATS TO WAIT FOR CONNECT REQUEST FROM CLIENT");
        local counter = 1;
        while _abortAsyncOperation ~= true do
            if _lpOverlapped[0].Internal ~= PipeChannel.STATUS_PENDING then
                break;
            end;
            if counter % 100000 == 0 then
                _logger: debug("WAITING FOR CONNECT REQUEST FROM CLIENT!");
            end;
            counter = counter + 1;
        end;

        if _abortAsyncOperation == true then
            _logger: debug("ABORTING ASYN WAIT FOR CLIENT "
                                    .. "R2QServer HAS BEEN INSTRUCTED TO SHUTDOWN "
                                                        .. "BEFORE SOMEONE CONNECTS");
            return false;
        else
            _logger: debug("ENDS ASYN WAIT FOR CLIENT BECAUSE "
                            .. "CLIENT HAS BEEN CONNECTED TO PIPE CHANNEL: " .. _pipeName);
            return true;
        end;
    end;

    -- Public methods:

    -- Signals to abort asyn operations within pipe
    function PipeChannel : abortAsync()
        _abortAsyncOperation = true;
    end;


    -- Create pipe instance
    function PipeChannel : open()
        _logger: debug("TRYING TO OPEN WINDOWS NAMED PIPE: " .. _pipeName);
        local pipeOpenMode = PipeChannel.PIPE_ACCESS_DUPLEX
                                + PipeChannel.FILE_FLAG_OVERLAPPED
                                    + PipeChannel.FILE_FLAG_FIRST_PIPE_INSTANCE;
        local pipeMode = PipeChannel.PIPE_TYPE_MESSAGE
                            + PipeChannel.PIPE_READMODE_MESSAGE
                                + PipeChannel.PIPE_REJECT_REMOTE_CLIENTS;

        _pipeHandle = ffiLib.C.CreateNamedPipeA(_pipeName,
                                                pipeOpenMode,
                                                pipeMode,
                                                PipeChannel.MAX_NUM_OF_INSTANCES,
                                                PipeChannel.OUT_BUFFER_SIZE,
                                                PipeChannel.IN_BUFFER_SIZE,
                                                PipeChannel.DEFAULT_TIME_OUT,
                                                PipeChannel.SECURITTY_ATTRIBUTES);
        if _pipeHandle == INVALID_HANDLE_VALUE then
            _logger: debug("CALL OF CreateNamedPipeA() FAILED WITH ERRORE CODE: " .. ffiLib.C.GetLastError());
            return false;
        else
            _logger: debug("SUCCESSFULLY OPENED WINDOWS NAMED PIPE: " .. _pipeName);
            return true;
        end;
    end;


    -- Destroy pipe instance and free allocated resources
    function PipeChannel : close()
        _logger: debug("TRYING CLOSE WINDOWS NAMED PIPE INSTANCE: " .. _pipeName);
        if _pipeHandle ~= INVALID_HANDLE_VALUE then
            local result = ffiLib.C.CloseHandle(_pipeHandle);
            if result == 0 then
                _logger: debug("CALL OF CloseHandle(...) FAILED WITH ERRORE CODE: "
                                                            .. ffiLib.C.GetLastError());
                return false;
            else
                _logger: debug("WINDOWS NAMED PIPE INSTANCE: "
                                .. _pipeName .. " HAS BEEN SUCCESSFULLY CLOSED!");
                return true;
            end;
        else
            _logger: debug("PIPE HANDLE IN INVALID! "
                                    .. "PIPE HAS TO BE OPENED FIRST!");
            return false;
        end;
    end;


    -- Run a wait loop for the incoming client
    -- and return true if someone connects to the pipe
    function PipeChannel : connect()
        _logger: debug("OPEN WINDOWS NAMED PIPE: " .. _pipeName .. " FOR INCOMING CONNECTION");
        local result = ffiLib.C.ConnectNamedPipe(_pipeHandle, _lpOverlapped);
        local lastError = ffiLib.C.GetLastError();

        -- Immediate result other than zero means successfull connect
        if result ~= 0 then
            _logger: debug("CALL OF ConnectNamedPipe(...) RETURNS NON ZERO IMMEDIATE RESULT! "
                                    .. "CLIENT SUCCESSFULLY CONNECTED TO PIPE: " .. _pipeName .. "!");
            _isConnected = true;
            return _isConnected;
        else
            -- Zero immediate result means that we have troubles with pipe's descriptor
            -- and we need to analize GetLastError() result.
            if lastError == PipeChannel.ERROR_IO_PENDING then
                -- According to https://msdn.microsoft.com/en-us/library/aa365603(v=VS.85).aspx:
                -- This error means that async IO operation is still in progress and we need to wait until someone connect
                _logger: debug("CALL OF ConnectNamedPipe(...) FAILED WITH ERROR_IO_PENDING");
                _abortAsyncOperation = false;
                return _waitForClient(self);
            elseif lastError == PipeChannel.ERROR_PIPE_CONNECTED then
                -- This means that client is already connected
                _logger: debug("CALL OF ConnectNamedPipe(...) FAILED WITH ERROR_PIPE_CONNECTED");
                _isConnected = true;
                return _isConnected;
            else
                _logger: debug("CALL OF ConnectNamedPipe(...) FAILED WITH ERRORE CODE: " .. lastError);
                _isConnected = false;
                return _isConnected;
            end;

        end;
    end;

    -- Flushes pipe's buffer and clear pipe's handle for new client
    function PipeChannel : disconnet()
        _logger: debug("DISCONNECTING CURRENT CLIENT FROM SERVER-END OF THE PIPE: " .. _pipeName);
        _isConnected = false;
        ffiLib.C.FlushFileBuffers(_pipeHandle);
        ffiLib.C.DisconnectNamedPipe(_pipeHandle);
    end;


    -- Read from windows named pipe
    function PipeChannel : readMessage()
        _logger: debug("START SYNC READ IO OPERATION ON PIPE " .. _pipeName);
        local request;
        local result = ffiLib.C.ReadFile(_pipeHandle, _buffer, PipeChannel.IN_BUFFER_SIZE, _countOfBytes, nil);
        local error = ffiLib.C.GetLastError();

        if result == 0 and _countOfBytes[0] == 0  then
            if error == PipeChannel.ERROR_BROKEN_PIPE then
                _logger: debug("SYNC READ IO OPERATION FAILED WITH ERROR_BROKEN_PIPE!"
                                            .. "CLIENT CLOSE HIS PIPE-END FOR SOME REASON! "
                                                .. "DISCONNECTING CLIENT FROM SERVER-END OF THE PIPE: "
                                                                                                .. _pipeName);
                request = "CLIENT_OFF";
            else
                _logger: debug("SYNC READ IO OPERATION FAILED WITH ERROR: " .. error);
            end;
            return request;
        else
            request = ffiLib.string(_buffer);
        end;
        _logger: debug("END SYNC READ IO OPERATION ON PIPE " .. _pipeName);
        return request;
    end;


    -- Write from windows named pipe
    function PipeChannel : writeMessage(message)
        _logger: debug("STARTING ASYNC WRITE IO OPERATION");

        local result;
        local overlappedStruct = ffiLib.new("OVERLAPPED[1]");

        local result = ffiLib.C.WriteFile(_pipeHandle, message, string.len(message), _bufferLength, overlappedStruct);
        local error = ffiLib.C.GetLastError();

        if result == 0 then
            if error == PipeChannel.ERROR_IO_PENDING then
                _logger: debug("CALL OF WriteFile(...) FAILED WITH ERROR_IO_PENDING");
                _abortAsyncOperation = false;
                _waitAsyncOperationEnd(self, overlappedStruct);
            elseif error == PipeChannel.ERROR_BROKEN_PIPE then
                _logger: debug("ASYNC WRITE IO OPERATION FAILED WITH ERROR_BROKEN_PIPE!"
                                            .. "CLIENT CLOSE HIS PIPE-END FOR SOME REASON! "
                                                .. "DISCONNECTING CLIENT FROM SERVER-END OF THE PIPE: "
                                                                                            .. _pipeName);
                result = "CLIENT_OFF";
            else
                _logger: debug("ASYNC WRITE FAILED WITH ERROR CODE: " .. error);
            end;
        end;

        _logger: debug("END ASYNC WRITE IO OPERATION");
        ffiLib.C.FlushFileBuffers(_pipeHandle);
        return result;
    end;

    function PipeChannel : isConnected()
        return _isConnected;
    end;

-- End of class declaration
return PipeChannel;