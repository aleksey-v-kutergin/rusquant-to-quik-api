---------------------------------------------------------------------------------------
-- RequestManager - class contains logic for requests and responses processing.
-- Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.com>
-- Company: rusquant.ru
--
---------------------------------------------------------------------------------------
local class = require "middleclass";

-- Class declaration
local RequestManager = class("RequestManager");

    -- Private fields (Their scope is Logger.lua file)
    local _logger;
    local _jsonParser;
    local _quikDataManager;
    local _isTerminalConnected;


    -- Class constructor
    function RequestManager : initialize(logger, jsonParser, quikDataManager)
        _logger = logger;
        _jsonParser = jsonParser;
        _quikDataManager = quikDataManager;
        _isTerminalConnected = false;
    end;


    -- Private Methods:
    local _validateRequest = function(self, request)
        local targetCount = 0;
        for key, value in pairs(request) do
            if ( key == "type"
                    or key == "subject"
                    or key == "id"
                    or key == "time"
                    or key == "body" ) and value ~= nil then
                targetCount = targetCount + 1;
            end;
        end;
        if targetCount == 5 then return true end;
        return false;
    end;


    local _createDefaultResponse = function(self, request)
        local response = {};
        response["requestId"]                           =   request["id"];
        response["sendingTimeOfRequestAtClient"]        =   request["time"];
        response["timeOfReceiptOfRequestAtServer"]      =   os.time();
        response["sendingTimeOfResponseAtServer"]       =   os.time();
        response["timeOfReceiptOfResponseAtClient"]     =   os.time();
        response["type"]                                =   request["type"];
        response["subject"]                             =   request["subject"];
        response["status"]                              =   "EMPTY_RESPONSE";
        response["error"]                               =   "NO_ERROR";
        response["body"]                                =   {};
        return response;
    end;


    local _createResponse = function(self, request, bodyClass, bodyFiledName, result)
        local response = _createDefaultResponse(self, request);
        local responseBody = {};
        responseBody["@class"] = bodyClass;

        if result.status ~= "FAILED" then
            response["status"] = "SUCCESS";
            responseBody[bodyFiledName] = result.value;
        else
            response["status"] = "FAILED";
            response["error"] = result.error;
        end;

        response["body"] = responseBody;
        response["sendingTimeOfResponseAtServer"] = os.time();
        return response;
    end;


    local function _createErrorResponse(self, request, error)
        local response = _createDefaultResponse(self, request);
        response["status"] = "FAILED";
        response["error"] = error;
        response["sendingTimeOfResponseAtServer"] = os.time();
        return response;
    end;


    local _createECHOResponse = function(self, request)
        local reuqestBody = request.body;
        local result = _quikDataManager: getEcho(reuqestBody.echoMessage);
        return _createResponse(self, request, "EchoResponseBody", "echo", result);
    end;


    local _createConnectionStateResponse = function(self, request)
        local result = _quikDataManager: getConnectionState();
        return _createResponse(self, request, "ConnectionSateResponseBody", "connectionState", result);
    end;


    local _createInfoParameterResponse = function(self, request)
        local reuqestBody = request.body;
        local result = _quikDataManager: getInfoParameterValue(reuqestBody.infoParamType);
        return _createResponse(self, request, "InfoParameterResponseBody", "infoParameter", result);
    end;


    local _createTransactionResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.transaction ~= nil then
            local result = _quikDataManager: sendTransaction(reuqestBody.transaction);
            return _createResponse(self, request, "TransactionResponseBody", "transactionReplay", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createOrderResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.orderNumber ~= nil then
            local result = _quikDataManager: getOrder(reuqestBody.orderNumber, true);
            return _createResponse(self, request, "OrderResponseBody", "order", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createTradesResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.orderNumber ~= nil then
            local result = _quikDataManager: getTrades(reuqestBody.orderNumber);
            return _createResponse(self, request, "TradesResponseBody", "tradesDataFrame", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createTableInfoResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.tableType ~= nil then
            local result = _quikDataManager: getTableInfo(reuqestBody.tableType);
            return _createResponse(self, request, "QuikTableInfoResponseBody", "tableInfo", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createTableItemResponse = function(self, request)
        local reuqestBody = request.body;
        local isValid = reuqestBody.tableType ~= nil;
        isValid = isValid and reuqestBody.itemIndex ~= nil;
        if isValid == true then
            local result = _quikDataManager: getTableItem(reuqestBody.tableType, reuqestBody.itemIndex);
            return _createResponse(self, request, "QuikTableItemResponseBody", "item", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createTableItemsResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.tableType ~= nil then
            local result = _quikDataManager: getTableItems(reuqestBody.tableType);
            return _createResponse(self, request, "QuikTableItemsResponseBody", "items", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createSubcribeParameterResponse = function(self, request)
        local reuqestBody = request.body;
        local isValid = reuqestBody.id ~= nil;
        isValid = isValid and reuqestBody.classCode ~= nil;
        isValid = isValid and reuqestBody.securityCode ~= nil;
        isValid = isValid and reuqestBody.parameterName ~= nil;

        if isValid == true then
            local result = _quikDataManager: subscribeParameter(reuqestBody.id,
                                                                reuqestBody.classCode,
                                                                reuqestBody.securityCode,
                                                                reuqestBody.parameterName);
            return _createResponse(self, request, "SubscribeParameterResponseBody", "descriptor", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createUnsubcribeParameterResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.descriptor ~= nil then
            local result = _quikDataManager: unsubscribeParameter(reuqestBody.descriptor);
            return _createResponse(self, request, "UnsubscribeParameterResponseBody", "result", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createTradingParameterResponse = function(self, request)
        local reuqestBody = request.body;
        local isValid = reuqestBody.classCode ~= nil;
        isValid = isValid and reuqestBody.securityCode ~= nil;
        isValid = isValid and reuqestBody.parameter ~= nil;

        if isValid == true then
            local result = _quikDataManager: getTradingParameter(reuqestBody.classCode,
                                                                reuqestBody.securityCode,
                                                                reuqestBody.parameter,
                                                                reuqestBody.version);
            return _createResponse(self, request, "TradingParameterResponseBody", "tradingParameter", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createTradeDateResponse = function(self, request)
        local result = _quikDataManager: getTradeDate();
        return _createResponse(self, request, "TradeDateResponseBody", "tradeDate", result);
    end;


    local _createSecurityInfoResponse = function(self, request)
        local reuqestBody = request.body;
        local isValid = reuqestBody.classCode ~= nil;
        isValid = isValid and reuqestBody.securityCode ~= nil;

        if isValid == true then
            local result = _quikDataManager: getSecurityInfo(reuqestBody.classCode, reuqestBody.securityCode);
            return _createResponse(self, request, "SecurityInfoResponseBody", "security", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createClassInfoResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.classCode ~= nil then
            local result = _quikDataManager: getClassInfo(reuqestBody.classCode);
            return _createResponse(self, request, "SecurityClassInfoResponseBody", "securityClass", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createClassesListResponse = function(self, request)
        local result = _quikDataManager: getClassesList();
        return _createResponse(self, request, "ClassesListResponseBody", "codes", result);
    end;


    local _createClassSecuritiesResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.classCode ~= nil then
            local result = _quikDataManager: getClassSecuritiesList(reuqestBody.classCode);
            return _createResponse(self, request, "ClassSecuritiesResponseBody", "codes", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createMaxLotCountResponse = function(self, request)
        local reuqestBody = request.body;
        local isValid = reuqestBody.classCode ~= nil;
        isValid = isValid and reuqestBody.securityCode ~= nil;
        isValid = isValid and reuqestBody.clientCode ~= nil;
        isValid = isValid and reuqestBody.account ~= nil;
        isValid = isValid and reuqestBody.price ~= nil;
        isValid = isValid and reuqestBody.isBuy ~= nil;
        isValid = isValid and reuqestBody.isMarket ~= nil;

        if isValid == true then
            local result = _quikDataManager: getMaxCountOfLotsInOrder(reuqestBody.classCode,
                                                                        reuqestBody.securityCode,
                                                                        reuqestBody.clientCode,
                                                                        reuqestBody.account,
                                                                        reuqestBody.price,
                                                                        reuqestBody.isBuy,
                                                                        reuqestBody.isMarket);
            return _createResponse(self, request, "MaxCountOfLotsResponseBody", "countOfLots", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createSubcribeQuotesResponse = function(self, request)
        local reuqestBody = request.body;
        local isValid = reuqestBody.id ~= nil;
        isValid = isValid and reuqestBody.classCode ~= nil;
        isValid = isValid and reuqestBody.securityCode ~= nil;

        if isValid == true then
            local result = _quikDataManager: subscribeQuotes(reuqestBody.id,
                                                                reuqestBody.classCode,
                                                                reuqestBody.securityCode);
            return _createResponse(self, request, "SubscribeQuotesResponseBody", "descriptor", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createUnsubcribeQuotesResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.descriptor ~= nil then
            local result = _quikDataManager: unsubscribeQuotes(reuqestBody.descriptor);
            return _createResponse(self, request, "UnsubscribeQuotesResponseBody", "result", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createIsSubscribedToQuotesResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.descriptor ~= nil then
            local result = _quikDataManager: isSubscribedToQuotes(reuqestBody.descriptor);
            return _createResponse(self, request, "IsSubscribeQuotesResponseBody", "result", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createQuotesResponse = function(self, request)
        local reuqestBody = request.body;
        local isValid = true;
        isValid = isValid and reuqestBody.classCode ~= nil;
        isValid = isValid and reuqestBody.securityCode ~= nil;

        if isValid == true then
            local result = _quikDataManager: getQuotes(reuqestBody.classCode, reuqestBody.securityCode);
            return _createResponse(self, request, "QuotesResponseBody", "orderBook", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createCreateDatasourceResponse = function(self, request)
        local reuqestBody = request.body;
        local isValid = reuqestBody.id ~= nil;
        isValid = isValid and reuqestBody.classCode ~= nil;
        isValid = isValid and reuqestBody.securityCode ~= nil;
        isValid = isValid and reuqestBody.interval ~= nil;

        if isValid == true then
            local result = _quikDataManager: createDatasource(reuqestBody.id,
                                                                reuqestBody.classCode,
                                                                reuqestBody.securityCode,
                                                                reuqestBody.interval,
                                                                reuqestBody.parameter);
            return _createResponse(self, request, "CreateDatasourceResponseBody", "descriptor", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createCloseDatasourceResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.descriptor ~= nil then
            local result = _quikDataManager: closeDatasource(reuqestBody.descriptor);
            return _createResponse(self, request, "CloseDatasourceResponseBody", "result", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createDatasourceSizeResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.descriptor ~= nil then
            local result = _quikDataManager: getDatasourceSise(reuqestBody.descriptor);
            return _createResponse(self, request, "DatasourceSizeResponseBody", "result", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createSingleCandleResponse = function(self, request)
        local reuqestBody = request.body;
        local isValid = reuqestBody.descriptor ~= nil;
        isValid = isValid and reuqestBody.candleIndex ~= nil;

        if isValid == true then
            local result = _quikDataManager: getCandle(reuqestBody.descriptor, reuqestBody.candleIndex);
            return _createResponse(self, request, "SingleCandleResponseBody", "candle", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _createAllCandlesResponse = function(self, request)
        local reuqestBody = request.body;
        if reuqestBody.descriptor ~= nil then
            local result = _quikDataManager: getAllCandles(reuqestBody.descriptor);
            return _createResponse(self, request, "AllCandlesResponseBody", "ohlcDataFrame", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;

    local _createDepoResponse = function(self, request)
        local reuqestBody = request.body;
        local isValid = true;
        isValid = isValid and reuqestBody.clientCode ~= nil;
        isValid = isValid and reuqestBody.firmId ~= nil;
        isValid = isValid and reuqestBody.securityCode ~= nil;
        isValid = isValid and reuqestBody.account ~= nil;

        if isValid == true then
            local result = _quikDataManager: getDepo(reuqestBody.clientCode,
                                                     reuqestBody.firmId,
                                                     reuqestBody.securityCode,
                                                     reuqestBody.account);
            return _createResponse(self, request, "DepoResponseBody", "depo", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;

    local _createMoneyResponse = function(self, request)
        local reuqestBody = request.body;
        local isValid = true;
        isValid = isValid and reuqestBody.clientCode ~= nil;
        isValid = isValid and reuqestBody.firmId ~= nil;
        isValid = isValid and reuqestBody.tag ~= nil;
        isValid = isValid and reuqestBody.currencyCode ~= nil;

        if isValid == true then
            local result = _quikDataManager: getMoney(reuqestBody.clientCode,
                                                      reuqestBody.firmId,
                                                      reuqestBody.tag,
                                                      reuqestBody.currencyCode);
            return _createResponse(self, request, "MoneyResponseBody", "money", result);
        else
            return _createErrorResponse(self, request, "INVALID REQUEST PARAMETERS!");
        end;
    end;


    local _processGET = function(self, request)
        local subject = request.subject;
        if subject == "ECHO" then
            return _createECHOResponse(self, request);
        elseif subject == "CONNECTION_SATE" then
            return _createConnectionStateResponse(self, request);
        elseif subject == "INFO_PARAMETER" then
            return _createInfoParameterResponse(self, request);
        elseif subject == "ORDER" then
            return _createOrderResponse(self, request);
        elseif subject == "TRADE" then
            return _createTradesResponse(self, request);
        elseif subject == "TABLE_INFO" then
            return _createTableInfoResponse(self, request);
        elseif subject == "TABLE_ITEM" then
            return _createTableItemResponse(self, request);
        elseif subject == "TABLE_ITEMS" then
            return _createTableItemsResponse(self, request);
        elseif subject == "TRADING_PARAMETER" then
            return _createTradingParameterResponse(self, request);
        elseif subject == "TRADE_DATE" then
            return _createTradeDateResponse(self, request);
        elseif subject == "SECURITY_INFO" then
            return _createSecurityInfoResponse(self, request);
        elseif subject == "MAX_LOT_COUNT" then
            return _createMaxLotCountResponse(self, request);
        elseif subject == "CLASS_INFO" then
            return _createClassInfoResponse(self, request);
        elseif subject == "CLASSES_LIST" then
            return _createClassesListResponse(self, request);
        elseif subject == "CLASS_SECURITIES" then
            return _createClassSecuritiesResponse(self, request);
        elseif subject == "IS_SUBSCRIBED_QUOTES" then
            return _createIsSubscribedToQuotesResponse(self, request);
        elseif subject == "QUOTES" then
            return _createQuotesResponse(self, request);
        elseif subject == "DATASOURCE_SIZE" then
            return _createDatasourceSizeResponse(self, request);
        elseif subject == "SINGLE_CANDLE" then
            return _createSingleCandleResponse(self, request);
        elseif subject == "ALL_CANDLES" then
            return _createAllCandlesResponse(self, request);
        elseif subject == "DEPO" then
            return _createDepoResponse(self, request);
        elseif subject == "MONEY" then
            return _createMoneyResponse(self, request);
        else
            return _createErrorResponse(self, request, "Unsupported GET request subject: " .. request.subject);
        end;
    end;


    local _processPOST = function(self, request)
        local subject = request.subject;
        if subject == "TRANSACTION" then
            return _createTransactionResponse(self, request);
        elseif subject == "SUBSCRIBE_TRADING_PARAMETER" then
            return _createSubcribeParameterResponse(self, request);
        elseif subject == "UNSUBSCRIBE_TRADING_PARAMETER" then
            return _createUnsubcribeParameterResponse(self, request);
        elseif subject == "SUBSCRIBE_QUOTES" then
            return _createSubcribeQuotesResponse(self, request);
        elseif subject == "UNSUBSCRIBE_QUOTES" then
            return _createUnsubcribeQuotesResponse(self, request);
        elseif subject == "CREATE_DATASOURCE" then
            return _createCreateDatasourceResponse(self, request);
        elseif subject == "CLOSE_DATASOURCE" then
            return _createCloseDatasourceResponse(self, request);
        else
            return _createErrorResponse(self, request, "Unsupported POST request subject: " .. request.subject);
        end;
    end;


    -- Public methods
    function RequestManager : processRequest(rawJSONRequest)
        local request = _jsonParser: decode(rawJSONRequest);
        local response;
        local rawJSONResponse;
        if request ~= nil and _validateRequest(self, request) then
            local type = request.type;
            if type == "GET" then
                response = _processGET(self, request);
            elseif type == "POST" then
                response = _processPOST(self, request);
            else
                return _createErrorResponse(self, request, "Unsupported request type: " .. request.type);
            end;
        end;
        if response ~= nil then
            rawJSONResponse = _jsonParser: encode(response);
        end;
        return rawJSONResponse;
    end;


    function RequestManager : setQUIKToBrokerConnectionFlag(isConnected)
        _isTerminalConnected = isConnected;
    end;


-- End of RequestManager class declaration
return RequestManager;