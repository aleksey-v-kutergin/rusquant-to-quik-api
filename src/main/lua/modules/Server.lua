---------------------------------------------------------------------------------------
-- Server - simple lua-server
-- Step 1. Create channel for data-transfering
-- Step 2. Wait for someone connets
-- Step 3. Read request and invoke business-logic
-- Step 4. Write response
-- If client is off - go to step 2
-- If server is instructed to stop - shutdown process and free resources
--
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
--
---------------------------------------------------------------------------------------

-- External dependencies:
-- JSON.lua - simple, pure-lua lua-object to JSON, JSON to lua-objec converter
-- middleclass.lua - nice lib to support OOP-stuff in lua
local jsonParser = require "lib.JSON";
local class      = require "lib.middleclass";

-- Custom dependencies:
local Logger            = require "modules.Logger";
local PipeChannel       = require "modules.channels.PipeChannel";
local SocketChannel     = require "modules.channels.SocketChannel";
local QuikDataManager   = require "modules.QuikDataManager";
local RequestManager    = require "modules.RequestManager";



-- Class declaration
local Server = class("Server");

    -- Static fields:

    Server.static.R2Q_PIPE_NAME = "\\\\.\\pipe\\R2QPipe";
    Server.static.R2Q_HOST = "localhost";
    Server.static.R2Q_PORT = 8989;

    -- Supported channel's types for data transfering
    Server.static.PIPE = "PIPE";
    Server.static.SOCKET = "SOCKET";

    -- Server switches between three modes:
    -- Mode 0 - Server waits for incoming client's connections
    -- Mode 1 - Server waits for a incoming requests from client
    -- Mode 2 - Server process request and writes corresponding response
    Server.static.WAIT_CLIENT = 0;
    Server.static.READING = 1;
    Server.static.WRITING = 2;

    -- Private fields:
    local _logger;
    local _quikDataManager;
    local _requestManager;

    -- Channel for data transfering
    local _channel;

    -- Current server state: wait, read, write
    local _serverMode;

    -- Determines if server has been instructed to shutdown
    local _isServerStoped;

    -- Keeps request from client between read and write steps
    local _clientRequest;

    -- Does terminal connected to remote broker's server
    local _isConnectedToBrokerServer;

    -- Class constructor:
    function Server : initialize(channelType)
        local scriptPath = getScriptPath();
        _logger = Logger: new(scriptPath, "log.txt");
        _logger: debug("START INITIALIZATION OF R2QServer");
        _quikDataManager = QuikDataManager: new(_logger, jsonParser);
        _requestManager = RequestManager: new(_logger, jsonParser, _quikDataManager);

        if channelType == Server.PIPE then
            _channel = PipeChannel: new(Server.R2Q_PIPE_NAME, _logger);
        elseif channelType == Server.SOCKET then
            _channel = SocketChannel: new(Server.R2Q_HOST, Server.R2Q_PORT, _logger);
        else
            _channel = PipeChannel: new();
        end;
        _isConnectedToBrokerServer = false;
        _isServerStoped = true;
        _serverMode = Server.WAIT_CLIENT;
    end;


    -- Determines need continue\stop main server loop
    local _needContinueWork = function(self)
        if _isServerStoped == false then
            return true;
        end;
        return false;
    end;


    local _executeWaitStep = function(self)
        if _channel: connect() == true then
            _serverMode = Server.READING;
        end;
    end;


    local _executeReadStep = function(self)
        local request = _channel: readMessage();
        if request ~= nil then
            if request == "CLIENT_OFF" then
                _channel: disconnet();
                _serverMode = Server.WAIT_CLIENT;
            else
                _clientRequest = request;
                _serverMode = Server.WRITING;
            end;
        end;
    end;


    local _executeWriteStep = function(self)
        local response = _requestManager: processRequest(_clientRequest);
        local result = _channel: writeMessage(response);
        if result == "CLIENT_OFF" then
            _channel: disconnet();
            _serverMode = Server.WAIT_CLIENT;
        else
            _serverMode = Server.READING;
        end;
    end;


    -- Public methods:
    function Server : run()
        -- If channel was opened - run server main loop
        if _channel: open() == true then
            _isServerStoped = false;
            while _needContinueWork(self) == true do
                if _serverMode == Server.WAIT_CLIENT then
                    _executeWaitStep(self);
                elseif _serverMode == Server.READING then
                    _executeReadStep(self)
                elseif _serverMode == Server.WRITING then
                    _executeWriteStep(self)
                end;
                sleep(1);
            end;
            _logger: debug("SERVER MAIN LOOP HAS BEEN STOPPED. CLOSING DATA TRANSFER CHANNEL.");
            -- Server is stoping - destroy channel instance
            if _channel: isConnected() == true then
                _channel: disconnet();
            end;
            _channel: close();
        end;
        _logger: debug("FREE ALL REMAINING RESOURCES!");
        _quikDataManager: flushCache();
        _logger: close();
    end;


    function Server : stop()
        _logger : debug("R2QServer HAS BEEN INSTRUCTED TO SHUTDOWN "
                .. "BUY STOP BUTTON CLICK (OnStop() callback) OR BY CLOSING "
                            .. "QUIK TERMINAL WINDOW (OnClose() callback)");
        _isServerStoped = true;
        _channel: abortAsync();
    end;


    function Server : onTransReplay(transReplay)
        _quikDataManager: cacheTransReplay(transReplay)
    end;

    function Server : onOrder(order)
        _quikDataManager: cacheOrder(order)
    end;

    function Server : onTrade(trade)
        _quikDataManager: cacheTrade(trade)
    end;

    function Server : onConnectBrokerServer(value)
        _isConnectedToBrokerServer = value;
    end;

-- End of class declaration
return Server;