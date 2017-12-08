---------------------------------------------------------------------------------------
-- Cache - simple cache for orders, transaction replays, desciptors... etc.
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
--
---------------------------------------------------------------------------------------
local class = require "lib.middleclass";

-- Class declaration
local Cache = class("Cache");

    -- Static fields:
    Cache.static.TRANS_REPLAY = "TRANS_REPLAY";
    Cache.static.ORDER = "ORDER";
    Cache.static.TRADE = "TRADE";
    Cache.static.PARAMETER_DESCRIPTOR = "PARAMETER_DESCRIPTOR";
    Cache.static.QUOTES_DESCRIPTOR = "QUOTES_DESCRIPTOR";
    Cache.static.OHLC_DATASOURCE = "OHLC_DATASOURCE";

    -- Private fields:
    local _logger;
    local _jsonParser;

    -- Cahe for transaction replay (inputs of OnTransReplay() callback)
    local _transReplayCache;
    local _transCacheSize;

    -- Cache for orders (inputs of OnOrder() callback)
    local _ordersCache;
    local _ordersCacheSize;

    -- Cache for trades (inputs of OnTrade() callback)
    local _tradesCache;
    local _tradesCacheSize;

    -- Cache for subsciptions on parameters of current trades table
    local _paramDescriptorCache;
    local _paramDescriptorCacheSize;

    -- Cache for subsciptions on quotes
    local _quotesDescriptorCache;
    local _quotesDescriptorCacheSize;

    -- Cache for OHCL price datasources
    local _datasourceCache;
    local _datasourceCacheSize;

    -- Class constructor
    function Cache : initialize(logger, jsonParser)
        _logger = logger;
        _jsonParser = jsonParser;

        _transReplayCache = {};
        _transCacheSize = 0;

        _ordersCache = {};
        _ordersCacheSize = 0;

        _tradesCache = {};
        _tradesCacheSize = 0;

        _paramDescriptorCache = {};
        _paramDescriptorCacheSize = 0;

        _quotesDescriptorCache = {};
        _quotesDescriptorCacheSize = 0;

        _datasourceCache = {};
        _datasourceCacheSize = 0;
    end;

    -- Private methods:
    local _putTransReplay = function(self, transReplay)
        _logger: debug("CACHING OnTransReplay() CALLBACK ARG: \n"
                .. _jsonParser: encode_pretty(transReplay));
        _transReplayCache[transReplay.trans_id] = transReplay;
        _transCacheSize = _transCacheSize + 1;
    end;


    local _extractTransReplay = function(self, transId, remove)
        local cacheItem = _transReplayCache[transId];
        if cacheItem ~= nil then
            if remove == true then
                _logger: debug("EXTRACT AND REMOVE TRANSACTION REPLAY:\n"
                        .. _jsonParser: encode_pretty(cacheItem)
                        .. "\nFROM CACHE FOR TRANSACTION ID: " .. transId .."!");
                _transReplayCache[transId] = nil;
                _transCacheSize = _transCacheSize - 1;
            else
                _logger: debug("EXTRACT TRANSACTION REPLAY:\n"
                        .. _jsonParser: encode_pretty(cacheItem)
                        .. "\nFROM CACHE FOR TRANSACTION ID: " .. transId .."!");
            end;
        else
            _logger: debug("TRANSACTION REPLAY WITH TRANSACTION ID: "
                    .. transId .." DOES NOT EXIST IN CACHE! RETURN NIL!");
        end;
        return cacheItem;
    end;


    local _containsTransReplay = function(self, transId)
        _logger: debug("CHEKING THE EXISTANCE OF "
                            .. "TRANSACTION REPLAY FOR TRANSACTION ID: "
                                        .. transId .." IN CACHE!");
        local transReplay = _transReplayCache[transId];
        if transReplay ~= nil then
            _logger: debug("FOUND TRANSACTION REPLAY:\n"
                                    .. _jsonParser: encode_pretty(transReplay)
                                    .. " \nFOR TRANSACTION ID: " .. transId .." IN CACHE!");
            return true;
        else
            _logger: debug("TRANSACTION REPLAY "
                                    .. "FOR TRANSACTION ID: "
                                    .. transId .." DOES NOT EXIST IN CACHE!");
            return false;
        end;
    end;


    local _putOrder = function(self, order)
        _logger: debug("CACHING OnOrder() CALLBACK ARG:\n"
                .. _jsonParser: encode_pretty(order));
        _ordersCache[order.order_num] = order;
        _ordersCacheSize = _ordersCacheSize + 1;
    end;


    local _extractOrder = function(self, orderNum, remove)
        local cacheItem = _ordersCache[orderNum];
        if cacheItem ~= nil then
            if remove == true then
                _logger: debug("EXTRACT AND REMOVE ORDER:\n"
                        .. _jsonParser: encode_pretty(cacheItem)
                        .. "\nFROM CACHE FOR ORDER NUMBER: " .. orderNum .."!");
                _ordersCache[orderNum] = nil;
                _ordersCacheSize = _ordersCacheSize - 1;
            else
                _logger: debug("EXTRACT ORDER:\n"
                        .. _jsonParser: encode_pretty(cacheItem)
                        .. "\nFROM CACHE FOR ORDER NUMBER: " .. orderNum .."!");
            end;
        else
            _logger: debug("ORDER WITH NUMBER: "
                    .. orderNum .." DOES NOT EXIST IN CACHE! RETURN NIL!");
        end;
        return cacheItem;
    end;


    local _containsOrder = function(self, orderNum)
        _logger: debug("CHEKING THE EXISTANCE OF "
                    .. "ORDER WITH NUMBER: " .. orderNum .." IN CACHE!");
        local order = _ordersCache[orderNum];
        if order ~= nil then
            _logger: debug("FOUND ORDER: \n"
                    .. _jsonParser: encode_pretty(order)
                    .. "\nFOR ORDER NUMBER: " .. orderNum .." IN CACHE!");
            return true;
        else
            _logger: debug("ORDER WITH NUMBER: "
                    .. orderNum .." DOES NOT EXIST IN CACHE!");
            return false;
        end;
    end;


    local _putTrade = function(self, trade)
        local trades = _tradesCache[trade.order_num];
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
                _logger: debug("TRADE: " .. trade.trade_num .. " ALREADY EXITST IN CACHE!");
            else
                _logger: debug("CACHING TRADE:\n" .. _jsonParser: encode_pretty(trade));
                _logger: debug("CACHE ALREADY CONTAINS TRADES FOR THIS ORDER: "
                                .. trade.order_num .. " ADD NEW TRADE TO EXISTING COLLECTION!");
                -- Add new trade for existing trade's collection
                trades[trade.trade_num] = trade;
                _tradesCacheSize = _tradesCacheSize + 1;
            end;
        else
            _logger: debug("CACHING TRADE:\n" .. _jsonParser: encode_pretty(trade));
            _logger: debug("THERE ARE NO TRADES FOR ORDER: "
                                    .. trade.order_num .. " IN CACHE. ADDING FIRST ONE!");
            -- Init new trades collection
            trades = {};
            trades[trade.trade_num] = trade;
            _tradesCache[trade.order_num] = trades;
            _tradesCacheSize = _tradesCacheSize + 1;
        end;
    end;


    local _extractTrades = function(self, orderNum, remove)
        local result = {};
        local tradesCollection = _tradesCache[orderNum];

        if tradesCollection ~= nil then
            if remove == true then
                _logger: debug("EXTRACT AND REMOVE TRADES:\n"
                                    .. _jsonParser: encode_pretty(tradesCollection)
                                    .. "\nFROM CACHE FOR ORDER NUMBER: " .. orderNum .."!");
                local counter = 1;
                for k, v in pairs(tradesCollection) do
                    result[counter] = v;
                    counter = counter + 1;
                end;
                _tradesCache[orderNum] = nil;
                _tradesCacheSize = _tradesCacheSize - counter;
            else
                _logger: debug("EXTRACT TRADES:\n"
                                .. _jsonParser: encode_pretty(tradesCollection)
                                .. "\nFROM CACHE FOR ORDER NUMBER: " .. orderNum .."!");
                result = tradesCollection;
            end;
        else
            _logger: debug("TRADES FOR ORDER WITH NUMBER: "
                    .. orderNum .." DO NOT EXIST IN CACHE! RETURN EMPTY COLLECTION!");
        end;
        return result;
    end;


    local _containsTrade = function(self, tradeNumber)
        _logger: debug("CHEKING THE EXISTANCE OF "
                .. "TRADE WITH NUMBER: " .. tradeNumber .." IN CACHE!");
        local trade;
        for orderNum, tradesCollection in pairs(_tradesCache) do
            for tradeNum, tradeItem in pairs(tradesCollection) do
                if tradeNumber == tradeNum then
                    trade = tradeItem;
                    break;
                end;
            end;
        end;

        if trade ~= nil then
            _logger: debug("FOUND TRADE: \n"
                    .. _jsonParser: encode_pretty(trade)
                    .. "\nFOR TRADE NUMBER: " .. tradeNumber .." IN CACHE!");
            return true;
        else
            _logger: debug("TRADE WITH NUMBER: "
                    .. tradeNumber .." DOES NOT EXIST IN CACHE!");
            return false;
        end;
    end;


    local _putParamDescriptor = function(self, descriptor)
        _logger: debug("CACHING PARAMETER'S DESCRIPTOR:\n"
                            .. _jsonParser: encode_pretty(descriptor));
        _paramDescriptorCache[descriptor.id] = descriptor;
        _paramDescriptorCacheSize = _paramDescriptorCacheSize + 1;
    end;


    local _extractParamDescriptor = function(self, descriptorId, remove)
        local cacheItem = _paramDescriptorCache[descriptorId];
        if cacheItem ~= nil then
            if remove == true then
                _logger: debug("EXTRACT AND REMOVE DESCRIPTOR:\n"
                                .. _jsonParser: encode_pretty(cacheItem)
                                .. "\nFOR TRADING PARAMETER FROM CACHE!");
                _paramDescriptorCache[descriptorId] = nil;
                _paramDescriptorCacheSize = _paramDescriptorCacheSize - 1;
            else
                _logger: debug("EXTRACT AND REMOVE DESCRIPTOR:\n"
                                .. _jsonParser: encode_pretty(cacheItem)
                                .. "\nFOR TRADING PARAMETER FROM CACHE!");
            end;
        else
            _logger: debug("TRADING PARAMETER DESCRIPTOR WITH ID: "
                    .. descriptorId .." DOES NOT EXIST IN CACHE! RETURN NIL!");
        end;
        return cacheItem;
    end;


    local _containsParamDescriptor = function(self, descriptorId)
        _logger: debug("CHEKING THE EXISTANCE OF "
                                .. "THE PARAMETER'S DESCRIPTOR WITH ID: "
                                    .. descriptorId .." IN CACHE!");
        local descriptor = _paramDescriptorCache[descriptorId];
        if descriptor ~= nil then
            _logger: debug("FOUND PARAMETER'S DESCRIPTOR:\n"
                                .. _jsonParser: encode_pretty(descriptor));
            return true;
        else
            _logger: debug("PARAMETER'S DESCRIPTOR WITH ID: "
                            .. descriptorId .." DOES NOT EXIST IN CACHE!");
            return false;
        end;
    end;


    local _putQuotesDescriptor = function(self, descriptor)
        _logger: debug("CACHING QUOTES DESCRIPTOR:\n"
                        .. _jsonParser: encode_pretty(descriptor));
        _quotesDescriptorCache[descriptor.id] = descriptor;
        _quotesDescriptorCacheSize = _quotesDescriptorCacheSize + 1;
    end;


    local _extractQuotesDescriptor = function(self, descriptorId, remove)
        local cacheItem = _quotesDescriptorCache[descriptorId];
        if cacheItem ~= nil then
            if remove == true then
                _logger: debug("EXTRACT AND REMOVE DESCRIPTOR:\n"
                        .. _jsonParser: encode_pretty(cacheItem)
                        .. "\nFOR QUOTES FROM CACHE!");
                _quotesDescriptorCache[descriptorId] = nil;
                _quotesDescriptorCacheSize = _quotesDescriptorCacheSize - 1;
            else
                _logger: debug("EXTRACT AND REMOVE DESCRIPTOR:\n"
                        .. _jsonParser: encode_pretty(cacheItem)
                        .. "\nFOR QUOTES FROM CACHE!");
            end;
        else
            _logger: debug("QUOTES DESCRIPTOR WITH ID: "
                    .. descriptorId .." DOES NOT EXIST IN CACHE! RETURN NIL!");
        end;
        return cacheItem;
    end;


    local _containsQuotesDescriptor = function(self, descriptorId)
        _logger: debug("CHEKING THE EXISTANCE OF "
                .. "THE QUOTES DESCRIPTOR WITH ID: "
                .. descriptorId .." IN CACHE!");
        local descriptor = _quotesDescriptorCache[descriptorId];
        if descriptor ~= nil then
            _logger: debug("FOUND QUOTES DESCRIPTOR:\n"
                    .. _jsonParser: encode_pretty(descriptor));
            return true;
        else
            _logger: debug("QUOTES DESCRIPTOR WITH ID: "
                    .. descriptorId .." DOES NOT EXIST IN CACHE!");
            return false;
        end;
    end;


    local _putDatasource = function(self, datasource)
        _logger: debug("CACHING DATASOURCE:\n"
                        .. _jsonParser: encode_pretty(datasource.descriptor));
        _datasourceCache[datasource.id] = datasource;
        _datasourceCacheSize = _datasourceCacheSize + 1;
    end;


    local _extractDatasource = function(self, datasourceId, remove)
        local cacheItem = _datasourceCache[datasourceId];
        if cacheItem ~= nil then
            if remove == true then
                _logger: debug("EXTRACT AND REMOVE DATASOURCE:\n"
                                        .. _jsonParser: encode_pretty(cacheItem)
                                        .. "\nFOR OHLC FROM CACHE!");
                _datasourceCache[datasourceId] = nil;
                _datasourceCacheSize = _datasourceCacheSize - 1;
            else
                _logger: debug("EXTRACT AND REMOVE DATASOURCE:\n"
                                        .. _jsonParser: encode_pretty(cacheItem)
                                        .. "\nFOR OHLC FROM CACHE!");
            end;
        else
            _logger: debug("QUOTES DATASOURCE WITH ID: "
                    .. datasourceId .." DOES NOT EXIST IN CACHE! RETURN NIL!");
        end;
        return cacheItem;
    end;


    local _containsDatasource = function(self, datasourceId)
        _logger: debug("CHEKING THE EXISTANCE OF "
                                        .. "THE OHLC DATASOURCE WITH ID: "
                                        .. datasourceId .." IN CACHE!");
        local descriptor = _datasourceCache[datasourceId];
        if descriptor ~= nil then
            _logger: debug("FOUND OHLC DATASOURCE:\n"
                    .. _jsonParser: encode_pretty(descriptor));
            return true;
        else
            _logger: debug("QUOTES DATASOURCE WITH ID: "
                    .. datasourceId .." DOES NOT EXIST IN CACHE!");
            return false;
        end;
    end;


    function Cache : put(objectType, object)
        if(Cache.TRANS_REPLAY == objectType) then
            _putTransReplay(self, object);
        elseif Cache.ORDER == objectType then
            _putOrder(self, object);
        elseif Cache.TRADE == objectType then
            _putTrade(self, object);
        elseif Cache.PARAMETER_DESCRIPTOR == objectType then
            _putParamDescriptor(self, object);
        elseif Cache.QUOTES_DESCRIPTOR == objectType then
            _putQuotesDescriptor(self, object);
        elseif Cache.OHLC_DATASOURCE == objectType then
            _putDatasource(self, object);
        else
            -- DO NOTHING
        end;
    end;


    function Cache : extract(objectType, key, remove)
        if(Cache.TRANS_REPLAY == objectType) then
            return _extractTransReplay(self, key, remove);
        elseif Cache.ORDER == objectType then
            return _extractOrder(self, key, remove);
        elseif Cache.TRADE == objectType then
            return _extractTrades(self, key, remove);
        elseif Cache.PARAMETER_DESCRIPTOR == objectType then
            return _extractParamDescriptor(self, key, remove);
        elseif Cache.QUOTES_DESCRIPTOR == objectType then
            return _extractQuotesDescriptor(self, key, remove);
        elseif Cache.OHLC_DATASOURCE == objectType then
            return _extractDatasource(self, key, remove);
        else
            return nil;
        end;
    end;


    function Cache : contains(objectType, key)
        if(Cache.TRANS_REPLAY == objectType) then
            return _containsTransReplay(self, key);
        elseif Cache.ORDER == objectType then
            return _containsOrder(self, key);
        elseif Cache.TRADE == objectType then
            return _containsTrade(self, key);
        elseif Cache.PARAMETER_DESCRIPTOR == objectType then
            return _containsParamDescriptor(self, key);
        elseif Cache.QUOTES_DESCRIPTOR == objectType then
            return _containsQuotesDescriptor(self, key);
        elseif Cache.OHLC_DATASOURCE == objectType then
            return _containsDatasource(self, key);
        else
            return nil;
        end;
    end;


    function Cache : flush()
        _transReplayCache = {};
        _transCacheSize = 0;
        _ordersCache = {};
        _ordersCacheSize = 0;
        _tradesCache = {};
        _tradesCacheSize = 0;

        -- Close descriptors for trading parameters
        for key, value in pairs(_paramDescriptorCache) do
            local result = CancelParamRequest(value.classCode, value.securityCode, value.parameterName);
            if result == true then
                _logger: debug("SUCCESSFULLY CLOSING PARAMETER'S DESCRIPTOR:\n"
                        .. _jsonParser: encode_pretty(value) .."\nWHILE RESETING CACHE!");
            else
                _logger: debug("FAILED TO CLOSE PARAMETER'S DESCRIPTOR IN CACHE FOR ID:\n"
                        .. _jsonParser: encode_pretty(value) .."\nWHILE RESETING CACHE!");
            end;
        end;
        _paramDescriptorCache = {};
        _paramDescriptorCacheSize = 0;

        -- Close descriptors for quotes
        for key, value in pairs(_quotesDescriptorCache) do
            local isSubscribed = IsSubscribed_Level_II_Quotes(value.classCode, value.securityCode);
            if isSubscribed == true then
                local result = Unsubscribe_Level_II_Quotes(value.classCode, value.securityCode);
                if result == true then
                    _logger: debug("SUCCESSFULLY CLOSING QUOTES DESCRIPTOR:\n"
                            .. _jsonParser: encode_pretty(value) .."\nWHILE RESETING CACHE!");
                else
                    _logger: debug("FAILED TO CLOSE QUOTES DESCRIPTOR IN CACHE FOR ID:\n"
                            .. _jsonParser: encode_pretty(value) .."\nWHILE RESETING CACHE!");
                end;
            end;
        end;
        _quotesDescriptorCache = {};
        _quotesDescriptorCacheSize = 0;

        -- Close OHCL datasources
        for key, value in pairs(_datasourceCache) do
            local result = value.instance:Close();
            if result == true then
                _logger: debug("SUCCESSFULLY CLOSING OHLC DATASOURCE:\n"
                        .. _jsonParser: encode_pretty(value.descriptor) .."\nWHILE RESETING CACHE!");
            else
                _logger: debug("FAILED TO CLOSE OHLC DATASOURCE:\n"
                        .. _jsonParser: encode_pretty(value.descriptor) .."\nWHILE RESETING CACHE!");
            end;
        end;
        _datasourceCache = {};
        _datasourceCache = 0;
    end;

-- End of class declaration
return Cache;