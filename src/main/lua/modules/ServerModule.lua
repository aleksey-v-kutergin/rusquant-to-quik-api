---------------------------------------------------------------------------------------
-- ServerModule.lua
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
-- Date: 10.08.2016
---------------------------------------------------------------------------------------
-- This module contains all pure server logic like connect, disconnect, sendMessage,
-- receiveMessage and so on without QUIK-specific code
---------------------------------------------------------------------------------------

-- @module src.modules.ServerModule
local ServerModule = {}; -- public interface


---------------------------------------------------------------------------------------
-- REQUIRE section
--
---------------------------------------------------------------------------------------

local jsonParser      = assert( require "modules.JSON" );
local logManager      = assert( require "modules.LogManager" );
local requestManager  = assert( require "modules.RequestManager" );
local cacheManager    = assert( require "modules.CacheManager" );
local quikDataManager = assert( require "modules.QuikDataManager" );

---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------
-- Definitions for local variables of the module
---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------

-- Important!
-- A word of advice: \\ , \p and \t are going to get interpreted as escape characters. Either switch to forward slashes or double-backslash to "escape the escapes": "\\\\.\\pipe\\test"
local PIPE_NAME = "\\\\.\\pipe\\RusquantToQuikPipe";

---------------------------------------------------------------------------------------
-- Pipe open modes
---------------------------------------------------------------------------------------


-- Specifies pipe to be bi-directional. Both server and client processes can read from and write to pipe
local PIPE_ACCESS_DUPLEX = 0x00000003;

-- This flag forbids multiple instances of the pipe. If you try to create multiple instances of the pipe with this flag,
-- creation of first instance succeeds, but creation of next instance fails with ERROR_ACCES_DENIDED
local FILE_FLAG_FIRST_PIPE_INSTANCE = 0x00080000;

-- This is very inportant flag!!!
-- It healps to avoid deadlock while client and server read from  or write to the same pipe simultaneously
-- This flag enebles overlapped mode for pipe:
-- 1. Fundtion performing read\write and connect operations, which may take a significant time to be completed, can return immediately
-- 2. Thread that started operation to perform other operations while time-consuming operation exectutes in background:
--    2.1. In overlapped mode thread can handle simultaneous IO operations on multiple instances of pipe
--    2.2. In overlapped mode thread can perform simulteneous read\write operations on the same pipe handle
-- 3. If the overlapped mode is not enabled, functions performing read, write and connect operations on the pipe handle do not return until the operation is finished
local FILE_FLAG_OVERLAPPED = 0x40000000;

-- Data is written to the pipe as steram of messages.
-- Iportant!!!
-- Pipe treats all bites, written during each write operation, as single message unit.
-- This results in:
-- The GetLastError() function reaturns ERROR_MORE_DATA when the messages not read completely.
local PIPE_TYPE_MESSAGE = 0x00000004;

-- Data is read from the pipe as stream of messages.
local PIPE_READMODE_MESSAGE = 0x00000002;

-- Connections from remote clients are automatically rejected
local PIPE_REJECT_REMOTE_CLIENTS = 0x00000008;


---------------------------------------------------------------------------------------
-- Other CreateNamedPipe params
---------------------------------------------------------------------------------------

-- The max number of instances that can be created for this pipe
local MAX_NUM_OF_INSTANCES = 1;

-- The number of bytes to reserve for output buffer
local OUT_BUFFER_SIZE = 512 * 1024;

-- The number of bytes to reserve for input buffer
local IN_BUFFER_SIZE = 512 * 1024;

-- The default time-out in milliseconds. Zero means time-out in 50 ms.
local DEFAULT_TIME_OUT = 0;

-- The pointer to the structure, that specify security descripto for new named pipe and determine whether child process can inherite from return handle
-- nil means defaults
local SCURITTY_ATTRIBUTES; -- = nil



---------------------------------------------------------------------------------------
-- Utility variables
---------------------------------------------------------------------------------------

-- Lua to C interface
local ffiLib = assert( require "ffi" );

-- Access to WinAPI
local dllLibs = {};

-- Storing descriptor for pipe
local pipeHandle;

-- OVERLAPPED structure
local lpOverlapped;

