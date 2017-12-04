---------------------------------------------------------------------------------------
-- CacheManager.lua
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
-- Date: 11.07.2017
---------------------------------------------------------------------------------------
-- This module provides functionality for caching transaction's replays, orders, trades
-- and othe objects. Also, it provides methods to manage cached objects.
--
---------------------------------------------------------------------------------------

-- src.modules.CacheManager
local CacheManager = {};


---------------------------------------------------------------------------------------
-- Available objects for caching
--
---------------------------------------------------------------------------------------
local TRANS_REPLAY = "TRANS_REPLAY";
local ORDER = "ORDER";
local TRADE = "TRADE";
local PARAMETER_DESCRIPTOR = "PARAMETER_DESCRIPTOR";
local QUOTES_DESCRIPTOR = "QUOTES_DESCRIPTOR";
local OHLC_DATASOURCE = "OHLC_DATASOURCE";
local logger;
local jsonParser;

---------------------------------------------------------------------------------------
-- Caching replays for transactions;
-- The key of the replay object in cache is trans_id ( user defined id of the transaction ).
--
---------------------------------------------------------------------------------------
local trasactionsReplayCache = {};
local transCacheSize = 0;


local function cacheTransactionReplay(transReplay)
    logger.writeToLog(this, "CACHING TRANSACTION REPLAY: \n" .. jsonParser: encode_pretty(transReplay));
    trasactionsReplayCache[transReplay.trans_id] = transReplay;
    transCacheSize = transCacheSize + 1;
end;


local function getTransactionReplay(transId, remove)
    local cacheItem = trasactionsReplayCache[transId];
    if cacheItem ~= nil then
        if remove == true then
            trasactionsReplayCache[transId] = nil;
            transCacheSize = transCacheSize - 1;
        end;
        logger.writeToLog(this, "EXTRACT TRANSACTION REPLAY FROM CACHE FOR TRANSACTION ID: " .. transId .."!");
    end;
    return cacheItem;
end;



---------------------------------------------------------------------------------------
-- Caching orders;
-- The key of the order object in cache is order_num ( because it is available in replay for transacrion ).
--
---------------------------------------------------------------------------------------
local ordersCache = {};
local ordersCacheSize = 0;


local function cacheOrder(order)
    logger.writeToLog(this, "CACHING ORDER: \n" .. jsonParser: encode_pretty(order));
    ordersCache[order.order_num] = order;
    ordersCacheSize = ordersCacheSize + 1;
end;


local function getOrder(orderNum, remove)
    logger.writeToLog(this, "CHECKING FOR EXISTANCE OF THE ORDER IN CACHE!");
    local cacheItem = ordersCache[orderNum];
    if cacheItem ~= nil then
        cacheItem["type"] = "Order";
        cacheItem.datetime["type"] = "DateTime";
        cacheItem.withdraw_datetime["type"] = "DateTime";
        if remove == true then
            ordersCache[orderNum] = nil;
            ordersCacheSize = ordersCacheSize - 1;
        end;
        logger.writeToLog(this, "EXTRACT ORDER FROM CACHE\n: " .. jsonParser: encode_pretty(cacheItem));
    end;
    return cacheItem;
end;



---------------------------------------------------------------------------------------
-- Caching trades;
-- A single order may cause several trades. So one needs to cache a collection of trades.
-- The key of the trade's collection in cache is order_num ( id of order that triggered  ).
--
---------------------------------------------------------------------------------------
local tradesCache = {};
local tradesCacheSize = 0;


local function cacheTrade(trade)
    local trades = tradesCache[trade.order_num];
    if trades ~= nil then

        -- For some reason Quik may call OnTrade() callback several times for same trade...
        local tradeAlreadyExitst = false;
        for k, v in pairs(trades) do
            if v.trade_num == trade.trade_num then
                tradeAlreadyExitst = true;
                break;
            end;
        end;

        if tradeAlreadyExitst == true then
            logger.writeToLog(this, "TRADE: " .. trade.trade_num .. " ALREADY EXITST IN CACHE!");
        else
            logger.writeToLog(this, "CACHING TRADE: \n" .. jsonParser: encode_pretty(trade));
            logger.writeToLog(this, "CACHE ALREADY CONTAINS TRADES FOR THIS ORDER: " .. trade.order_num .. " ADD NEW TRADE TO EXISTING COLLECTION!");
            -- Add new trade for existing trade's collection
            trades[trade.trade_num] = trade;
        end;
    else
        logger.writeToLog(this, "CACHING TRADE: \n" .. jsonParser: encode_pretty(trade));
        logger.writeToLog(this, "THERE ARE NO TRADES FOR ORDER: " .. trade.order_num .. " IN CACHE. ADDING FIRST ONE!");
        -- Create new trades collection
        trades = {};
        trades[trade.trade_num] = trade;
        tradesCache[trade.order_num] = trades;
    end;
    tradesCacheSize = tradesCacheSize + 1;
end;


