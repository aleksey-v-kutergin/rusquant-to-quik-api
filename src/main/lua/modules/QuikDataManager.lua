---------------------------------------------------------------------------------------
-- QuikDataManager.lua
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
-- Date: 11.07.2017
---------------------------------------------------------------------------------------
-- This module provides functionality for ...
--
---------------------------------------------------------------------------------------

-- src.modules.CacheManager
local QuikDataManager = {};

local logger;
local jsonParser;
local cacheManager;


---------------------------------------------------------------------------------------
-- This is because one cannot hold nil for key in hash table
-- At the java side one could serialize all fields of the Transaction class, including fields without referense to data (null value)
-- In resulting JSON-object it looks like "someFiledName": null.
-- But when converting from JSON to lua table, JSON parser does not include null-field to resulting table
-- Thus, one needs some kind of etalon set of feilds. This set must not be out of date!
--
---------------------------------------------------------------------------------------
local TRANSACTION_FIELDS =
{
    "ACCOUNT",
    "BALANCE",
    "BROKERREF",
    "CLASSCODE",
    "CLIENT_CODE",
    "EXCHANGE_CODE",
    "FIRM_ID",
    "FLAGS",
    "ORDER_NUM",
    "PRICE",
    "QUANTITY",
    "RESULT_MSG",
    "SECCODE",
    "SERVER_TRANS_ID",
    "TIME",
    "TRANS_ID",
    "UID",
    "ACTION",
    "STATUS",
    "OPERATION",
    "TYPE",
    "COMMENT",
    "MODE"
}


local TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP = {};
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



---------------------------------------------------------------------------------------
-- Setter for external dependencies
--
---------------------------------------------------------------------------------------

function QuikDataManager : setLogger(externalLogger)
    logger = externalLogger;
    logger.writeToLog(this, "LOGGER WITHIN QUIK DATA MANAGER");
end;

function QuikDataManager : setJsonParser(parser)
    jsonParser = parser;
    logger.writeToLog(this, "JSON PASER WITHIN QUIK DATA MANAGER!");
end;


function QuikDataManager : setCacheManager(cache)
    cacheManager = cache;
    logger.writeToLog(this, "CACHE MANAGER WITHIN QUIK DATA MANAGER!");
end;





---------------------------------------------------------------------------------------
-- Section with server-side functionality for transaction processing.
--
---------------------------------------------------------------------------------------

local TRANSACTION_REPLAY_RETRY_COUNT = 10000000;

local function prepareTransactionArgs(transaction)
    local args = {};
    for key, value in pairs(transaction) do
        if type(value) == "string" then
            if key ~= "MODE" then
                args[key] = value;
            end;
        else
            args[key] = tostring(value);
        end;
    end;
    return args;
end;



local function getTransactionResult(transaction)
    local transId = transaction["TRANS_ID"];
    local replay = cacheManager.get(this, "TRANS_REPLAY", transId, true)

    local result = {};
    local counter = 0;
    while replay == nil do
        if counter % 100 == 0 then
            logger.writeToLog(this, "CHECKING FOR EXISTANCE OF TRANSACTION REPLAY IN CACHE!");
        end;

        replay = cacheManager.get(this, "TRANS_REPLAY", transId, true);
        if counter > TRANSACTION_REPLAY_RETRY_COUNT then
            result["status"] = "FAILED";
            result["error"] = "EXCEEDING RETRY COUNT FOR WAITING OF OCCURANCE OF THE TRANSACTION REPLAY IN CACHE! TRANSACTION REPLAY HAS NOT OCCURED YET!";
            return result;
        end;
        counter = counter + 1;
    end

    result["status"] = "SUCCESS";
    result["trans_replay"] = replay
    return result;
end;



