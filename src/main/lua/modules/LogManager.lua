---------------------------------------------------------------------------------------
-- Logger.lua
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
-- Date: 13.09.2016
---------------------------------------------------------------------------------------
-- This module contains logic for server logging
--
---------------------------------------------------------------------------------------

-- src.modules.RequestManager

local LogManager = {};


local logFile;


---------------------------------------------------------------------------------------
-- Opens log-file in given folder for writing
--
---------------------------------------------------------------------------------------
function LogManager : createLog(folder)

    local file = folder .. "\\log.txt";
    logFile = io.open(file, "w");

end;


---------------------------------------------------------------------------------------
-- Close log-file descriptor
--
---------------------------------------------------------------------------------------
function LogManager : closeLog()

    if logFile then
        logFile : close();
        logFile = nil;
    end;

end;


---------------------------------------------------------------------------------------
-- Writes line to log-file
--
---------------------------------------------------------------------------------------
function LogManager : writeToLog(message)

    if logFile ~= nil then
        logFile : write(os.date ("%c") .. ": " .. message .. "\n");
    end;

end;

-- End of RequestManager module
return LogManager;