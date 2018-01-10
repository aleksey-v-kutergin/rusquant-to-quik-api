---------------------------------------------------------------------------------------
-- QuikDataManager - contains all "business" logic
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
--
---------------------------------------------------------------------------------------
local class = require "middleclass";
local Cache = require "modules.Cache";


-- Class declaration
local QuikDataManager = class("QuikDataManager");

    -- Static fields:
    QuikDataManager.static.JAVA_CLASS_FIELD = "@class";
    QuikDataManager.static.MAX_RANGE_SIZE = 10;

    -- Private fields:
    local _logger;
    local _jsonParser;
    local _cache;

    -- This is because one cannot hold nil for key in hash table
    -- At the java side one could serialize all fields of the Transaction class, including fields without referense to data (null value)
    -- In resulting JSON-object it looks like "someFiledName": null.
    -- But when converting from JSON to lua table, JSON parser does not include null-field to resulting table
    -- Thus, one needs some kind of etalon set of feilds. This set must not be out of date!
    local TRANSACTION_FIELDS;

    -- To get Java class for table row based on table name
    local TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP;

    -- How long we need to wait untile replay for transaction occurs
    local TRANSACTION_REPLAY_RETRY_COUNT;

    -- How long we need to wait untile replay for order occurs
    local ORDER_REPLAY_RETRY_COUNT;

    -- Class constructor
    function QuikDataManager : initialize(logger, jsonParser)
        _logger = logger;
        _jsonParser = jsonParser;
        _cache = Cache: new(logger, jsonParser);

        TRANSACTION_REPLAY_RETRY_COUNT = 10000000;
        ORDER_REPLAY_RETRY_COUNT = 10000000;

        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP = {};
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["firms"] = "Firm";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["classes"] = "SecurityClass";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["securities"] = "Security";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["trade_accounts"] = "TradingAccount";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["client_codes"] = "ClientCode";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["all_trades"] = "AnonymousTrade";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["account_positions"] = "AccountPosition";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["orders"] = "Order";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["futures_client_holding"] = "FuturesClientHolding";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["futures_client_limits "] = "FuturesClientLimit";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["money_limits"] = "MoneyLimit";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["depo_limits"] = "DepoLimit";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["trades"] = "Trade";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["stop_orders"] = "StopOrder";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["neg_deals"] = "NegDeal";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["neg_trades"] = "NegTrade";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["neg_deal_reports"] = "NegDealReport";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["firm_holding"] = "FirmHolding";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["account_balance"] = "AccountBalance";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["ccp_positions"] = "CppPosition";
        TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP["ccp_holdings"] = "CppHolding";

        TRANSACTION_FIELDS =
        {
            "ACCOUNT", "BALANCE", "BROKERREF", "CLASSCODE", "CLIENT_CODE", "EXCHANGE_CODE", "FIRM_ID",
            "FLAGS", "ORDER_NUM", "PRICE", "QUANTITY", "RESULT_MSG", "SECCODE", "SERVER_TRANS_ID", "TIME",
            "TRANS_ID", "UID", "ACTION", "STATUS", "OPERATION", "TYPE", "COMMENT", "MODE"
        }
    end;

    -- Private methods:

    -- Splits string by delimiter
    local _split = function(self, s, delimiter)
        local result = {};
        for match in (s..delimiter):gmatch("(.-)"..delimiter) do
            table.insert(result, match);
        end
        return result;
    end

    -- Creates common for ALL business-functions result table
    local _createResult = function(self, isSucces, value, error)
        local result = {};
        if isSucces == true then
            result["status"] = "SUCCESS";
            result["value"] = value;
        else
            result["status"] = "FAILED";
            result["error"] = error;
        end;
        return result;
    end;


    -- Checks for DateTime QUIK table
    local _isDateTime = function(self, object)
        local isDate = true;
        isDate = isDate and type(object) == "table";
        isDate = isDate and object.day ~= nil;
        isDate = isDate and object.hour ~= nil;
        --isDate = isDate and object.mcs ~= nil;
        isDate = isDate and object.min ~= nil;
        isDate = isDate and object.month ~= nil;
        --isDate = isDate and object.ms ~= nil;
        isDate = isDate and object.sec ~= nil;
        isDate = isDate and object.week_day ~= nil;
        isDate = isDate and object.year ~= nil;
        return isDate;
    end;

    -- According to QLua docs, all fields in transaction table
    -- have to be a string type
    local _prepareTransactionArgs = function(self, transaction)
        local args = {};
        for key, value in pairs(transaction) do
            if type(value) == "string" then
                if key ~= "MODE" and key ~= QuikDataManager.JAVA_CLASS_FIELD then
                    args[key] = value;
                end;
            else
                args[key] = tostring(value);
            end;
        end;
        return args;
    end;


    local _getTransactionReplay = function(self, transaction)
        local transId = transaction.TRANS_ID;
        local replay = _cache: extract(Cache.TRANS_REPLAY, transId, true)

        -- Waiting loop
        local counter = 0;
        while replay == nil do
            if counter % 100 == 0 then
                _logger: debug("WAITING FOR APPEARANCE OF THE TRANSACTION REPLAY IN CACHE!");
            end;

            replay = _cache: extract(Cache.TRANS_REPLAY, transId, true);
            if counter > TRANSACTION_REPLAY_RETRY_COUNT then
                local error = "EXCEEDING RETRY COUNT FOR WAITING OF OCCURANCE OF THE TRANSACTION REPLAY IN CACHE!"
                        .. "TRANSACTION REPLAY HAS NOT OCCURED YET!";
                return _createResult(self, false, nil, error);
            end;
            counter = counter + 1;
        end
        return _createResult(self, true, replay, nil);
    end;


    -- During development, folowing trubles have been uccured:
    -- 1. QLua's sendTransaction(...) requires upper-case keys in input transaction table
    --    However, transaction replay table contains lower-case keys
    --
    -- 2. Since, the same java class is used both for incoming transaction and replay, it is nice to obtain
    --    set of fields from incoming object + set of fields, that become availble after transaction
    --    has been registered at quik server.
    --    For some reason, not all fields from input transaction table present in trans replay table.

    -- 3. Total mess with keys.
    --
    -- The simples way to solve above problems - merge input transaction and replay table base on fixed set of fields
    -- with priority to transaction replay table.
    local function _mergeTransactionReplay(self, origin, replay)
        _logger: debug("CALL: _mergeTransactionReplay(...) WITH" ..
                " ORIGIN: " .. _jsonParser: encode_pretty(origin) ..
                "\n END REPLAY: " .. _jsonParser: encode_pretty(replay));
        local mergedReplay = {};
        local lowerCaseFieldName;

        for index, fieldName in ipairs(TRANSACTION_FIELDS) do
            lowerCaseFieldName = string.lower(fieldName);
            if replay[lowerCaseFieldName] ~= nil then
                mergedReplay[fieldName] = replay[lowerCaseFieldName];
            elseif origin[fieldName] ~= nil then
                mergedReplay[fieldName] = origin[fieldName];
            end;
        end;

        return mergedReplay;
    end;


    local _processQuotes = function(self, depth, quotes)
        local orderBookSide = {};
        local orderBookLevel;

        for i = 1, depth, 1 do
            orderBookLevel = {};
            orderBookLevel[QuikDataManager.JAVA_CLASS_FIELD] = "OrderBookLevel";

            -- Number of the order book level
            orderBookLevel["number"] = i;

            -- Prive of the level
            orderBookLevel["price"] = quotes[i].price;

            -- For a certain price level there may be no quotes
            if quotes[i].quantity ~= nil then
                orderBookLevel["quantity"] = quotes[i].quantity;
            else
                orderBookLevel["quantity"] = 0;
            end;
            orderBookSide[i] = orderBookLevel;
        end;
        return orderBookSide;
    end;


    -- Public functions:

    function QuikDataManager : cacheTransReplay(transReplay)
        _logger: debug("OnTransReplay() CALLBACK HAS BEEN CALLED!");
        _cache: put(Cache.TRANS_REPLAY, transReplay);
    end;


    function QuikDataManager : cacheOrder(order)
        _logger: debug("OnOrder() CALLBACK HAS BEEN CALLED!");
        order[QuikDataManager.JAVA_CLASS_FIELD] = "Order";
        if order.datetime ~= nil then
            order.datetime[QuikDataManager.JAVA_CLASS_FIELD] = "DateTime";
        end;
        if order.withdraw_datetime ~= nil then
            order.withdraw_datetime[QuikDataManager.JAVA_CLASS_FIELD] = "DateTime";
        end;
        _cache: put(Cache.ORDER, order);
    end;


    function QuikDataManager : cacheTrade(trade)
        _logger: debug("OnеTrade() CALLBACK HAS BEEN CALLED!");
        trade[QuikDataManager.JAVA_CLASS_FIELD] = "Trade";
        if trade.datetime ~= nil then
            trade.datetime[QuikDataManager.JAVA_CLASS_FIELD] = "DateTime";
        end;
        if trade.canceled_datetime ~= nil then
            trade.canceled_datetime[QuikDataManager.JAVA_CLASS_FIELD] = "DateTime";
        end;
        _cache: put(Cache.TRADE, trade);
    end;

    function QuikDataManager : flushCache()
        _cache : flush();
    end;


    function QuikDataManager : getEcho(echoMessage)
        local result;
        if echoMessage ~= nil then
            local echo = {};
            echo[QuikDataManager.JAVA_CLASS_FIELD] = "QuikEcho";
            echo["echoAnswer"] = "@ECHO: " .. echoMessage;
            result =  _createResult(self, true, echo, nil);
        else
            result =  _createResult(self, false, nil, "ECHO MESSAGE IS NIL!");
        end;
        _logger: debug("CALL: getEcho(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getTradeDate()
        local result;
        local tradeDate = getTradeDate();
        if tradeDate ~= nil then
            tradeDate[QuikDataManager.JAVA_CLASS_FIELD] = "TradeDate";
            result =  _createResult(self, true, tradeDate, nil);
        else
            result =  _createResult(self, false, nil, "CALL: getTradeDate() RETURN NIL!");
        end;
        _logger: debug("CALL: getTradeDate(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getConnectionState()
        local connectionState = {};
        connectionState[QuikDataManager.JAVA_CLASS_FIELD] = "ConnectionState";
        connectionState["isConnected"] = isConnected();

        local result =_createResult(self, true, connectionState, nil);
        _logger: debug("CALL: getConnectionState(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getInfoParameterValue(infoParamType)
        local result;
        if infoParamType ~= nil then
            local info = {};
            info[QuikDataManager.JAVA_CLASS_FIELD] = "InfoParameter";
            info["parameterName"] = infoParamType;

            local value = getInfoParam(infoParamType);
            if value == "" then
                info["parameterValue"] = "NA";
            else
                info["parameterValue"] = value;
            end;
            result =  _createResult(self, true, info, nil);
        else
            result =  _createResult(self, false, nil, "INFO PARAMETER TYPE IS NIL!");
        end;
        _logger: debug("CALL: getInfoParameterValue(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : sendTransaction(transaction)
        local result;
        local args = _prepareTransactionArgs(self, transaction);

        _logger: debug("CALL: sendTransaction(...) WITH ARGS: " .. _jsonParser: encode_pretty(args));
        local error = sendTransaction(args);
        local isSuccess = not (error ~= nil and error ~= '');

        if isSuccess == true then
            local onTransReplayResult = _getTransactionReplay(self, transaction);
            if onTransReplayResult.status ~= "FAILED" then
                local replay = _mergeTransactionReplay(self, transaction, onTransReplayResult.value);
                replay[QuikDataManager.JAVA_CLASS_FIELD] = "Transaction";
                result =  _createResult(self, true, replay, nil);
            else
                result =  _createResult(self, false, nil,
                    "CALL: sendTransaction(...) FAILS WITH ERROR: " .. onTransReplayResult.error);
            end;
        else
            result =  _createResult(self, false, nil, "CALL: sendTransaction(...) FAILS WITH ERROR: " .. error);
        end;

        _logger: debug("CALL: sendTransaction(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getOrder(orderNumber, noWait)
        local result;
        local order = _cache: extract(Cache.ORDER, orderNumber, true);
        if noWait == true then
            if order ~= nil then
                result = _createResult(self, true, order, nil);
            else
                result = _createResult(self, true, nil, "ORDER REPLAY HAS NOT OCCURED YET!");
            end;
        else
            local counter = 0;
            while order == nil and counter <= ORDER_REPLAY_RETRY_COUNT do
                if counter % 100 == 0 then
                    _logger: debug("WAITING FOR APPEARANCE OF THE ORDER REPLAY IN CACHE!");
                end;
                order = _cache: extract(Cache.ORDER, orderNumber, true);
                counter = counter + 1;
            end

            if counter <= ORDER_REPLAY_RETRY_COUNT then
                result = _createResult(self, true, order, nil);
            else
                result = _createResult(self, true, nil,
                    "EXCEEDING RETRY COUNT FOR WAITING OF OCCURANCE OF THE ORDER IN CACHE! ORDER HAS NOT OCCURED YET!");
            end;
        end;

        _logger: debug("CALL: getOrder(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getTrades(orderNumber)
        local trades = _cache: extract(Cache.TRADE, orderNumber, true);
        local tradesDataFrame = {};
        tradesDataFrame[QuikDataManager.JAVA_CLASS_FIELD] = "TradesDataFrame";
        tradesDataFrame["records"] = trades;
        local result = _createResult(self, true, tradesDataFrame, nil);
        _logger: debug("CALL: getTrades(...) FINISHED WITH RESULT: "
                                    .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : subscribeParameter(id, classCode, securityCode, parameterName)
        local result;
        local error;
        local exitsInCache = _cache: contains(Cache.PARAMETER_DESCRIPTOR, id);

        if exitsInCache == false then
            local subscribeResult = ParamRequest(classCode, securityCode, parameterName);
            if subscribeResult == true then

                local descriptor = {};
                descriptor[QuikDataManager.JAVA_CLASS_FIELD] = "ParameterDescriptor";
                descriptor["id"] = id;
                descriptor["classCode"] = classCode;
                descriptor["securityCode"] = securityCode;
                descriptor["parameterName"] = parameterName;

                _cache: put(Cache.PARAMETER_DESCRIPTOR, descriptor)
                result = _createResult(self, true, descriptor, nil);
            else
                error = "CALL OF " .. "ParamRequest" ..
                        "( classCode = " .. classCode ..
                        ", securityCode = " .. securityCode ..
                        ", parameterName = " .. parameterName ..
                        ") RETURNS FALSE!";
                result = _createResult(self, false, nil, error);
            end;
        else
            error = "SUBSCRIPTION TO PARAMETER: " ..
                    "{ id = " .. id ..
                    ", classCode = " .. classCode ..
                    ", securityCode = " .. securityCode ..
                    ", parameterName = " .. parameterName ..
                    "} ALREADY EXISTS IN CACHE!";
            result = _createResult(self, false, nil, error);
        end;
        _logger: debug("CALL: subscribeParameter(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : unsubscribeParameter(descriptor)
        local result;
        local error;
        local exitsInCache = _cache: contains(Cache.PARAMETER_DESCRIPTOR, descriptor.id);

        if exitsInCache == true then
            local cancelationResult = CancelParamRequest(descriptor.classCode, descriptor.securityCode, descriptor.parameterName);
            if cancelationResult == true then
                local booleanResult = {};
                booleanResult[QuikDataManager.JAVA_CLASS_FIELD] = "BooleanResult";
                booleanResult["value"] = true;

                _cache: extract(Cache.PARAMETER_DESCRIPTOR, descriptor.id, true);
                result = _createResult(self, true, booleanResult, nil);
            else
                error = "CALL OF " .. "CancelParamRequest" ..
                        "( classCode = " .. descriptor.classCode ..
                        ", securityCode = " .. descriptor.securityCode ..
                        ", parameterName = " .. descriptor.parameterName ..
                        ") RETURNS FALSE!";
                result = _createResult(self, false, nil, error);
            end;
        else
            error = "SUBSCRIPTION TO PARAMETER: " ..
                    "{ id = " .. descriptor.id ..
                    ", classCode = " .. descriptor.classCode ..
                    ", securityCode = " .. descriptor.securityCode ..
                    ", parameterName = " .. descriptor.parameterName ..
                    "} DOES EXISTS IN CACHE! SUBSCRIBE TO PARAMETER FIRST!";
            result = _createResult(self, false, nil, error);
        end;
        _logger: debug("CALL: unsubscribeParameter(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getTradingParameter(classCode, securityCode, parameterName, version)
        local result;
        local error;
        local parameter;
        local functionName;

        if version == "EX1" then
            functionName = "getParamEx";
            parameter = getParamEx(classCode,  securityCode, parameterName);
        elseif version == "EX2" then
            functionName = "getParamEx2";
            parameter = getParamEx2(classCode,  securityCode, parameterName);
        else
            result = _createResult(self, false, nil, "INVALID VERSION OF THE getParamEx() FUNCTION: " .. version .. "!");
        end;

        if parameter ~= nil then
            if parameter.result == "1" then
                parameter[QuikDataManager.JAVA_CLASS_FIELD] = "TradingParameter";
                result = _createResult(self, true, parameter, nil);
            else
                error = "CALL OF " .. functionName ..
                                                    "( classCode = " .. classCode ..
                                                    ", securityCode = " .. securityCode ..
                                                    ", parameterName = " .. parameterName ..
                                                    ") ENDS WITH ERROR (RESULT = 0)!";
                result = _createResult(self, false, nil, error);
            end;
        else
            error = "CALL OF " .. functionName ..
                                                "( classCode = " .. classCode ..
                                                ", securityCode = " .. securityCode ..
                                                ", parameterName = " .. parameterName ..
                                                ") RETURNS NIL VALUE!";
            result = _createResult(self, false, nil, error);
        end;
        _logger: debug("CALL: getTradingParameter(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : subscribeQuotes(id, classCode, securityCode)
        local result;
        local error;
        local exitsInCache = _cache: contains(Cache.QUOTES_DESCRIPTOR, id);

        if exitsInCache == false then
            local subscribeResult = Subscribe_Level_II_Quotes(classCode, securityCode);
            if subscribeResult == true then

                local descriptor = {};
                descriptor[QuikDataManager.JAVA_CLASS_FIELD] = "QuotesDescriptor";
                descriptor["id"] = id;
                descriptor["classCode"] = classCode;
                descriptor["securityCode"] = securityCode;

                _cache: put(Cache.QUOTES_DESCRIPTOR, descriptor);
                result = _createResult(self, true, descriptor, nil);
            else
                error = "CALL OF " .. "Subscribe_Level_II_Quotes" ..
                        "( classCode = " .. classCode ..
                        ", securityCode = " .. securityCode ..
                        ") RETURNS FALSE!";
                result = _createResult(self, false, nil, error);
            end;
        else
            error = "SUBSCRIPTION TO QUOTES FOR: " ..
                    "{ id = " .. id ..
                    ", classCode = " .. classCode ..
                    ", securityCode = " .. securityCode ..
                    "} ALREADY EXISTS IN CACHE!";
            result = _createResult(self, false, nil, error);
        end;
        _logger: debug("CALL: subscribeQuotes(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : unsubscribeQuotes(descriptor)
        local result;
        local error;
        local exitsInCache = _cache: contains(Cache.QUOTES_DESCRIPTOR, descriptor.id);

        if exitsInCache == true then
            local cancelationResult = Unsubscribe_Level_II_Quotes(descriptor.classCode, descriptor.securityCode);
            if cancelationResult == true then

                local booleanResult = {};
                booleanResult[QuikDataManager.JAVA_CLASS_FIELD] = "BooleanResult";
                booleanResult["value"] = true;

                _cache: extract(Cache.QUOTES_DESCRIPTOR, descriptor.id, true);
                result = _createResult(self, true, booleanResult, nil);
            else
                error = "CALL OF " .. "Unsubscribe_Level_II_Quotes" ..
                        "( classCode = " .. descriptor.classCode ..
                        ", securityCode = " .. descriptor.securityCode ..
                        ") RETURNS FALSE!";
                result = _createResult(self, false, nil, error);
            end;
        else
            error = "SUBSCRIPTION TO QUOTES FOR: " ..
                    "{ id = " .. descriptor.id ..
                    ", classCode = " .. descriptor.classCode ..
                    ", securityCode = " .. descriptor.securityCode ..
                    "} DOES EXISTS IN CACHE! SUBSCRIBE TO QUOTES FIRST!";
            result = _createResult(self, false, nil, error);
        end;
        _logger: debug("CALL: unsubscribeQuotes(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : isSubscribedToQuotes(descriptor)
        local result;
        local exitsInCache = _cache: contains(Cache.QUOTES_DESCRIPTOR, descriptor.id);
        if exitsInCache == true then
            local booleanResult = {};
            booleanResult[QuikDataManager.JAVA_CLASS_FIELD] = "BooleanResult";
            booleanResult["value"] = IsSubscribed_Level_II_Quotes(descriptor.classCode, descriptor.securityCode);
            result = _createResult(self, true, booleanResult, nil);
        else
            local error = "SUBSCRIPTION TO QUOTES FOR: " ..
                    "{ id = " .. descriptor.id ..
                    ", classCode = " .. descriptor.classCode ..
                    ", securityCode = " .. descriptor.securityCode ..
                    "} DOES EXISTS IN CACHE! SUBSCRIBE TO QUOTES FIRST!";
            result = _createResult(self, false, nil, error);
        end;
        _logger: debug("CALL: isSubscribedToQuotes(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getQuotes(classCode, securityCode)
        local result;
        local quotesSnapshot = getQuoteLevel2(classCode, securityCode);

        if quotesSnapshot ~= nil then
            _logger: debug("RAW QUOTES SNAPSHOT FROM getQuoteLevel2: " .. _jsonParser: encode_pretty(quotesSnapshot));

            local orderBook = {};
            orderBook[QuikDataManager.JAVA_CLASS_FIELD] = "OrderBook";
            orderBook["bid_count"] = tonumber(quotesSnapshot.bid_count);
            orderBook["offer_count"] = tonumber(quotesSnapshot.offer_count);
            orderBook["bid"] = _processQuotes(self, tonumber(quotesSnapshot.bid_count), quotesSnapshot.bid);
            orderBook["offer"] = _processQuotes(self, tonumber(quotesSnapshot.offer_count), quotesSnapshot.offer);

            _logger: debug("RESULTING ORDER BOOK OBJECT: " .. _jsonParser: encode_pretty(orderBook));
            result = _createResult(self, true, orderBook, nil);
        else
            local error = "CALL OF " .. "getQuoteLevel2" ..
                    "( classCode = " .. classCode ..
                    ", securityCode = " .. securityCode ..
                    ") RETURNS NIL! SUBCRIBE TO QUOTES OR OPEN ORDER BOOK FOR SECURITY OF INTEREST!";
            result = _createResult(self, false, nil, error);
        end;

        _logger: debug("CALL: getQuotes(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : createDatasource(id, classCode, securityCode, interval, parameter)
        local result;
        local exitsInCache = _cache: contains(Cache.OHLC_DATASOURCE, id);
        if exitsInCache == false then
            local ds, error;
            local msg = "CALL OF " .. "CreateDataSource" ..
                    "{ id = " .. id ..
                    ", classCode = " .. classCode ..
                    ", securityCode = " .. securityCode ..
                    ", interval = " .. interval;
            if parameter ~= nil then
                msg = msg .. ", parameter = " .. parameter;
            end;
            msg = msg .. "}";
            _logger: debug(msg);
            if parameter == nil then
                ds, error = CreateDataSource(classCode, securityCode, interval);
            else
                ds, error = CreateDataSource(classCode, securityCode, interval, parameter);
            end;

            if error ~= "" and error ~= nil then
                local msg = "CALL OF " .. "CreateDataSource" ..
                        "{ id = " .. id ..
                        ", classCode = " .. classCode ..
                        ", securityCode = " .. securityCode ..
                        ", interval = " .. interval;
                if parameter ~= nil then
                    msg = msg .. ", parameter = " .. parameter;
                end;
                msg = msg .. "} RETURN ERROR: " .. error;
                result = _createResult(self, false, nil, msg);
            else
                -- Если график не открыт в терминале, то данные заказываются с сервера.
                -- На их получение может уйти время, поэтому рекомендуется довить такое ожидание:
                while ds:Size() == 0 do
                    local msg = "\nWAITING FOR THE RECEIPT OF DATA IN DATASOURCE: " ..
                            "{ id = " .. id ..
                            ", classCode = " .. classCode ..
                            ", securityCode = " .. securityCode ..
                            ", interval = " .. interval;
                    if parameter ~= nil then
                        msg = msg .. ", parameter = " .. parameter;
                    end;
                    msg = msg .. "}";
                    _logger: debug(msg);
                    sleep(1);
                end;

                local descriptor = {};
                descriptor[QuikDataManager.JAVA_CLASS_FIELD] = "DatasourceDescriptor";
                descriptor["id"] = id;
                descriptor["classCode"] = classCode;
                descriptor["securityCode"] = securityCode;
                descriptor["interval"] = interval;
                if parameter ~= nil then
                    descriptor["parameter"] = parameter;
                end;

                -- Чтобы получать новые данные без использования функции обратного вызова, а просто получать новые данные в ds
                -- и брать их оттуда по необходимости нужно повесить на источник пустой коллбак.
                local isSuccess = ds:SetEmptyCallback();
                if isSuccess == true then
                    local datasource = {};
                    datasource["id"] = descriptor.id;
                    datasource["descriptor"] = descriptor;
                    datasource["instance"] = ds;
                    _cache: put(Cache.OHLC_DATASOURCE, datasource)
                    result = _createResult(self, true, descriptor, nil);
                else
                    error = "CALL OF "
                            .. "SetEmptyCallback() FOR DATASOURCE: "
                            .. _jsonParser: encode_pretty(descriptor)
                            .. "\nRETURNS FALSE! DATA UPDATES FROM SERVER ARE NOT AVAILABLE!";
                    result = _createResult(self, false, nil, error);
                end;
            end;
        else
            local msg = "OHLC DATASOURCE FOR: " ..
                    "{ id = " .. id ..
                    ", classCode = " .. classCode ..
                    ", securityCode = " .. securityCode ..
                    ", interval = " .. interval;
            if parameter ~= nil then
                msg = msg .. ", parameter = " .. parameter;
            end;
            msg = msg .. "} ALREADY EXISTS IN CACHE!";
            result = _createResult(self, false, nil, msg);
        end;

        _logger: debug("CALL: createDatasource(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : closeDatasource(descriptor)
        local result;
        local exitsInCache = _cache: contains(Cache.OHLC_DATASOURCE, descriptor.id);

        if exitsInCache == true then
            local datasource = _cache: extract(Cache.OHLC_DATASOURCE, descriptor.id, true);
            local booleanResult = {};
            booleanResult[QuikDataManager.JAVA_CLASS_FIELD] = "BooleanResult";
            booleanResult["value"] = datasource.instance:Close();
            result = _createResult(self, true, booleanResult, nil);
        else
            local msg = "OHLC DATASOURCE FOR: " ..
                    "{ id = " .. descriptor.id ..
                    ", classCode = " .. descriptor.classCode ..
                    ", securityCode = " .. descriptor.securityCode ..
                    ", interval = " .. descriptor.interval;
            if descriptor.parameter ~= nil then
                msg = msg .. ", parameter = " .. descriptor.parameter;
            end;
            msg = msg .. "} DOES EXISTS IN CACHE! CREATE DATASOURCE FIRST!";
            result = _createResult(self, false, nil, msg);
        end;

        _logger: debug("CALL: closeDatasource(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getDatasourceSise(descriptor)
        local result;
        local exitsInCache = _cache: contains(Cache.OHLC_DATASOURCE, descriptor.id);

        if exitsInCache == true then
            local datasource = _cache: extract(Cache.OHLC_DATASOURCE, descriptor.id, false);
            local dsSize = {};
            dsSize[QuikDataManager.JAVA_CLASS_FIELD] = "LongResult";
            dsSize["value"] = datasource.instance:Size();
            result = _createResult(self, true, dsSize, nil);
        else
            local msg = "OHLC DATASOURCE FOR: " ..
                    "{ id = " .. descriptor.id ..
                    ", classCode = " .. descriptor.classCode ..
                    ", securityCode = " .. descriptor.securityCode ..
                    ", interval = " .. descriptor.interval;
            if descriptor.parameter ~= nil then
                msg = msg .. ", parameter = " .. descriptor.parameter;
            end;
            msg = msg .. "} DOES EXISTS IN CACHE! CREATE DATASOURCE FIRST!";
            result = _createResult(self, false, nil, msg);
        end;

        _logger: debug("CALL: getDatasourceSise(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getCandle(descriptor, candleIndex)
        local result;
        local msg;
        local exitsInCache = _cache: contains(Cache.OHLC_DATASOURCE, descriptor.id);

        if exitsInCache == true then
            local datasource = _cache: extract(Cache.OHLC_DATASOURCE, descriptor.id, false);
            local dsSize = datasource.instance:Size();
            if candleIndex < 1 and candleIndex > dsSize then
                msg = "CANDLE INDEX IS OUT OF RANGE! CURRENT DATASOURCE SIZE IS: " .. dsSize;
                result = _createResult(self, false, nil, msg);
            else
                local candle = {};
                candle[QuikDataManager.JAVA_CLASS_FIELD] = "Candle";

                -- Set prices and volume
                candle["open"]   = datasource.instance:O(candleIndex);
                candle["high"]   = datasource.instance:H(candleIndex);
                candle["low"]    = datasource.instance:L(candleIndex);
                candle["close"]  = datasource.instance:C(candleIndex);
                candle["volume"] = datasource.instance:V(candleIndex);

                -- Set date and time
                local dateTime = datasource.instance:T(candleIndex);
                dateTime[QuikDataManager.JAVA_CLASS_FIELD] = "DateTime";
                candle["datetime"] = dateTime;

                result = _createResult(self, true, candle, nil);
            end;
        else
            msg = "OHLC DATASOURCE FOR: " ..
                                                "{ id = " .. descriptor.id ..
                                                ", classCode = " .. descriptor.classCode ..
                                                ", securityCode = " .. descriptor.securityCode ..
                                                ", interval = " .. descriptor.interval;
            if descriptor.parameter ~= nil then
                msg = msg .. ", parameter = " .. descriptor.parameter;
            end;
            msg = msg .. "} DOES EXISTS IN CACHE! CREATE DATASOURCE FIRST!";
            result = _createResult(self, false, nil, msg);
        end;

        _logger: debug("CALL: getCandle(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getCandlesSet(descriptor, firstIndex, lastIndex)
        local result;
        local error;
        local exitsInCache = _cache: contains(Cache.OHLC_DATASOURCE, descriptor.id);

        if exitsInCache == true then
            local datasource = _cache: extract(Cache.OHLC_DATASOURCE, descriptor.id, false);
            local dsSize = datasource.instance:Size();

            local isValidFirstIndex = firstIndex >= 1;
            local isValidLastIndex = lastIndex <= dsSize;
            local isValidRange = isValidFirstIndex == true and isValidLastIndex == true;
            isValidRange = isValidRange and (firstIndex <= lastIndex);

            if isValidRange == true then

                local ohlcDataFrame = {};
                ohlcDataFrame[QuikDataManager.JAVA_CLASS_FIELD] = "OhlcDataFrame";
                ohlcDataFrame["records"] = {};

                local isValidRangeSize = (lastIndex - (firstIndex - 1)) <= QuikDataManager.MAX_RANGE_SIZE;
                local rangeEnd = lastIndex;
                if isValidRangeSize == false then
                    rangeEnd = firstIndex + (QuikDataManager.MAX_RANGE_SIZE - 1);
                end;

                local candle;
                local dateTime;
                for i = 1, rangeEnd, 1 do
                    candle = {};
                    candle[QuikDataManager.JAVA_CLASS_FIELD] = "Candle";

                    -- Set prices and volume
                    candle["open"]   = datasource.instance:O(i);
                    candle["high"]   = datasource.instance:H(i);
                    candle["low"]    = datasource.instance:L(i);
                    candle["close"]  = datasource.instance:C(i);
                    candle["volume"] = datasource.instance:V(i);

                    -- Set date and time
                    dateTime = datasource.instance:T(i);
                    dateTime[QuikDataManager.JAVA_CLASS_FIELD] = "DateTime";
                    candle["datetime"] = dateTime;
                    _logger: debug("\nCANDLE(" .. i .. "): " .. _jsonParser: encode_pretty(candle));
                    ohlcDataFrame.records[i] = candle;
                end;
                result = _createResult(self, true, ohlcDataFrame, nil);

            elseif isValidFirstIndex == false and isValidLastIndex == false then
                error = "Invalid range! First and last index both have invalid values. " ..
                        "First index has to be greater then or equal to 1! " ..
                        "Last index has to be less or equal then current size of datasource (total count of candles). " ..
                        " Currently, datasource with id : " .. descriptor.id .. " contains only " .. dsSize .. " candles.";
                result = _createResult(self, false, nil, error);
            elseif isValidFirstIndex == false then
                error = "First index out of range! First index has to be greater then or equal to 1!";
                result = _createResult(self, false, nil, error);
            elseif isValidLastIndex == false then
                error = "Last index out of range! " ..
                        "Last index has to be less or equal then current size of datasource (total count of candles). " ..
                        " Currently, datasource with id : " .. descriptor.id .. " contains only " .. dsSize .. " candles.";
                result = _createResult(self, false, nil, error);
            elseif firstIndex > lastIndex then
                error = "Invalid range! First index is greater then last index!";
                result = _createResult(self, false, nil, error);
            end;
        else
            local msg = "OHLC DATASOURCE FOR: " ..
                    "{ id = " .. descriptor.id ..
                    ", classCode = " .. descriptor.classCode ..
                    ", securityCode = " .. descriptor.securityCode ..
                    ", interval = " .. descriptor.interval;
            if descriptor.parameter ~= nil then
                msg = msg .. ", parameter = " .. descriptor.parameter;
            end;
            msg = msg .. "} DOES EXISTS IN CACHE! CREATE DATASOURCE FIRST!";
            result = _createResult(self, false, nil, msg);
        end;

        _logger: debug("CALL: getCandlesSet(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getSecurityInfo(classCode, securityCode)
        local result;
        local security = getSecurityInfo(classCode, securityCode);

        if security ~= nil then
            for key, value in pairs(security) do
                if _isDateTime(self, value) then
                    value[QuikDataManager.JAVA_CLASS_FIELD] = "DateTime";
                elseif type(value) == "string" then
                    security[key] = value:gsub('"','');
                end;
            end;
            security[QuikDataManager.JAVA_CLASS_FIELD] = "Security";
            result = _createResult(self, true, security, nil);
        else
            local error = "CALL OF " .. "getSecurityInfo" ..
                    "( classCode = " .. classCode ..
                    ", securityCode = " .. securityCode ..
                    ") RETURNS NIL VALUE! INVALID CLASS CODE OR SECURITY CODE!";
            result = _createResult(self, false, nil, error);
        end;

        _logger: debug("CALL: getSecurityInfo(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getClassInfo(classCode)
        local result;
        local securityClass = getClassInfo(classCode);

        if securityClass ~= nil then
            for key, value in pairs(securityClass) do
                if type(value) == "string" then
                    securityClass[key] = value:gsub('"','');
                end;
            end;
            securityClass[QuikDataManager.JAVA_CLASS_FIELD] = "SecurityClass";
            result = _createResult(self, true, securityClass, nil);
        else
            local error = "CALL OF " .. "getClassInfo" ..
                    "( classCode = " .. classCode ..
                    ") RETURNS NIL VALUE! INVALID CLASS CODE!";
            result = _createResult(self, false, nil, error);
        end;

        _logger: debug("CALL: getClassInfo(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getClassesList()
        local result;
        local codesString = getClassesList();

        if codesString ~= nil and codesString ~= "" then
            local codes = {};
            codes[QuikDataManager.JAVA_CLASS_FIELD] = "CodesArray";
            codes["separator"] = ",";
            codes["codesString"] = codesString;
            result = _createResult(self, true, codes, nil);
        else
            local error = "CALL OF " .. "getClassesList()"
                    .. " RETURNS NIL VALUE!";
            result = _createResult(self, false, nil, error);
        end;

        _logger: debug("CALL: getClassesList(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getClassSecuritiesList(classCode, firstIndex, lastIndex)
        local result;
        local error;

        -- Reurns empty string if such class does not exists
        local secsCodesString = getClassSecurities(classCode);
        if secsCodesString ~= nil and secsCodesString ~= ""  then

            local secsCount = 0;
            local secsTable = _split(self, secsCodesString, ",");
            for key, value in pairs(secsTable) do
                secsCount = secsCount + 1;
            end

            local isValidFirstIndex = firstIndex >= 1;
            local isValidLastIndex = lastIndex <= secsCount;
            local isValidRange = isValidFirstIndex == true and isValidLastIndex == true;
            isValidRange = isValidRange and (firstIndex <= lastIndex);

            if isValidRange == true then

                local isValidRangeSize = (lastIndex - (firstIndex - 1)) <= QuikDataManager.MAX_RANGE_SIZE;
                local rangeEnd = lastIndex;
                if isValidRangeSize == false then
                    rangeEnd = firstIndex + (QuikDataManager.MAX_RANGE_SIZE - 1);
                end;

                local security;
                local secsInRange = {};
                for i = 1, rangeEnd, 1 do
                    security = secsTable[i];
                    _logger: debug("\nADD SECURITY CODE TO RESULT" .. security);
                    secsInRange[i] = security;
                end;

                local codes = {};
                codes[QuikDataManager.JAVA_CLASS_FIELD] = "CodesArray";
                codes["separator"] = ",";
                codes["codesString"] = table.concat(secsInRange, ",");
                result = _createResult(self, true, codes, nil);

            elseif isValidFirstIndex == false and isValidLastIndex == false then
                error = "Invalid range! First and last index both have invalid values. " ..
                        "First index has to be greater then or equal to 1! " ..
                        "Last index has to be less or equal then number of securities in class. " ..
                        " Currently, security's class: " .. classCode .. " contains only " .. secsCount .. " securities.";
                result = _createResult(self, false, nil, error);
            elseif isValidFirstIndex == false then
                error = "First index out of range! First index has to be greater then or equal to 1!";
                result = _createResult(self, false, nil, error);
            elseif isValidLastIndex == false then
                error = "Last index out of range! " ..
                        "Last index has to be less or equal then number of securities in class. " ..
                        " Currently, security's class: " .. classCode .. " contains only " .. secsCount .. " securities.";
                result = _createResult(self, false, nil, error);
            elseif firstIndex > lastIndex then
                error = "Invalid range! First index is greater then last index!";
                result = _createResult(self, false, nil, error);
            end;
        else
            result["status"] = "FAILED";
            local error = "CALL OF " .. "getClassSecurities" ..
                    "( classCode = " .. classCode ..
                    ") RETURNS EMPTY STRING! INVALID CLASS CODE!";
            result = _createResult(self, false, nil, error);
        end;

        _logger: debug("CALL: getClassSecuritiesList(...) FINISHED WITH RESULT: "
                                                    .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getTableInfo(tableName)
        local result;
        local itemClass = TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP[tableName];
        if itemClass ~= nil then
            local tableInfo = {};
            tableInfo[QuikDataManager.JAVA_CLASS_FIELD] = "QuikTableInfo";
            tableInfo["tableType"] = tableName;
            tableInfo["rowsCount"] =  getNumberOf(tableName);
            result = _createResult(self, true, tableInfo, nil);
        else
            result = _createResult(self, false, nil, "UNSUPPORTED TABLE!");
        end;

        _logger: debug("CALL OF getTableInfo( tableName = " .. tableName
                .. ") FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getTableItem(tableName, itemIndex)
        local result;
        local error;
        local itemClass = TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP[tableName];

        if itemClass ~= nil then
            local rowsCount = getNumberOf(tableName);
            if itemIndex > (rowsCount - 1) then
                error = "Index out of range! Currently, table: " .. tableName .. " contains only " .. rowsCount .. " rows.";
                result = _createResult(self, false, nil, error);
            else
                local item = getItem(tableName, itemIndex);
                if item ~= nil then
                    if tableName == "client_codes" then
                        -- In this case item is string, containing code of the client with index itemIndex
                        local clientCode = item;
                        item = {};
                        item["code"] = clientCode;
                        item[QuikDataManager.JAVA_CLASS_FIELD] = itemClass;
                    else
                        for key, value in pairs(item) do
                            if _isDateTime(self, value) then
                                value[QuikDataManager.JAVA_CLASS_FIELD] = "DateTime";
                            elseif type(value) == "string" then
                                item[key] = value:gsub('"','');
                            end;
                        end;
                        item[QuikDataManager.JAVA_CLASS_FIELD] = itemClass;
                    end;
                    result = _createResult(self, true, item, nil);
                else
                    error = "CALL OF getItem( tableName = " .. tableName .. ", index = " .. itemIndex .. ") RETURNS NIL VALUE!";
                    result = _createResult(self, false, nil, error);
                end;
            end;

        else
            result = _createResult(self, false, nil, "UNSUPPORTED TABLE!");
        end;

        _logger: debug("CALL OF getItem( tableName = " .. tableName
                                    .. ", index = " .. itemIndex
                                    .. ") FINISHED WITH RESULT: "
                                    .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getTableItems(tableName, firstIndex, lastIndex)
        local result;
        local error;
        local itemClass = TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP[tableName];

        if itemClass ~= nil then
            local rowsCount = getNumberOf(tableName);
            local isValidFirstIndex = firstIndex >= 0;
            local isValidLastIndex = lastIndex <= (rowsCount - 1);
            local isValidRange = isValidFirstIndex == true and isValidLastIndex == true;
            isValidRange = isValidRange and (firstIndex <= lastIndex);

            if isValidRange == true then
                local dataFrame = {};
                dataFrame[QuikDataManager.JAVA_CLASS_FIELD] = "QuikDataFrame";
                dataFrame["records"] = {};

                local isValidRangeSize = ( (lastIndex + 1) - firstIndex ) <= QuikDataManager.MAX_RANGE_SIZE;
                local rangeEnd = lastIndex;
                if isValidRangeSize == false then
                    rangeEnd = firstIndex + (QuikDataManager.MAX_RANGE_SIZE - 1);
                end;

                for i = 0, rangeEnd, 1 do
                    local item = getItem(tableName, i);
                    if tableName == "client_codes" then
                        -- In this case item is string, containing code of the client with index itemIndex
                        local clientCode = item;
                        item = {};
                        item["code"] = clientCode;
                        item[QuikDataManager.JAVA_CLASS_FIELD] = itemClass;
                    else
                        for key, value in pairs(item) do
                            if _isDateTime(self, value) then
                                value[QuikDataManager.JAVA_CLASS_FIELD] = "DateTime";
                            elseif type(value) == "string" then
                                item[key] = value:gsub('"','');
                            end;
                        end;
                        item[QuikDataManager.JAVA_CLASS_FIELD] = itemClass;
                    end;
                    _logger: debug("\nITEM: " .. _jsonParser: encode_pretty(item));
                    dataFrame.records[i + 1] = item;
                end;
                result = _createResult(self, true, dataFrame, nil);
            elseif isValidFirstIndex == false and isValidLastIndex == false then
                error = "Invalid range! First and last index both have invalid values. " ..
                        "First index has to be greater then or equal to 0! " ..
                        "Last index has to be less or equal then number of rows - 1. " ..
                        " Currently, table: " .. tableName .. " contains only " .. rowsCount .. " rows.";
                result = _createResult(self, false, nil, error);
            elseif isValidFirstIndex == false then
                error = "First index out of range! First index has to be greater then or equal to 0!";
                result = _createResult(self, false, nil, error);
            elseif isValidLastIndex == false then
                error = "Last index out of range! " ..
                        "Last index has to be less or equal then number of rows - 1. " ..
                        " Currently, table: " .. tableName .. " contains only " .. rowsCount .. " rows.";
                result = _createResult(self, false, nil, error);
            elseif firstIndex > lastIndex then
                error = "Invalid range! First index is greater then last index!";
                result = _createResult(self, false, nil, error);
            end;

        else
            result = _createResult(self, false, nil, "UNSUPPORTED TABLE!");
        end;

        _logger: debug("CALL OF getTableItems( tableName = " .. tableName
                                                                .. ", index = " .. firstIndex
                                                                .. ", index = " .. lastIndex
                                                                .. ") FINISHED WITH RESULT: "
                                                                .. _jsonParser: encode_pretty(result));
        return result;
    end;


    function QuikDataManager : getMaxCountOfLotsInOrder(classCode, securityCode, clientCode, account, price, isBuy, isMarket)
        local result;
        local qty, comission = CalcBuySell(classCode, securityCode, clientCode, account, price, isBuy, isMarket);
        if qty == nil or comission == nil then
            local error = "CALL OF " .. "CalcBuySell" ..
                    "( classCode = " .. classCode ..
                    ", securityCode = " .. securityCode ..
                    ", securityCode = " .. clientCode ..
                    ", securityCode = " .. account ..
                    ", securityCode = " .. price ..
                    ", securityCode = " .. isBuy ..
                    ", securityCode = " .. isMarket ..
                    ") RETURNS NIL VALUE FOR: ";
            if qty == nil and comission == nil then
                error  = error .. "qty, comission";
            elseif qty == nil then
                error  = error .. "qty";
            else
                error  = error .. "comission";
            end;
            error = error .. "!";
            result = _createResult(self, false, nil, error);
        else
            local countOfLots = {};
            countOfLots["qty"] = qty;
            countOfLots["comission"] = comission;
            countOfLots[QuikDataManager.JAVA_CLASS_FIELD] = "CountOfLots";
            result = _createResult(self, true, countOfLots, nil);
        end;
        _logger: debug("CALL: getMaxCountOfLotsInOrder(...) FINISHED WITH RESULT: "
                .. _jsonParser: encode_pretty(result));
        return result;
    end;

    function QuikDataManager : getDepo(clientCode, firmId, securityCode, account)
        local result;
        local depo = getDepo(clientCode, firmId, securityCode, account);

        if depo ~= nil then
            depo[QuikDataManager.JAVA_CLASS_FIELD] = "Depo";
            result = _createResult(self, true, depo, nil);
        else
            local error = "CALL OF " .. "getDepo" ..
                                        "( clientCode = "   .. clientCode ..
                                        ", firmId = "       .. firmId ..
                                        ", securityCode = " .. securityCode ..
                                        ", account = "      .. account .. ") RETURNS NIL!";
            result = _createResult(self, false, nil, error);
        end;

        _logger: debug("CALL: getDepo(...) FINISHED WITH RESULT: "
                                        .. _jsonParser: encode_pretty(result));
        return result;
    end;

    function QuikDataManager : getMoney(clientCode, firmId, tag, currencyCode)
        local result;
        local money = getMoney(clientCode, firmId, tag, currencyCode);

        if money ~= nil then
            money[QuikDataManager.JAVA_CLASS_FIELD] = "Money";
            result = _createResult(self, true, money, nil);
        else
            local error = "CALL OF " .. "getMoney" ..
                                        "( clientCode = "   .. clientCode ..
                                        ", firmId = "       .. firmId ..
                                        ", tag = "          .. tag ..
                                        ", account = "      .. currencyCode .. ") RETURNS NIL!";
            result = _createResult(self, false, nil, error);
        end;

        _logger: debug("CALL: getMoney(...) FINISHED WITH RESULT: "
                                            .. _jsonParser: encode_pretty(result));
        return result;
    end;


-- End of QuikDataManager class declaration
return QuikDataManager;