function QuikDataManager : sendTransaction(transaction)
    -- Remove class information from object
    transaction["type"] = nil;
    local args = prepareTransactionArgs(transaction);
    logger.writeToLog(this, "\nCALL SEND TRANSACTION WITH ARGS: " .. jsonParser: encode_pretty(args) .. "\n");
    local error = sendTransaction(args);
    local result = {};
    if error ~= nil and error ~= '' then
        logger.writeToLog(this, "\nSEND TRANSACTION FAILS WITH ERROR: " .. error .. "\n");
        result["status"] = "FAILED";
        result["error"] = error;
        return result;
    end;

    local transactionReesult = getTransactionResult(transaction);
    if transactionReesult["status"] == "FAILED" then
        return transactionReesult;
    end;

    result["status"] = "SUCCESS";
    result["error"] = nil;
    local replay = transactionReesult["trans_replay"];
    logger.writeToLog(this, "\nREPLAY FOR TRANSACTION FROM CACHE: " .. jsonParser: encode_pretty(replay) .. "\n");

    logger.writeToLog(this, "MERGING TRANSACTION AND TRANSACTION REPLAY WITH PRIORITY TO REPLAY\n");
    local mergedReplay = {};
    local lowerCaseFieldName;
    for index, fieldName in ipairs(TRANSACTION_FIELDS) do
        lowerCaseFieldName = string.lower(fieldName);
        if replay[lowerCaseFieldName] ~= nil then
            mergedReplay[fieldName] = replay[lowerCaseFieldName];
        elseif transaction[fieldName] ~= nil then
            mergedReplay[fieldName] = transaction[fieldName];
        end;
    end;

    mergedReplay["type"] = "Transaction";
    result["transReplay"] = mergedReplay;
    logger.writeToLog(this, "\nFINAL TRANSACTION REPLAY: " .. jsonParser: encode_pretty(result) .. "\n");
    return result;
end;



---------------------------------------------------------------------------------------
-- Section with server-side functionality for orders processing.
--
---------------------------------------------------------------------------------------


local ORDER_REPLAY_RETRY_COUNT = 10000000;

function QuikDataManager : getOrder(orderNumber, noWait)
    local order = cacheManager.get(this, "ORDER", orderNumber, true)
    local result = {};

    if noWait == true then
        if order ~= nil then
            result["status"] = "SUCCESS";
            result["order"] = order;
            return result;
        else
            result["status"] = "FAILED";
            result["error"] = "ORDER HAS NOT OCCURED YET!";
            return result;
        end;
    end;

    local counter = 0;
    while order == nil do
        order = cacheManager.get(this, "ORDER", orderNumber, true);
        if counter > ORDER_REPLAY_RETRY_COUNT then
            result["status"] = "FAILED";
            result["error"] = "EXCEEDING RETRY COUNT FOR WAITING OF OCCURANCE OF THE ORDER IN CACHE! ORDER HAS NOT OCCURED YET!";
            return result;
        end;
        counter = counter + 1;
    end

    result["status"] = "SUCCESS";
    result["order"] = order;
    return result;
end;



---------------------------------------------------------------------------------------
-- Section with server-side functionality for trades processing.
--
---------------------------------------------------------------------------------------


function QuikDataManager : getTrades(orderNumber)
    local result = {};
    result["status"] = "SUCCESS";

    local trades = cacheManager.get(this, "TRADE", orderNumber, true);
    local tradesDataFrame = {};
    tradesDataFrame["type"] = "TradesDataFrame";
    tradesDataFrame["records"] = trades;
    result["tradesDataFrame"] = tradesDataFrame;
    logger.writeToLog(this, "\nFINAL TRADES LIST: " .. jsonParser: encode_pretty(tradesDataFrame) .. "\n");
    return result;
end;



---------------------------------------------------------------------------------------
-- Section with server-side functionality for access to parameters of current trading table.
--
---------------------------------------------------------------------------------------

function QuikDataManager : subscribeParameter(id, classCode, securityCode, parameterName)
    local result = {};

    local exitsInCache = cacheManager.contains(this, "PARAMETER_DESCRIPTOR", id);
    if exitsInCache == false then
        local subscribeResult = ParamRequest(classCode, securityCode, parameterName);
        if subscribeResult == true then
            local descriptor = {};
            descriptor["type"] = "ParameterDescriptor";
            descriptor["id"] = id;
            descriptor["classCode"] = classCode;
            descriptor["securityCode"] = securityCode;
            descriptor["parameterName"] = parameterName;
            cacheManager.cache(this, "PARAMETER_DESCRIPTOR", descriptor)

            result["status"] = "SUCCESS";
            result["descriptor"] = descriptor;
        else
            result["status"] = "FAILED";
            result["error"] = "CALL OF " .. "ParamRequest" ..
                    "( classCode = " .. classCode ..
                    ", securityCode = " .. securityCode ..
                    ", parameterName = " .. parameterName ..
                    ") RETURNS FALSE!";
        end;
    else
        result["status"] = "FAILED";
        result["error"] = "SUBSCRIPTION TO PARAMETER: " ..
                "{ id = " .. id ..
                ", classCode = " .. classCode ..
                ", securityCode = " .. securityCode ..
                ", parameterName = " .. parameterName ..
                "} ALREADY EXISTS IN CACHE!";
    end;

    return result;