-- Buffer for incoming data
local readBuffer = ffiLib.new("char[?]", IN_BUFFER_SIZE);
local readBufferLength = ffiLib.new("unsigned long[?]", 1);
local countOfBytesRead = ffiLib.new("unsigned long[?]", 1); -- lua from C type's conversion specific

-- Server state flags
local isServerInitSuccess = false;
local isServerConnected = false;
local isServerStoped = false;

-- txt file for temporal form of logging process
local serverLogFile;

-- Server switches between three modes:
-- Mode 0 - Server waits for incoming client's connections
-- Mode 1 - Server wiats for a incoming requests from client
-- Mode 2 - Server process request and writes corresponding response
local serverMode = 0;

-- Incoming request and outcoming response. Used in server's main loop.
local clientRequest;
local serverResponse;

---------------------------------------------------------------------------------------
-- Pipe ERRORS and ERROR CODES
---------------------------------------------------------------------------------------

local INVALID_HANDLE_VALUE = assert( ffiLib.cast("void*", -1) );
local ERROR_PIPE_CONNECTED = 535;
local STATUS_PENDING = 259;
local ERROR_IO_PENDING = 997;
local ERROR_BROKEN_PIPE = 109;



---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------
-- Definitions for all private functions of the module
---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------


---------------------------------------------------------------------------------------
-- Create Windows Named Pipe with given name
-- @return:
-- true - if success
-- false - if fail
---------------------------------------------------------------------------------------
local function createPipe()

    logManager.writeToLog(this, "SERVER TRYING TO CREATE PIPE HANDLE");

    local pipeOpenMode = PIPE_ACCESS_DUPLEX + FILE_FLAG_OVERLAPPED + FILE_FLAG_FIRST_PIPE_INSTANCE;
    local pipeMode = PIPE_TYPE_MESSAGE + PIPE_READMODE_MESSAGE + PIPE_REJECT_REMOTE_CLIENTS;

    pipeHandle = assert( ffiLib.C.CreateNamedPipeA(PIPE_NAME, pipeOpenMode, pipeMode, MAX_NUM_OF_INSTANCES, OUT_BUFFER_SIZE, IN_BUFFER_SIZE, DEFAULT_TIME_OUT, SCURITTY_ATTRIBUTES) );
    if pipeHandle == INVALID_HANDLE_VALUE then
        logManager.writeToLog(this, "CALL OF CreateNamedPipeA() FAILED WITH ERRORE CODE: " .. ffiLib.C.GetLastError());
        return false;
    else
        return true;
    end;
end;


---------------------------------------------------------------------------------------
-- Closes pipe handle
-- @return:
-- 0 - If the function fails
-- non-zero value - If the function succeeds
---------------------------------------------------------------------------------------
local function closeHandle()
    logManager.writeToLog(this, "WITHIN closeHandle()!");
    if pipeHandle ~= INVALID_HANDLE_VALUE then

        local result = ffiLib.C.CloseHandle(pipeHandle);
        if result == 0 then
            logManager.writeToLog(this, "CALL OF CloseHandle(pipeHandle) FAILED WITH ERRORE CODE: " .. ffiLib.C.GetLastError());
            return false;
        else
            logManager.writeToLog(this, "SERVER CLOSED PIPE HANDLE SUCCESSFULLY");
            return true;
        end;

    end;

end;



---------------------------------------------------------------------------------------
-- Waits FOREVER for incomming client connection
-- So the server can be stoped only by triggering of OnStop() event in QUIK or aborting  the script execution
--
---------------------------------------------------------------------------------------
local function waitForClientConnection()

    logManager.writeToLog(this, "SERVER WAITS FOR SOMEONE CONNECTED");
    while isServerStoped ~= true do
          if lpOverlapped[0].Internal ~= STATUS_PENDING then
                break;
          end;
    end;

    if isServerStoped == true then
        logManager.writeToLog(this, "SERVER WAS STOPPED BEFORE SOMEONE CONNECTS");
        return false;
    end;

    logManager.writeToLog(this, "SOMEONE CONNECTED TO SERVER");
    return true;

end;



