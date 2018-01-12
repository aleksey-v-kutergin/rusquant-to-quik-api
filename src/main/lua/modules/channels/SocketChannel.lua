---------------------------------------------------------------------------------------
-- SocketChannel - wrapper for all socets stuf
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
--
---------------------------------------------------------------------------------------
local class = require "middleclass";


-- ACTUNG!!!
-- If you encounter a bug: "could not find modue [socket.core]... bla bla..." then
-- no panic! This means that core.dll under socket folder cannot find lua51.dll!
-- For some hell reason core.dll require lua51.dll! NOT lua5.1.dll, distributed
-- with QUIK terminal! Fucking hell!
-- Also, lua51.dll, distributed with project may not be compatible with lua build
-- used by your version of the quik terminal.
-- To fix these issues:
-- 1. Make copy of lua5.1.dll from your Quik terminal distribution.
-- 2. Rename file to lua51.dll
-- 3. Store it in the same folder with info.exe or somewhere else (adjust package.cpath in this case)
-- Thanks for the solution: https://forum.quik.ru/forum10/topic1097/ - postpositions post
local socket = require "socket";

-- Custom dependencies:
local DataTransferChannel = require "modules.channels.DataTransferChannel";

-- Class declaration
local SocketChannel = class("SocketChannel", DataTransferChannel);

    -- Static fields:
    SocketChannel.static.ACCEPT_TIMEOUT = 5;
    SocketChannel.static.RECEIVE_TIMEOUT = 5;

    -- Private fields:
    local _host;
    local _port;
    local _serverSocket;
    local _client;
    local _logger;
    --local _isConnected;

    -- Suspend the termination of an asynchronous operation
    -- if server was stopped
    --local _abortAsyncOperation;

    -- Class constructor:
    function SocketChannel : initialize(host, port, logger)
        -- Call base class constructor
        DataTransferChannel.initialize(self);
        _host = host;
        _port = port;
        _logger = logger;
        _logger: debug("INITIALIZE SOCKET CHANNEL AT HOST: "
                                        .. host .. " AND PORT: " ..port);
    end;

    -- Public methods:

    -- Create pipe instance
    function SocketChannel : open()
        -- Creates and returns a TCP server object bound to a local address and port,
        -- ready to accept client connections
        local error;
        _serverSocket, error = socket.bind(_host, _port, 1);
        if _serverSocket ~= nil then
            local ip, p = _serverSocket: getsockname()
            _logger: debug("SUCCESSFULLY BIND SOKET AT HOST: "
                                            .. ip .. " AND PORT: " .. p);
            return true;
        else
            _logger: debug("FAILED TO BIND SOKET AT HOST: "
                        .. _host .. " AND PORT: " .. _port .. " WITH ERROR" .. error);
            return false;
        end;
    end;


    function SocketChannel : close()
        if _serverSocket ~= nil then
            _logger: debug("CLOSING SOKET AT HOST: "
                                        .. _host .. " AND PORT: " .. _port);
            _serverSocket: close();
        else
            _logger: debug("SOCKET HAS NOT YET BEEN OPENED!");
        end;
    end;


    -- ACTUNG!!!
    -- Call of _serverSocket: accept() blocks the thread
    -- until a request from the client is received.
    -- This leads to the fact that when you try to stop the server script (OnStop and OnClose event),
    -- the execution thread is blocked and there is no normal completion and release of resources.
    -- Call settimeout(...) before accept() allows to unblock thread if no clients comes in 2 seconds.
    -- accept() returns nil, timeout in this case
    function SocketChannel : connect()
        _logger: debug("OPEN SERVER SOCKET FOR ACCEPTING INCOMING CLIENTS");
        DataTransferChannel.setConnected(self, false);
        DataTransferChannel.resetAbortAsync(self);

        local status;
        local error;
        while DataTransferChannel.isAsyncAborted(self) ~= true do
            _serverSocket: settimeout(SocketChannel.ACCEPT_TIMEOUT);
            _client, error = _serverSocket: accept();
            if _client ~= nil then
                _logger: debug("SUCCESSFULLY ACCEPT CLIENT ON SERVER!");
                DataTransferChannel.setConnected(self, true);
                return true;
            else
                _logger: debug("WAITING FOR CLIENT REQUEST...");
            end;
        end;
        return false;
    end;


    function SocketChannel : disconnet()
        if DataTransferChannel.isConnected() == true then
            _logger: debug("DISCONNECTING CLIENT FROM SERVER");
            _client: close();
            _client = nil;
            DataTransferChannel.setConnected(self, false);
        else
            _logger: debug("THERE IS NO ACCEPTED CLIENT YET! WAIT FOR CLIENT FIRST!");
        end;
    end;


    -- receive() reads data from a client object, according to the specified read pattern
    -- Used pattern: *l - reads a line of text from the socket. This id default pattern
    -- In order to finish read transaction, message must have \n in the end!
    -- In case of error, the method returns nil followed by an error message
    -- If client close connection receive() returns nil, closed
    -- Call of receive() also blocks thread! Fix is the same as for accept()
    function SocketChannel : readMessage()
        local request;
        local error;
        DataTransferChannel.resetAbortAsync(self);
        while DataTransferChannel.isAsyncAborted(self) ~= true do
            _client: settimeout(SocketChannel.RECEIVE_TIMEOUT);
            request, error = _client: receive();
            if request ~= nil then
                _logger: debug("SUCCESSFULLY READ MESSAGE FROM SOCKET!");
                break;
            else
                if error ~= nil then
                    if error == "closed" then
                        _logger: debug("SOCKET CLOSED ERROR DURING READ OPERATION! "
                                                            .. "CLIENT CLOSE CONNECTION");
                        request = DataTransferChannel.CLIENT_OFF;
                        break;
                    elseif error == "timeout" then
                        _logger: debug("SOCKET RECEIVE IS WAITING FOR INCOMING DATA...");
                    else
                        -- Jsust logging...
                        _logger: debug("READ MESSAGE FROM SOCKET FAILED WITH ERROR: "
                                                                            .. error .. "!");
                    end;
                else
                    -- Jsust logging...
                    _logger: debug("READ MESSAGE FROM SOCKET FAILED!");
                end;
            end;
        end;
        return request;
    end;


    function SocketChannel : writeMessage(message)
        local result;
        local line = message .. "\n";
        local lastByteIndex, error = _client: send(line);
        if lastByteIndex ~= nil then
            _logger: debug("SUCCESSFULLY WRITE MESSAGE TO SOCKET!");
        else
            if error ~= nil then
                if error == "closed" then
                    _logger: debud("SOCKET CLOSED ERROR DURING WRITE OPERATION! "
                                                        .. "CLIENT CLOSE CONNECTION");
                    result = DataTransferChannel.CLIENT_OFF;
                else
                    -- Jsust logging...
                    _logger: debug("WRITE MESSAGE TO SOCKET FAILED WITH ERROR: "
                            .. error .. "!");
                end;
            else
                -- Jsust logging...
                _logger: debug("WRITE MESSAGE TO SOCKET FAILED!");
            end;
        end;

        return result;
    end;


-- End of class declaration
return SocketChannel;

