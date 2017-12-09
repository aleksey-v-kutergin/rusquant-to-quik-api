---------------------------------------------------------------------------------------
-- DataTransferChannel - Base class for channels. Just sintax sugar :)
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
--
---------------------------------------------------------------------------------------
local class = require "middleclass";


-- Class declaration
local DataTransferChannel = class("DataTransferChannel");

    -- Static fields:

    -- Methods return this value if client suddenly close connection
    -- during IO read or write operation
    DataTransferChannel.static.CLIENT_OFF = "CLIENT_OFF";

    -- Private fields:
    local _isConnected;

    -- Suspend the termination of an asynchronous operation
    -- if server was stopped
    local _abortAsyncOperation;


    -- Class constructor:
    function DataTransferChannel : initialize()
        _isConnected = false;
        _abortAsyncOperation = false;
    end;

    -- Public methods:

    -- Signals to abort asyn operations
    function DataTransferChannel : abortAsync()
        _abortAsyncOperation = true;
    end;

    function DataTransferChannel : setConnected(value)
        _isConnected = value;
    end;

    function DataTransferChannel : resetAbortAsync()
        _abortAsyncOperation = false;
    end;

    function DataTransferChannel : isAsyncAborted()
        return _abortAsyncOperation;
    end;

    function DataTransferChannel : open()
        return false;
    end;

    function DataTransferChannel : close()

    end;

    function DataTransferChannel : connect()
        return false;
    end;


    function DataTransferChannel : disconnet()

    end;

    function DataTransferChannel : readMessage()
        return nil;
    end;

    function DataTransferChannel : writeMessage(message)
        return nil;
    end;

    function DataTransferChannel : isConnected()
        return _isConnected;
    end;


-- End of class declaration
return DataTransferChannel;