---------------------------------------------------------------------------------------
-- Check the Client to Server connection after the status of asynchroniouse connect operation changed from STATUS_PENDING
-- According to Microsoft docs (See last sentense of the last paragraf of the Remarks section), the good connction between Server and Client process
-- only exits if the second call of ConnectNamedPipe() for
-- @return:
-- true - if connection is OK
-- false - else
---------------------------------------------------------------------------------------
--[[local function checkConnection()

    -- According to docs, a new instance of OVERLAPPED structure has to be used for each asynchroniouse call
    local overaptStruct = ffiLib.new("OVERLAPPED");

    local result = ffiLib.C.ConnectNamedPipe(pipeHandle, overaptStruct);
    local error = ffiLib.C.GetLastError();

    if result == 0 and error == ERROR_PIPE_CONNECTED then
        return true;
    else
        return false;
    end;
end;]]



---------------------------------------------------------------------------------------
-- Reads bytes from buffer and transforms them to lua string
--
---------------------------------------------------------------------------------------
local function readRequestFromBuffer(buffer, length)

    local request;
    if length > 0 then
        request = ffiLib.string(buffer);
    end;

    return request;

end;



---------------------------------------------------------------------------------------
-- Wiats for the end of the asynchroniouse operation
--
---------------------------------------------------------------------------------------
local function waitForOperationEnd(overlappedStruct)

    while isServerStoped ~= true do
        if overlappedStruct[0].Internal ~= STATUS_PENDING then break end;
    end;

end;



---------------------------------------------------------------------------------------
-- Reads message from pipe client
--
---------------------------------------------------------------------------------------
local function readMessage()

    --logManager.writeToLog(this, "STARTING SYNC READ IO OPERATION");

    local request;
    local result = ffiLib.C.ReadFile(pipeHandle, readBuffer, IN_BUFFER_SIZE, countOfBytesRead, nil);
    local error = ffiLib.C.GetLastError();

    if result == 0 and countOfBytesRead[0] == 0  then
        --logManager.writeToLog(this, "SYNC READ IO OPERATION FAILED WITH ERROR: " .. error);
        if error == ERROR_BROKEN_PIPE then
            request = "CLIENT_OFF";
        end;
        return request;
    else
        request = ffiLib.string(readBuffer);
    end;

    --logManager.writeToLog(this, "END SYNC READ IO OPERATION");
    return request;

end;



---------------------------------------------------------------------------------------
-- Writes message to pipe client
--
---------------------------------------------------------------------------------------
local function writeMessage(response)

    --logManager.writeToLog(this, "STARTING ASYNC WRITE IO OPERATION");
    local result;
    local overlappedStruct = ffiLib.new("OVERLAPPED[1]");
    local result = ffiLib.C.WriteFile(pipeHandle, response, string.len(response), readBufferLength, overlappedStruct);
    local error = ffiLib.C.GetLastError();

    if result == 0 then

        if error == ERROR_IO_PENDING then
            waitForOperationEnd(overlappedStruct);
        elseif error == ERROR_BROKEN_PIPE then
            result = "CLIENT_OFF";
        else
            logManager.writeToLog(this, "ASYNC WRITE FAILED WITH ERROR CODE: " .. error);
        end;

    end;
    ffiLib.C.FlushFileBuffers(pipeHandle);
    return result;
    --logManager.writeToLog(this, "WRITE RESPONSE WITH LENGTH: " .. string.len(response) .. " readBufferLength: " .. readBufferLength[0]);
    --logManager.writeToLog(this, "END ASYNC WRITE IO OPERATION");

end;



---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------
-- Definitions for all public functions of the module
---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------