end;


function QuikDataManager : unsubscribeParameter(descriptor)
    local result = {};

    local id = descriptor.id;
    local classCode = descriptor.classCode;
    local securityCode = descriptor.securityCode;
    local parameterName = descriptor.parameterName;

    local exitsInCache = cacheManager.contains(this, "PARAMETER_DESCRIPTOR", id);
    if exitsInCache == true then
        local cancelationResult = CancelParamRequest(classCode, securityCode, parameterName);
        if cancelationResult == true then
            local booleanResult = {};
            booleanResult["type"] = "BooleanResult";
            booleanResult["value"] = true;

            cacheManager.get(this, "PARAMETER_DESCRIPTOR", id, true);
            result["status"] = "SUCCESS";
            result["booleanResult"] = booleanResult;
        else
            result["status"] = "FAILED";
            result["error"] = "CALL OF " .. "CancelParamRequest" ..
                    "( classCode = " .. classCode ..
                    ", securityCode = " .. securityCode ..
                    ", parameterName = " .. parameterName ..
                    ") RETURNS FALSE!";
        end;
    else
        result["status"] = "FAILED";
        result["error"] = "SUBSCRIPTION TO PARAMETER: " ..
                "{ id = " .. id ..
                ", classCode = " .. classCode ..
                ", securityCode = " .. securityCode ..
                ", parameterName = " .. parameterName ..
                "} DOES EXISTS IN CACHE! SUBSCRIBE TO PARAMETER FIRST!";
    end;

    return result;
end;



function QuikDataManager : getTradingParameter(classCode, securityCode, parameterName, version)
    local result = {};

    local parameter;
    local functionName;
    if version == "EX1" then
        functionName = "getParamEx";
        parameter = getParamEx(classCode,  securityCode, parameterName);
    elseif version == "EX2" then
        functionName = "getParamEx2";
        parameter = getParamEx2(classCode,  securityCode, parameterName);
    else
        result["status"] = "FAILED";
        result["error"] = "INVALID VERSION OF THE getParamEx() FUNCTION: " .. version .. "!";
    end;

    if parameter ~= nil then
        if parameter.result == "1" then
            result["status"] = "SUCCESS";
            parameter["type"] = "TradingParameter";
            result["tradingParameter"] = parameter;
        else
            result["status"] = "FAILED";
            result["error"] = "CALL OF " .. functionName ..
                                                "( classCode = " .. classCode ..
                                                ", securityCode = " .. securityCode ..
                                                ", parameterName = " .. parameterName ..
                                                ") ENDS WITH ERROR (RESULT = 0)!";
        end;
    else
        result["status"] = "FAILED";
        result["error"] = "CALL OF " .. functionName ..
                                            "( classCode = " .. classCode ..
                                            ", securityCode = " .. securityCode ..
                                            ", parameterName = " .. parameterName ..
                                            ") RETURNS NIL VALUE!";
    end;

    return result;
end;


---------------------------------------------------------------------------------------
-- Section with server-side functionality to access order book data
--
---------------------------------------------------------------------------------------


