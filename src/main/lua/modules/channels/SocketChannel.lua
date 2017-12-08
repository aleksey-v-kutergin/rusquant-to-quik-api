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

-- Class declaration
local SocketChannel = class("SocketChannel");

    -- Class constructor:
    function SocketChannel : initialize(host, port)

    end;

-- End of class declaration
return SocketChannel;