---------------------------------------------------------------------------------------
-- Init all server internals
-- __cdecl - it is just calling convention for C-language
-- __cdecl mneas, that args of function are pushed into the stack in reverse order and call stack is cleaned by caller
---------------------------------------------------------------------------------------
function ServerModule : init()

    logManager.createLog(this, getScriptPath());
    logManager.writeToLog(this, "START EXECUTE SERVER INITIALIZATION");

    dllLibs.__cdecl = assert(ffiLib.load("kernel32"));

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

    lpOverlapped = ffiLib.new("OVERLAPPED[1]");
    isServerInitSuccess = createPipe();
    logManager.writeToLog(this, "SUCCESSFULLY INIT PIPE!");

    logManager.writeToLog(this, "TRYING TO SET LOGGER TO MODULES");
    cacheManager.setLogger(this, logManager);
    quikDataManager.setLogger(this, logManager);
    requestManager.setLogger(this, logManager);

    logManager.writeToLog(this, "TRYING TO SET JSON PARSER TO MODULES");
    quikDataManager.setJsonParser(this, jsonParser);
    requestManager.setJsonParser(this, jsonParser);
    cacheManager.setJsonParser(this, jsonParser);

    logManager.writeToLog(this, "TRYING TO SET DATA MANAGER TO MODULES");
    requestManager.setDataManager(this, quikDataManager);

    logManager.writeToLog(this, "TRYING TO SET CACHE MANAGER TO MODULES");
    quikDataManager.setCacheManager(this, cacheManager);

    logManager.writeToLog(this, "SUCCESSFULLY DEPENDENCIES FOR MODULES! END SERVER INITIALIZATION");

end;



---------------------------------------------------------------------------------------
-- Waits for client to connect
--
---------------------------------------------------------------------------------------
function ServerModule : connect()

        local result = ffiLib.C.ConnectNamedPipe(pipeHandle, lpOverlapped);
        local lastError = ffiLib.C.GetLastError();
        if result ~= 0 then
            isServerConnected = true;
        else

            -- This means that we have troubles with pipe's descriptor and we need to analize GetLastError() result.
            if lastError == ERROR_IO_PENDING then

                -- According to https://msdn.microsoft.com/en-us/library/aa365603(v=VS.85).aspx:
                -- This error means that IO operation is still in progress and we need to wait until someone connect
                logManager.writeToLog(this, "CALL OF ConnectNamedPipe(pipeHandle, lpOverlapped) FAILED WITH ERROR_IO_PENDING");
                isServerConnected = waitForClientConnection(); -- checkConnection();

            elseif lastError == ERROR_PIPE_CONNECTED then

                -- This means that client is already connected
                logManager.writeToLog(this, "CALL OF ConnectNamedPipe(pipeHandle, lpOverlapped) FAILED WITH ERROR_PIPE_CONNECTED");
                isServerConnected = true;

            else
                logManager.writeToLog(this, "CALL OF ConnectNamedPipe(pipeHandle, lpOverlapped) FAILED WITH ERRORE CODE: " .. lastError);
                isServerConnected = false;
            end;

        end;

end;



---------------------------------------------------------------------------------------
-- Breaks the connection with pipe
-- @return:
-- true - if success
-- false - if fail
---------------------------------------------------------------------------------------
function ServerModule : disconnet()

    logManager.writeToLog(this, "CLIENT IS DICONNECTED FROM PIPE!");
    isServerConnected = false;
    ffiLib.C.FlushFileBuffers(pipeHandle);
    ffiLib.C.DisconnectNamedPipe(pipeHandle);

end;




---------------------------------------------------------------------------------------
-- Executes server main loop
--
---------------------------------------------------------------------------------------
function ServerModule : run()
    if isServerInitSuccess == true then

        while isServerStoped ~= true do

            if serverMode == 0 then

                ServerModule.connect();
                if isServerConnected == true then
                    serverMode = 1;
                end;

            elseif serverMode == 1 then

                local request = readMessage();
                if request ~= nil then
                    if request == "CLIENT_OFF" then
                        ServerModule.disconnet();
                        serverMode = 0;
                    else
                        clientRequest = request;
                        serverMode = 2;
                    end;
                end;

            elseif serverMode == 2 then

                local response = requestManager.processRequest(self, clientRequest);
                local result = writeMessage(response);
                if result == "CLIENT_OFF" then
                    ServerModule.disconnet();
                    serverMode = 0;
                else
                    serverMode = 1;
                end;

            end;

            sleep(1);

        end;

        closeHandle();
        logManager.closeLog();

    end;
end;