function QuikDataManager : subscribeQuotes(id, classCode, securityCode)
    local result = {};

    local exitsInCache = cacheManager.contains(this, "QUOTES_DESCRIPTOR", id);
    if exitsInCache == false then
        local subscribeResult = Subscribe_Level_II_Quotes(classCode, securityCode);
        if subscribeResult == true then
            local descriptor = {};
            descriptor["type"] = "QuotesDescriptor";
            descriptor["id"] = id;
            descriptor["classCode"] = classCode;
            descriptor["securityCode"] = securityCode;
            cacheManager.cache(this, "QUOTES_DESCRIPTOR", descriptor)

            result["status"] = "SUCCESS";
            result["descriptor"] = descriptor;
        else
            result["status"] = "FAILED";
            result["error"] = "CALL OF " .. "Subscribe_Level_II_Quotes" ..
                    "( classCode = " .. classCode ..
                    ", securityCode = " .. securityCode ..
                    ") RETURNS FALSE!";
        end;
    else
        result["status"] = "FAILED";
        result["error"] = "SUBSCRIPTION TO QUOTES FOR: " ..
                "{ id = " .. id ..
                ", classCode = " .. classCode ..
                ", securityCode = " .. securityCode ..
                "} ALREADY EXISTS IN CACHE!";
    end;

    return result;
end;


function QuikDataManager : unsubscribeQuotes(descriptor)
    local result = {};

    local id = descriptor.id;
    local classCode = descriptor.classCode;
    local securityCode = descriptor.securityCode;

    local exitsInCache = cacheManager.contains(this, "QUOTES_DESCRIPTOR", id);
    if exitsInCache == true then
        local cancelationResult = Unsubscribe_Level_II_Quotes(classCode, securityCode);
        if cancelationResult == true then
            local booleanResult = {};
            booleanResult["type"] = "BooleanResult";
            booleanResult["value"] = true;

            cacheManager.get(this, "QUOTES_DESCRIPTOR", id, true);
            result["status"] = "SUCCESS";
            result["booleanResult"] = booleanResult;
        else
            result["status"] = "FAILED";
            result["error"] = "CALL OF " .. "Unsubscribe_Level_II_Quotes" ..
                    "( classCode = " .. classCode ..
                    ", securityCode = " .. securityCode ..
                    ") RETURNS FALSE!";
        end;
    else
        result["status"] = "FAILED";
        result["error"] = "SUBSCRIPTION TO QUOTES FOR: " ..
                "{ id = " .. id ..
                ", classCode = " .. classCode ..
                ", securityCode = " .. securityCode ..
                "} DOES EXISTS IN CACHE! SUBSCRIBE TO QUOTES FIRST!";
    end;

    return result;
end;


function QuikDataManager : isSubscribedToQuotes(descriptor)
    local result = {};

    local id = descriptor.id;
    local classCode = descriptor.classCode;
    local securityCode = descriptor.securityCode;

    local exitsInCache = cacheManager.contains(this, "QUOTES_DESCRIPTOR", id);
    if exitsInCache == true then
        local booleanResult = {};
        booleanResult["type"] = "BooleanResult";
        booleanResult["value"] = IsSubscribed_Level_II_Quotes(classCode, securityCode);

        result["status"] = "SUCCESS";
        result["booleanResult"] = booleanResult;
    else
        result["status"] = "FAILED";
        result["error"] = "SUBSCRIPTION TO QUOTES FOR: " ..
                "{ id = " .. id ..
                ", classCode = " .. classCode ..
                ", securityCode = " .. securityCode ..
                "} DOES EXISTS IN CACHE! SUBSCRIBE TO QUOTES FIRST!";
    end;

    return result;
end;


local function processQuotes(depth, quotes)
    local orderBookSide = {};
    local orderBookLevel;

    for i = 1, depth, 1 do
        orderBookLevel = {};
        orderBookLevel["type"] = "OrderBookLevel";

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


