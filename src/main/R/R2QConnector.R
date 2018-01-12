#############################################################################
#
# R to Quik terminal connector (R6 class wrapper for J2QConnector).
#
# Correct rJava system path setup (adjust to your R version):
#
#   Add system variable: R_HOME = C:\R\R-3.3.2
#   Add to path (x32 system): %R_HOME%\bin\i386;%R_HOME%\library\rJava\jri\i386
#   Add to path (x64 system): %R_HOME%\bin\x64;%R_HOME%\library\rJava\jri\x64
#
#   And of couse (adjust to your version of the java SDK\jre):
#   Add system variable JAVA_HOME = C:\Program Files\Java\jdk1.8.0_112
#   Add to path: %JAVA_HOME%\bin
#
# Author: Aleksey Kutergin, Rusquant
#
#############################################################################
library("rJava");
library("R6")

# add relative path calculation here!
jarPath <- "D:\\Development\\desktop\\rusquant\\test\\package\\java\\r2q-api.jar";

# Init JVM, datatypes and add jar to classpath
.jinit();
.jaddClassPath(jarPath);

# Load java datatypes
Long <- J("java.lang.Long");
Integer <- J("java.lang.Integer");
Double <- J("java.lang.Double");
Boolean <- J("java.lang.Boolean");


R2QConnector <- R6Class("R2QConnector",

    portable = TRUE,

    # Private fields and methods
    private = list(connector = NA),

    # Public fields and methods
    public = list(

        initialize = function() {
            print("Initializing R2QConnector...");
            private$connector <- new(J("ru.rusquant.api.impl.J2QuikConnector"));
        },

        connect = function() {
            return(private$connector$connect());
        },

        getConnectErrorMessage = function() {
            return(private$connector$getConnectErrorMessage())
        },

        disconnect = function() {
            private$connector$disconnect();
        },



        # =================== Quik API inplementation ===================

        isConnected = function() {
            result <- private$connector$isConnected();
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(FALSE);
            } else {
                return(result$isConnected());
            }
        },

        getEcho = function(message) {
            result <- private$connector$getEcho(message);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result$getEchoAnswer());
            }
        },

        getInfoParam = function(paramName) {
            result <- private$connector$getInfoParam(paramName);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result$getParameterValue());
            }
        },

        sendTransaction = function(transaction) {
            result <- private$connector$sendTransaction(transaction);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getOrder = function(orderNumber) {
            result <- private$connector$getOrder(new(Long, toString(orderNumber)));
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getTrades = function(orderNumber) {
            result <- private$connector$getTrades(new(Long, toString(orderNumber)));
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getNumberOfRows = function(tableName) {
            result <- private$connector$getNumberOfRows(tableName);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getItem = function(tableName, index) {
            result <- private$connector$getItem(tableName, new(Integer, toString(index)));
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getItems = function(tableName, firstIndex, lastIndex) {
            first <- new(Integer, toString(firstIndex));
            last <- new(Integer, toString(lastIndex));
            result <- private$connector$getItems(tableName, first, last);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getParamEx = function(classCode, securityCode, paramName) {
            result <- private$connector$getParamEx(classCode, securityCode, paramName);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getParamEx2 = function(classCode, securityCode, paramName) {
            result <- private$connector$getParamEx2(classCode, securityCode, paramName);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        subscribeParameter = function(classCode, securityCode, paramName) {
            result <- private$connector$subscribeParameter(classCode, securityCode, paramName);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        unsubscribeParameter = function(descriptor) {
            result <- private$connector$unsubscribeParameter(descriptor);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        searchItems = function(tableName, startIndex, endIndex, params) {
            start <- new(Long, toString(startIndex));
            end <- new(Long, toString(endIndex));
            result <- private$connector$searchItems(tableName, start, end, params);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getClassesList = function() {
            result <- private$connector$getClassesList();
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getClassInfo = function(classCode) {
            result <- private$connector$getClassInfo(classCode);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getClassSecurities = function(classCode, firstIndex, lastIndex) {
            first <- new(Integer, toString(firstIndex));
            last <- new(Integer, toString(lastIndex));
            result <- private$connector$getClassSecurities(classCode, first, last);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getMoney = function(clientCode, firmId, tag, currencyCode) {
            result <- private$connector$getMoney(clientCode, firmId, tag, currencyCode);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getMoneyEx = function(clientCode, firmId, tag, currencyCode, limitKind) {
            kind <- new(Integer, toString(limitKind));
            result <- private$connector$getMoneyEx(clientCode, firmId, tag, currencyCode, kind);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getDepo = function(clientCode, firmId, securityCode, account) {
            result <- private$connector$getDepo(clientCode, firmId, securityCode, account);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getDepoEx = function(clientCode, firmId, tag, account, limitKind) {
            kind <- new(Integer, toString(limitKind));
            result <- private$connector$getDepoEx(clientCode, firmId, tag, account, kind);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getFuturesLimit = function(firmId, accountId, limitType, currencyCode) {
            type <- new(Integer, toString(limitType));
            result <- private$connector$getFuturesLimit(firmId, accountId, type, currencyCode);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getFuturesHolding = function(firmId, accountId, securityCode, posType) {
            type <- new(Integer, toString(posType));
            result <- private$connector$getFuturesHolding(firmId, accountId, securityCode, type);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getSecurityInfo = function(classCode, securityCode) {
            result <- private$connector$getSecurityInfo(classCode, securityCode);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getTradeDate = function() {
            result <- private$connector$getTradeDate();
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getQuoteLevel2 = function(classCode, securityCode) {
            result <- private$connector$getQuoteLevel2(classCode, securityCode);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        subscribeQuotes = function(classCode, securityCode) {
            result <- private$connector$subscribeQuotes(classCode, securityCode);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        unsubscribeQuotes = function(descriptor) {
            result <- private$connector$unsubscribeQuotes(descriptor);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        isSubscribedToQuotes = function(descriptor) {
            result <- private$connector$isSubscribedToQuotes(descriptor);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result$getValue());
            }
        },

        getMaxCountOfLotsInOrder = function(classCode, securityCode, clientCode, account, price, isBuy, isMarket) {
            jPrice <- new(Double, toString(price));
            jIsBuy <- new(Boolean, toString( tolower(isBuy) ));
            jIsMarket <- new(Boolean, toString( tolower(isMarket) ));
            result <- private$connector$getMaxCountOfLotsInOrder(classCode, securityCode, clientCode, account, jPrice, jIsBuy, jIsMarket);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getPortfolioInfo = function(firmId, clientCode) {
            result <- private$connector$getPortfolioInfo(firmId, clientCode);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getPortfolioInfoEx = function(firmId, clientCode, limitKind) {
            kind <- new(Integer, toString(limitKind));
            result <- private$connector$getPortfolioInfoEx(firmId, clientCode, kind);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getBuySellInfo = function(firmId, clientCode, classCode, securityCode, price) {
            jPrice <- new(Double, toString(price));
            result <- private$connector$getBuySellInfo(firmId, clientCode, classCode, securityCode, jPrice);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getBuySellInfoEx = function(firmId, clientCode, classCode, securityCode, price) {
            jPrice <- new(Double, toString(price));
            result <- private$connector$getBuySellInfoEx(firmId, clientCode, classCode, securityCode, jPrice);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        createDataSource = function(classCode, securityCode, interval, parameter = NULL) {
            result <- NULL;
            if(is.null(parameter)) {
                result <- private$connector$createDataSource(classCode, securityCode, interval);
            } else {
                result <- private$connector$createDataSource(classCode, securityCode, interval, parameter);
            }
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        closeDatasource = function(datasource) {
            result <- private$connector$closeDatasource(datasource);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getDatasourceSize = function(datasource) {
            result <- private$connector$getDatasourceSize(datasource);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getOHLCPrice = function(datasource, index) {
            jIndex <- new(Long, toString(index));
            result <- private$connector$getOHLCPrice(datasource, jIndex);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        },

        getOHLCPrices = function(datasource, firstIndex, lastIndex) {
            first <- new(Integer, toString(firstIndex));
            last <- new(Integer, toString(lastIndex));
            result <- private$connector$getOHLCPrices(datasource, first, last);
            if(.jinstanceof(result, J("ru.rusquant.data.quik.ErrorObject"))) {
                print( result$getErrorMessage() );
                return(NA);
            } else {
                return(result);
            }
        }
        #End of public section
    )

# End R2QConnector declaration
);