---------------------------------------------------------------------------------------
-- Shutdowns the server
--
-- ACTUNG!
-- It seems that QUIK allocates 5 sec. life-time preriod for main thread
-- of the executing script after OnStop() callback has been called.
-- When that period ends, it seems that QUIK terminates execution of script immediately.
--
-- Examle:
-- 1. The is no connected client. In this case ConnectNamedPipe() executes async and waits
--    for incuming connections by call of waitForClientConnection(), which executes while loop within.
--    OnStop() callback sets isServerStoped to true and brakes execution of waitForClientConnection().
--    Then main server loop breaks its execution and call closeHandle() to free resources.
--
-- 2. If there is connected client, server switches to read mode and call ReadFile() in blocking mode.
--    So, call of ReadFile() blocks execution of main thread untile some data arraives.
--    Then, if user stops server script, the OnStop() callback executes in separate thread and sets
--    isServerStoped to true, but this has no effect at blocked main thread since it'is still within readMessage() call.
--    So, when 5 sec. time-out expired, QUIK terminates execution of the script and pipe's handle does not close propertly.
--    To avoid this, one need to call closeHandle() within OnStop() callback call if server is in read mode.
--
-- 3. There is no such effect at write mode since WriteFile() executes async.
--
---------------------------------------------------------------------------------------
function ServerModule : stop()

    logManager.writeToLog(this, "OnStop() CALLBACK HAS BEEN CALLED!");
    isServerStoped = true;

    if serverMode == 1 then
        closeHandle();
        logManager.closeLog();
    end;

end;



---------------------------------------------------------------------------------------
-- Access methods
--
---------------------------------------------------------------------------------------

function ServerModule : isServerInitSuccess()
    return isServerInitSuccess;
end;


function ServerModule : isSeverConnected()
    return isServerConnected;
end


function ServerModule : isServerStoped()
    return isServerStoped;
end


function ServerModule : setQUIKToBrokerConnectionFlag(isConnected)
    if isConnected == true then
        logManager.writeToLog(this, "OnConnected() CALLBACK HAS BEEN CALLED!");
    else
        logManager.writeToLog(this, "OnDisconnected() CALLBACK HAS BEEN CALLED!");
    end;
    requestManager.setQUIKToBrokerConnectionFlag(this, isConnected);
end;



---------------------------------------------------------------------------------------
-- For caching objects
--
---------------------------------------------------------------------------------------

function ServerModule : cacheTransReplay(transReplay)
    logManager.writeToLog(this, "OnTransReplay() CALLBACK HAS BEEN CALLED!");
    cacheManager.cache(this, "TRANS_REPLAY", transReplay);
end;


function ServerModule : cacheOrder(order)
    logManager.writeToLog(this, "OnOrder() CALLBACK HAS BEEN CALLED!");
    cacheManager.cache(this, "ORDER", order);
end;


function ServerModule : cacheTrade(trade)
    logManager.writeToLog(this, "OnеTrade() CALLBACK HAS BEEN CALLED!");
    cacheManager.cache(this, "TRADE", trade);
end;















-- Async read failed to read somethig for some reason
--[[
local function readMessage()

    logManager.writeToLog(this, "STARTING ASYNC READ IO OPERATION");

    local request;
    local overlappedStruct = ffiLib.new("OVERLAPPED[1]");
    local result = ffiLib.C.ReadFile(pipeHandle, readBuffer, IN_BUFFER_SIZE, countOfBytesRead, overlappedStruct);
    local error = ffiLib.C.GetLastError();
    if result ~= 0 then

        request = readRequestFromBuffer(readBuffer, countOfBytesRead[0]);
        --ffiLib.C.FlushFileBuffers(pipeHandle);
        logManager.writeToLog(this, "ASYNC READ IO RETURNS IMMEDIATE.");

    else

        if error == ERROR_IO_PENDING then

            logManager.writeToLog(this, "ASYNC READ IO RETURNS ERROR_IO_PENDING. WAITING...");
            waitForOperationEnd(overlappedStruct);
            request = readRequestFromBuffer(readBuffer, countOfBytesRead[0]);
            --ffiLib.C.FlushFileBuffers(pipeHandle);

        elseif error == 109 then

            request = "CLIENT_OFF";

        else

            -- read operation failed for some reason
            logManager.writeToLog(this, "ASYNC READ IO OPERATION FAILED WITH ERRORE CODE: " .. error);

        end;

    end;

    logManager.writeToLog(this, "END ASYNC READ IO OPERATION");

    return request;

end;
]]



-- End of the module
return ServerModule;