function QuikDataManager : getQuotes(classCode, securityCode)
    local result = {};

    local quotesSnapshot = getQuoteLevel2(classCode, securityCode);
    if quotesSnapshot ~= nil then
        logger.writeToLog(this, "\nSOURCE QUOTES SNAPSHOT FROM getQuoteLevel2: " .. jsonParser: encode_pretty(quotesSnapshot) .. "\n");
        local orderBook = {};
        orderBook["type"] = "OrderBook";

        orderBook["bid_count"] = tonumber(quotesSnapshot.bid_count);
        orderBook["offer_count"] = tonumber(quotesSnapshot.offer_count);
        orderBook["bid"] = processQuotes(tonumber(quotesSnapshot.bid_count), quotesSnapshot.bid);
        orderBook["offer"] = processQuotes(tonumber(quotesSnapshot.offer_count), quotesSnapshot.offer);
        logger.writeToLog(this, "\nRESULTING ORDER BOOK OBJECT: " .. jsonParser: encode_pretty(orderBook) .. "\n");

        result["status"] = "SUCCESS";
        result["orderBook"] = orderBook;
    else
        result["status"] = "FAILED";
        result["error"] = "CALL OF " .. "getQuoteLevel2" ..
                "( classCode = " .. classCode ..
                ", securityCode = " .. securityCode ..
                ") RETURNS NIL! SUBCRIBE TO QUOTES OR OPEN ORDER BOOK FOR SECURITY OF INTEREST!";
    end;

    return result;
end;



---------------------------------------------------------------------------------------
-- Section with server-side functionality to access OHLC price data
--
---------------------------------------------------------------------------------------

function QuikDataManager : createDatasource(id, classCode, securityCode, interval, parameter)
    local result = {};
    local exitsInCache = cacheManager.contains(this, "OHLC_DATASOURCE", id);
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
        logger.writeToLog(this, msg);
        if parameter == nil then
            ds, error = CreateDataSource(classCode, securityCode, interval);
        else
            ds, error = CreateDataSource(classCode, securityCode, interval, parameter);
        end;

        if error ~= "" and error ~= nil then
            result["status"] = "FAILED";
            local msg = "CALL OF " .. "CreateDataSource" ..
                    "{ id = " .. id ..
                    ", classCode = " .. classCode ..
                    ", securityCode = " .. securityCode ..
                    ", interval = " .. interval;
            if parameter ~= nil then
                msg = msg .. ", parameter = " .. parameter;
            end;
            msg = msg .. "} RETURN ERROR: " .. error;
            result["error"] = msg;
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
                logger.writeToLog(this, msg);
                sleep(1);
            end;

            local descriptor = {};
            descriptor["type"] = "DatasourceDescriptor";
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
                cacheManager.cache(this, "OHLC_DATASOURCE", datasource)

                result["status"] = "SUCCESS";
                result["descriptor"] = descriptor;
            else
                result["status"] = "FAILED";
                result["error"] = "CALL OF "
                        .. "SetEmptyCallback() FOR DATASOURCE: "
                        .. jsonParser: encode_pretty(descriptor)
                        .. "\nRETURNS FALSE! DATA UPDATES FROM SERVER ARE NOT AVAILABLE!";
            end;
        end;
    else
        result["status"] = "FAILED";
        local msg = "OHLC DATASOURCE FOR: " ..
                "{ id = " .. id ..
                ", classCode = " .. classCode ..
                ", securityCode = " .. securityCode ..
                ", interval = " .. interval;
        if parameter ~= nil then
            msg = msg .. ", parameter = " .. parameter;
        end;
        msg = msg .. "} ALREADY EXISTS IN CACHE!";
        result["error"] = msg;
    end;

    return result;
end;


function QuikDataManager : closeDatasource(descriptor)
    local result = {};
    local exitsInCache = cacheManager.contains(this, "OHLC_DATASOURCE", descriptor.id);
    if exitsInCache == true then
        local datasource = cacheManager.get(this, "OHLC_DATASOURCE", descriptor.id, true);
        result["status"] = "SUCCESS";

        local booleanResult = {};
        booleanResult["type"] = "BooleanResult";
        booleanResult["value"] = datasource.instance:Close();
        result["booleanResult"] = booleanResult;
    else
        result["status"] = "FAILED";
        local msg = "OHLC DATASOURCE FOR: " ..
                "{ id = " .. descriptor.id ..
                ", classCode = " .. descriptor.classCode ..
                ", securityCode = " .. descriptor.securityCode ..
                ", interval = " .. descriptor.interval;
        if descriptor.parameter ~= nil then
            msg = msg .. ", parameter = " .. descriptor.parameter;
        end;
        msg = msg .. "} DOES EXISTS IN CACHE! CREATE DATASOURCE FIRST!";
        result["error"] = msg;
    end;

    return result;
end;


