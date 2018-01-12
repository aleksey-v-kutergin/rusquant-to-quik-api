---------------------------------------------------------------------------------------
-- Logger - class contains logic for server-side logging.
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
--
---------------------------------------------------------------------------------------
local class = require "middleclass";

-- Class declaration
local Logger = class("Logger");

    -- Private fields (Their scope is Logger.lua file)
    local _log;
    local _logFileName;
    local _logPath;

    -- Class constructor
    function Logger : initialize(scriptFolder, fileName)
        _logPath = scriptFolder;
        _logFileName = fileName;

        local path = _logPath .. "\\logs\\" .. _logFileName;
        _log = io.open(path, "w");
    end;

    -- Public method
    function Logger : getLogPath()
        return _logPath .. "\\" .. _logFileName;
    end;

    function Logger : getLogFileName()
        return _logFileName;
    end;

    function Logger : close()
        if _log ~= nil then
            _log : flush();
            _log : close();
            _log = nil;
        end;
    end;

    function Logger : info(message)
        if _log ~= nil then
            _log : write(os.date ("%c") .. " INFO: " .. message .. "\n");
            _log : flush();
        end;
    end;

    function Logger : warn(message)
        if _log ~= nil then
            _log : write(os.date ("%c") .. " WARN: " .. message .. "\n");
            _log : flush();
        end;
    end;

    function Logger : debug(message)
        if _log ~= nil then
            _log : write(os.date ("%c") .. " DEBUG: " .. message .. "\n");
            _log : flush();
        end;
    end;

    function Logger : error(message)
        if _log ~= nil then
            _log : write(os.date ("%c") .. " ERROR: " .. message .. "\n");
            _log : flush();
        end;
    end;

-- End of Logger class declaration
return Logger;