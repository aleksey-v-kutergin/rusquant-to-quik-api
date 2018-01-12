---------------------------------------------------------------------------------------
-- R2QServer - wrapper for all remaining code It glues all the pieces into single program.
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
--
---------------------------------------------------------------------------------------
local Server = require "modules.Server";
local _server;

-- QUIK Terminal calls this function before main.
-- Therefore, it is logical to perform all init operations here
function OnInit()
    _server = Server: new(Server.SOCKET);
end;

-- Entry point to qlua script execution process under QUIK terminal
-- QUIK executes main() in separate thread
function main()
    _server: run();
end;

-- Callback for Stop sript execution event
function OnStop()
    _server: stop();
end;

-- Callback for terminal exiting event.
function OnClose()
    _server: stop();
end;

-- Callback for terminal connects to broker's server event.
function OnConnected()
    _server: onConnectBrokerServer(true);
end;

-- Callback for terminal lost connection to broker's server event.
function OnDisconnected()
    _server: onConnectBrokerServer(false);
end;

-- Callback for transaction.
function OnTransReply(trans_reply)
    _server: onTransReplay(trans_reply);
end;

-- Callback for and order occurance.
function OnOrder(order)
    _server: onOrder(order);
end;

-- Callback for a trade occurance.
function OnTrade(trade)
    _server: onTrade(trade);
end;