function QuikDataManager : getDatasourceSise(descriptor)
    local result = {};
    local exitsInCache = cacheManager.contains(this, "OHLC_DATASOURCE", descriptor.id);
    if exitsInCache == true then
        local datasource = cacheManager.get(this, "OHLC_DATASOURCE", descriptor.id, false);
        result["status"] = "SUCCESS";

        local dsSize = {};
        dsSize["type"] = "LongResult";
        dsSize["value"] = datasource.instance:Size();
        result["dsSize"] = dsSize;
    else
        result["status"] = "FAILED";
        local msg = "OHLC DATASOURCE FOR: " ..
                "{ id = " .. descriptor.id ..
                ", classCode = " .. descriptor.classCode ..
                ", securityCode = " .. descriptor.securityCode ..
                ", interval = " .. descriptor.interval;
        if descriptor.parameter ~= nil then
            msg = msg .. ", parameter = " .. descriptor.parameter;
        end;
        msg = msg .. "} DOES EXISTS IN CACHE! CREATE DATASOURCE FIRST!";
        result["error"] = msg;
    end;

    return result;
end;



function QuikDataManager : getCandle(descriptor, candleIndex)
    local result = {};
    local exitsInCache = cacheManager.contains(this, "OHLC_DATASOURCE", descriptor.id);
    if exitsInCache == true then
        local datasource = cacheManager.get(this, "OHLC_DATASOURCE", descriptor.id, false);
        local dsSize = datasource.instance:Size();
        if candleIndex < 1 and candleIndex > dsSize then
            result["status"] = "FAILED";
            result["error"] = "CANDLE INDEX IS OUT OF RANGE! CURRENT DATASOURCE SIZE IS: " .. dsSize;
        else
            local candle = {};
            candle["type"] = "Candle";

            -- Set prices and volume
            candle["open"]   = datasource.instance:O(candleIndex);
            candle["high"]   = datasource.instance:H(candleIndex);
            candle["low"]    = datasource.instance:L(candleIndex);
            candle["close"]  = datasource.instance:C(candleIndex);
            candle["volume"] = datasource.instance:V(candleIndex);

            -- Set date and time
            local dateTime = datasource.instance:T(candleIndex);
            dateTime["type"] = "DateTime";
            candle["datetime"] = dateTime;

            result["status"] = "SUCCESS";
            result["candle"] = candle;
        end;
    else
        result["status"] = "FAILED";
        local msg = "OHLC DATASOURCE FOR: " ..
                                            "{ id = " .. descriptor.id ..
                                            ", classCode = " .. descriptor.classCode ..
                                            ", securityCode = " .. descriptor.securityCode ..
                                            ", interval = " .. descriptor.interval;
        if descriptor.parameter ~= nil then
            msg = msg .. ", parameter = " .. descriptor.parameter;
        end;
        msg = msg .. "} DOES EXISTS IN CACHE! CREATE DATASOURCE FIRST!";
        result["error"] = msg;
    end;

    logger.writeToLog(this, "\nCANDLE RESULT: " .. jsonParser: encode_pretty(result) .. "\n");
    return result;
end;



function QuikDataManager : getAllCandles(descriptor)
    local result = {};
    local exitsInCache = cacheManager.contains(this, "OHLC_DATASOURCE", descriptor.id);
    if exitsInCache == true then
        local datasource = cacheManager.get(this, "OHLC_DATASOURCE", descriptor.id, false);
        local dsSize = datasource.instance:Size();

        local ohlcDataFrame = {};
        ohlcDataFrame["type"] = "OhlcDataFrame";
        ohlcDataFrame["records"] = {};

        local candle;
        local dateTime;
        if dsSize > 10 then dsSize = 10; end;
        for i = 1, dsSize, 1 do
            candle = {};
            candle["type"] = "Candle";

            -- Set prices and volume
            candle["open"]   = datasource.instance:O(i);
            candle["high"]   = datasource.instance:H(i);
            candle["low"]    = datasource.instance:L(i);
            candle["close"]  = datasource.instance:C(i);
            candle["volume"] = datasource.instance:V(i);

            -- Set date and time
            dateTime = datasource.instance:T(i);
            dateTime["type"] = "DateTime";
            candle["datetime"] = dateTime;
            logger.writeToLog(this, "\nCANDLE(" .. i .. "): " .. jsonParser: encode_pretty(candle) .. "\n");
            ohlcDataFrame.records[i] = candle;
        end;

        result["ohlcDataFrame"] = ohlcDataFrame;
        result["status"] = "SUCCESS";
    else
        result["status"] = "FAILED";
        local msg = "OHLC DATASOURCE FOR: " ..
                "{ id = " .. descriptor.id ..
                ", classCode = " .. descriptor.classCode ..
                ", securityCode = " .. descriptor.securityCode ..
                ", interval = " .. descriptor.interval;
        if descriptor.parameter ~= nil then
            msg = msg .. ", parameter = " .. descriptor.parameter;
        end;
        msg = msg .. "} DOES EXISTS IN CACHE! CREATE DATASOURCE FIRST!";
        result["error"] = msg;
    end;

    logger.writeToLog(this, "\nALL CANDLES RESULT: " .. jsonParser: encode_pretty(result) .. "\n");
    return result;