local function getTrades(orderNum, remove)
    local trades = tradesCache[orderNum];
    local result = {};
    local counter = 1;
    if trades ~= nil then
        for k, v in pairs(trades) do
            v["type"] = "Trade";
            v.datetime["type"] = "DateTime";
            v.canceled_datetime["type"] = "DateTime";
            result[counter] = v;
            counter = counter + 1;
        end;
        if remove == true then
            tradesCache[orderNum] = nil;
            tradesCacheSize = tradesCacheSize - counter;
        end;
    end;
    return result;
end;



---------------------------------------------------------------------------------------
-- Caching descriptors for tarding parameters;
-- The key of the replay object in cache is id (hash code of the string = classCode + securityCode + parameterName).
--
---------------------------------------------------------------------------------------
local paramDescriptorCache = {};
local paramDescriptorCacheSize = 0;


local function cacheParamDescriptor(descriptor)
    logger.writeToLog(this, "CACHING PARAMETER'S DESCRIPTOR: \n" .. jsonParser: encode_pretty(descriptor));
    paramDescriptorCache[descriptor.id] = descriptor;
    paramDescriptorCacheSize = paramDescriptorCacheSize + 1;
end;


local function getParamDescriptor(descriptorId, remove)
    local cacheItem = paramDescriptorCache[descriptorId];
    if cacheItem ~= nil then
        logger.writeToLog(this, "EXTRACT PARAMETER'S DESCRIPTOR FROM CACHE FOR ID: " .. descriptorId .."!");
        if remove == true then
            paramDescriptorCache[descriptorId] = nil;
            paramDescriptorCacheSize = paramDescriptorCacheSize - 1;
        end;
    end;
    return cacheItem;
end;


local function containsParamDescriptor(descriptorId)
    logger.writeToLog(this, "CHEKING THE EXISTANCE OF THE PARAMETER'S DESCRIPTOR WITH ID: " .. descriptorId .." IN CACHE!");
    local descriptor = paramDescriptorCache[descriptorId];
    if descriptor ~= nil then
        logger.writeToLog(this, "FOUND PARAMETER'S DESCRIPTOR: " .. jsonParser: encode_pretty(descriptor));
        return true;
    else
        logger.writeToLog(this, "PARAMETER'S DESCRIPTOR WITH ID: " .. descriptorId .." DOES NOT EXIST IN CACHE!");
        return false;
    end;
end;



---------------------------------------------------------------------------------------
-- Caching descriptors for quotes (order book data);
-- The key of the object in cache is id (hash code of the string = classCode + securityCode).
--
---------------------------------------------------------------------------------------
local quotesDescriptorCache = {};
local quotesDescriptorCacheSize = 0;


local function cacheQuotesDescriptor(descriptor)
    logger.writeToLog(this, "CACHING QUOTES DESCRIPTOR: \n" .. jsonParser: encode_pretty(descriptor));
    quotesDescriptorCache[descriptor.id] = descriptor;
    quotesDescriptorCacheSize = quotesDescriptorCacheSize + 1;
end;


local function getQuotesDescriptor(descriptorId, remove)
    local cacheItem = quotesDescriptorCache[descriptorId];
    if cacheItem ~= nil then
        logger.writeToLog(this, "EXTRACT QUOTES DESCRIPTOR FROM CACHE FOR ID: " .. descriptorId .."!");
        if remove == true then
            quotesDescriptorCache[descriptorId] = nil;
            quotesDescriptorCacheSize = quotesDescriptorCacheSize - 1;
        end;
    end;
    return cacheItem;
end;


local function containsQuotesDescriptor(descriptorId)
    logger.writeToLog(this, "CHEKING THE EXISTANCE OF THE QUOTES DESCRIPTOR WITH ID: " .. descriptorId .." IN CACHE!");
    local descriptor = quotesDescriptorCache[descriptorId];
    if descriptor ~= nil then
        logger.writeToLog(this, "FOUND QUOTES DESCRIPTOR: " .. jsonParser: encode_pretty(descriptor));
        return true;
    else
        logger.writeToLog(this, "QUOTES DESCRIPTOR WITH ID: " .. descriptorId .." DOES NOT EXIST IN CACHE!");
        return false;
    end;
end;



---------------------------------------------------------------------------------------
-- Caching datasources for OHLC data;
--
---------------------------------------------------------------------------------------
local datasourceCache = {};
local datasourceCacheSize = 0;


local function cacheDatasource(datasource)
    logger.writeToLog(this, "CACHING DATASOURCE: \n" .. jsonParser: encode_pretty(datasource.descriptor));
    datasourceCache[datasource.id] = datasource;
    datasourceCacheSize = datasourceCacheSize + 1;
end;


local function getDatasource(datasourceId, remove)
    local cacheItem = quotesDescriptorCache[datasourceId];
    if cacheItem ~= nil then
        logger.writeToLog(this, "EXTRACT DATASOURCE FROM CACHE FOR ID: " .. datasourceId .."!");
        if remove == true then
            datasourceCache[datasourceId] = nil;
            datasourceCacheSize = datasourceCacheSize - 1;
        end;
    end;
    return cacheItem;
