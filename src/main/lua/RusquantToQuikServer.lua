------------------------------------------------------------------------------
-- RusquantToQuikServer.lua
-- Top level project module
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
-- Date: 09.08.2016
------------------------------------------------------------------------------
-- This module is a king of wrapper for all remaining code
-- It glues all the pieces into single program
------------------------------------------------------------------------------

---------------------------------------------------------------------------------------
-- REQUIRE section
--
---------------------------------------------------------------------------------------

local serverModule = assert( require "modules.ServerModule" );

---------------------------------------------------------------------------------------
-- Test code
---------------------------------------------------------------------------------------

---------------------------------------------------------------------------------------
-- QUIK Terminal calls this function before main.
-- Therefore, it is logical to perform all init operations here
---------------------------------------------------------------------------------------
function OnInit()
    serverModule.init();
end;



---------------------------------------------------------------------------------------
-- Entry point to qlua script execution process under QUIK terminal
-- QUIK executes main() in separate thread
--
---------------------------------------------------------------------------------------
function main()
    serverModule.run();
end;


---------------------------------------------------------------------------------------
-- Callback for Stop sript execution event
--
---------------------------------------------------------------------------------------
function OnStop()
    serverModule.stop();
end;


---------------------------------------------------------------------------------------
-- Callback for terminal exiting event.
--
---------------------------------------------------------------------------------------
function OnClose()
    serverModule.stop();
end;


---------------------------------------------------------------------------------------
-- Callback for terminal connects to broker's server event.
--
---------------------------------------------------------------------------------------
function OnConnected()
    serverModule.setQUIKToBrokerConnectionFlag(true);
end;


---------------------------------------------------------------------------------------
-- Callback for terminal lost connection to broker's server event.
--
---------------------------------------------------------------------------------------
function OnDisconnected()
    serverModule.setQUIKToBrokerConnectionFlag(false);
end;


---------------------------------------------------------------------------------------
-- Callback for transaction.
--
---------------------------------------------------------------------------------------
function OnTransReply(trans_reply)
    serverModule.cacheTransReplay(this, trans_reply);
end;


---------------------------------------------------------------------------------------
-- Callback for and order occurance.
--
---------------------------------------------------------------------------------------
function OnOrder(order)
    serverModule.cacheOrder(this, order);
end;


---------------------------------------------------------------------------------------
-- Callback for a trade occurance.
--
---------------------------------------------------------------------------------------
function OnTrade(trade)
    serverModule.cacheTrade(this, trade);
end;