end;









---------------------------------------------------------------------------------------
-- Section with server-side functionality for access to quik tables.
--
---------------------------------------------------------------------------------------


local function isDateTime(object)
    local isDate = true;
    isDate = isDate and type(object) == "table";
    isDate = isDate and object.day ~= nil;
    isDate = isDate and object.hour ~= nil;
    isDate = isDate and object.mcs ~= nil;
    isDate = isDate and object.min ~= nil;
    isDate = isDate and object.month ~= nil;
    isDate = isDate and object.ms ~= nil;
    isDate = isDate and object.sec ~= nil;
    isDate = isDate and object.week_day ~= nil;
    isDate = isDate and object.year ~= nil;
    return isDate;
end;


function QuikDataManager : getTableItem(tableName, itemIndex)
    logger.writeToLog(this, "\nTRYING TO GET ITEM WITH INDEX: " .. itemIndex ..  " OF QUIK TABLE: " .. tableName .. "\n");
    local result = {};
    local itemClass = TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP[tableName];
    if itemClass ~= nil then

        local rowsCount = getNumberOf(tableName);
        if itemIndex > (rowsCount - 1) then
            result["status"] = "FAILED";
            result["error"] = "Index out of range! Currently, table: " .. tableName .. " contains only " .. rowsCount .. " rows.";
        else
            local item = getItem(tableName, itemIndex);
            if item ~= nil then
                if tableName == "client_codes" then
                    -- In this case item is string, containing code of the client with index itemIndex
                    local clientCode = item;
                    item = {};
                    item["code"] = clientCode;
                    item["type"] = itemClass;
                else
                    for key, value in pairs(item) do
                        if isDateTime(value) then
                            value["type"] = "DateTime";
                        elseif type(value) == "string" then
                            item[key] = value:gsub('"','');
                        end;
                    end;
                    item["type"] = itemClass;
                end;

                result["status"] = "SUCCESS";
                result["item"] = item;
            else
                result["status"] = "FAILED";
                result["error"] = "CALL OF getItem( tableName = " .. tableName .. ", index = " .. itemIndex .. ") RETURNS NIL VALUE!";
            end;
        end;

    else
        result["status"] = "FAILED";
        result["error"] = "UNKNOWN TABLE!";
    end;
    logger.writeToLog(this, "\nRESULT: " .. jsonParser: encode_pretty(result) .. "\n");
    return result;
end;