end;


local function containsDatasource(datasourceId)
    logger.writeToLog(this, "CHEKING THE EXISTANCE OF THE DATASOURCE WITH ID: " .. datasourceId .." IN CACHE!");
    local datasource = datasourceCache[datasourceId];
    if datasource ~= nil then
        logger.writeToLog(this, "FOUND DATASOURCE: " .. jsonParser: encode_pretty(datasource.descriptor));
        return true;
    else
        logger.writeToLog(this, "DATASOURCE WITH ID: " .. datasourceId .." DOES NOT EXIST IN CACHE!");
        return false;
    end;
end;



---------------------------------------------------------------------------------------
-- Public functions to work with cache
--
---------------------------------------------------------------------------------------
function CacheManager : cache(objectType, object)
    if(TRANS_REPLAY == objectType) then
        cacheTransactionReplay(object);
    elseif ORDER == objectType then
        cacheOrder(object);
    elseif TRADE == objectType then
        cacheTrade(object);
    elseif PARAMETER_DESCRIPTOR == objectType then
        cacheParamDescriptor(object);
    elseif QUOTES_DESCRIPTOR == objectType then
        cacheQuotesDescriptor(object);
    elseif OHLC_DATASOURCE == objectType then
        cacheDatasource(object);
    else
        -- DO NOTHING
    end;
end;


function CacheManager : get(objectType, key, remove)
    if(TRANS_REPLAY == objectType) then
        return getTransactionReplay(key, remove);
    elseif ORDER == objectType then
        return getOrder(key, remove);
    elseif TRADE == objectType then
        return getTrades(key, remove);
    elseif PARAMETER_DESCRIPTOR == objectType then
        return getParamDescriptor(key, remove);
    elseif QUOTES_DESCRIPTOR == objectType then
        return getQuotesDescriptor(key, remove);
    elseif OHLC_DATASOURCE == objectType then
        return getDatasource(key, remove);
    else
        return nil;
    end;
end;


function CacheManager : contains(objectType, key)
    if(TRANS_REPLAY == objectType) then
        return false;
    elseif ORDER == objectType then
        return false;
    elseif TRADE == objectType then
        return false;
    elseif PARAMETER_DESCRIPTOR == objectType then
        return containsParamDescriptor(key);
    elseif QUOTES_DESCRIPTOR == objectType then
        return containsQuotesDescriptor(key);
    elseif OHLC_DATASOURCE == objectType then
        return containsDatasource(key);
    else
        return nil;
    end;
end;


function CacheManager : resetCache()
    trasactionsReplayCache = {};
    transCacheSize = 0;
    ordersCache = {};
    ordersCacheSize = 0;
    tradesCache = {};
    tradesCacheSize = 0;

    -- Close descriptors for trading parameters
    for key, value in pairs(paramDescriptorCache) do
        local result = CancelParamRequest(value.classCode, value.securityCode, value.parameterName);
        if result == true then
            logger.writeToLog(this, "SUCCESSFULLY CLOSING PARAMETER'S DESCRIPTOR: " .. jsonParser: encode_pretty(value) .."\nWHILE RESETING CACHE!");
        else
            logger.writeToLog(this, "FAILED TO CLOSE PARAMETER'S DESCRIPTOR IN CACHE FOR ID: " .. jsonParser: encode_pretty(value) .."\nWHILE RESETING CACHE!");
        end;
    end;
    paramDescriptorCache = {};
    paramDescriptorCacheSize = 0;


    -- Close descriptors for quotes
    for key, value in pairs(quotesDescriptorCache) do
        local isSubscribed = IsSubscribed_Level_II_Quotes(value.classCode, value.securityCode);
        if isSubscribed == true then
            local result = Unsubscribe_Level_II_Quotes(value.classCode, value.securityCode);
            if result == true then
                logger.writeToLog(this, "SUCCESSFULLY CLOSING QUOTES DESCRIPTOR: " .. jsonParser: encode_pretty(value) .."\nWHILE RESETING CACHE!");
            else
                logger.writeToLog(this, "FAILED TO CLOSE QUOTES DESCRIPTOR IN CACHE FOR ID: " .. jsonParser: encode_pretty(value) .."\nWHILE RESETING CACHE!");
            end;
        end;
    end;
    quotesDescriptorCache = {};
    quotesDescriptorCacheSize = 0;
end;


function CacheManager : setLogger(externalLogger)
    logger = externalLogger;
    logger.writeToLog(this, "LOGGER WITHIN CACHE MANAGER");
end;


function CacheManager : setJsonParser(parser)
    jsonParser = parser;
    logger.writeToLog(this, "JSOM PARSE WITHIN CACHE MANAGER");
end;


-- End of CacheManager module
return CacheManager;