function QuikDataManager : getTableItems(tableName)
    logger.writeToLog(this, "\nTRYING TO GET ALL ITEMS OF QUIK TABLE: " .. tableName .. "\n");
    local result = {};
    local itemClass = TABLE_NAME_TO_JAVA_ITEM_CLASS_MAP[tableName];
    if itemClass ~= nil then

        local dataFrame = {};
        dataFrame["type"] = "QuikDataFrame";
        dataFrame["records"] = {};

        local rowsCount = getNumberOf(tableName);
        for i = 0, (rowsCount - 1), 1 do
            local item = getItem(tableName, i);
            if tableName == "client_codes" then
                -- In this case item is string, containing code of the client with index itemIndex
                local clientCode = item;
                item = {};
                item["code"] = clientCode;
                item["type"] = itemClass;
            else
                for key, value in pairs(item) do
                    if isDateTime(value) then
                        value["type"] = "DateTime";
                    elseif type(value) == "string" then
                        item[key] = value:gsub('"','');
                    end;
                end;
                item["type"] = itemClass;
            end;
            logger.writeToLog(this, "\nITEM: " .. jsonParser: encode_pretty(item) .. "\n");
            dataFrame.records[i + 1] = item;
        end;

        result["tableItems"] = dataFrame;
        result["status"] = "SUCCESS";
    else
        result["status"] = "FAILED";
        result["error"] = "UNKNOWN TABLE!";
    end;
    logger.writeToLog(this, "\nRESULT: " .. jsonParser: encode_pretty(result) .. "\n");
    return result;
end;


function QuikDataManager : getSecurityInfo(classCode, securityCode)
    local result = {};
    local security = getSecurityInfo(classCode, securityCode);
    if security ~= nil then
        for key, value in pairs(security) do
            if isDateTime(value) then
                value["type"] = "DateTime";
            elseif type(value) == "string" then
                security[key] = value:gsub('"','');
            end;
        end;
        security["type"] = "Security";

        result["security"] = security;
        result["status"] = "SUCCESS";
    else
        result["status"] = "FAILED";
        result["error"] = "CALL OF " .. "getSecurityInfo" ..
                "( classCode = " .. classCode ..
                ", securityCode = " .. securityCode ..
                ") RETURNS NIL VALUE! INVALID CLASS CODE OR SECURITY CODE!";
    end;
    return result;
end;



function QuikDataManager : getClassInfo(classCode)
    local result = {};
    local securityClass = getClassInfo(classCode);
    if securityClass ~= nil then
        for key, value in pairs(securityClass) do
            if type(value) == "string" then
                securityClass[key] = value:gsub('"','');
            end;
        end;
        securityClass["type"] = "SecurityClass";

        result["securityClass"] = securityClass;
        result["status"] = "SUCCESS";
    else
        result["status"] = "FAILED";
        result["error"] = "CALL OF " .. "getClassInfo" ..
                "( classCode = " .. classCode ..
                ") RETURNS NIL VALUE! INVALID CLASS CODE!";
    end;
    return result;
end;


function QuikDataManager : getClassesList()
    local result = {};
    local codesString = getClassesList();
    if codesString ~= nil then
        local codes = {};
        codes["type"] = "CodesArray";
        codes["separator"] = ",";
        codes["codesString"] = codesString;

        result["codes"] = codes;
        result["status"] = "SUCCESS";
    else
        result["status"] = "FAILED";
        result["error"] = "CALL OF " .. "getClassesList()"
                                     .. " RETURNS NIL VALUE!";
    end;
    return result;
end;


function QuikDataManager : getClassSecuritiesList(classCode)
    local result = {};
    -- Reurns empty string if such class does not exists
    local codesString = getClassSecurities(classCode);
    if codesString ~= nil then
        local codes = {};
        codes["type"] = "CodesArray";
        codes["separator"] = ",";
        codes["codesString"] = codesString;

        result["codes"] = codes;
        result["status"] = "SUCCESS";
    else
        result["status"] = "FAILED";
        result["error"] = "CALL OF " .. "getClassSecurities" ..
                "( classCode = " .. classCode ..
                ") RETURNS NIL VALUE! INVALID CLASS CODE!";
    end;
    return result;
end;


function QuikDataManager : getMaxCountOfLotsInOrder(classCode, securityCode, clientCode, account, price, isBuy, isMarket)
    local result = {};
    local qty, comission = CalcBuySell(classCode, securityCode, clientCode, account, price, isBuy, isMarket);
    if qty == nil or comission == nil then
        result["status"] = "FAILED";
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
        result["error"] = error .. "!"
    else
        local countOfLots = {};
        countOfLots["qty"] = qty;
        countOfLots["comission"] = comission;
        countOfLots["type"] = "CountOfLots";

        result["countOfLots"] = countOfLots;
        result["status"] = "SUCCESS";
    end;
    return result;
end;


-- End of CacheManager module
return QuikDataManager;
