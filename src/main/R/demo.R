#############################################################################
#
# Just demo script
# Author: Aleksey Kutergin, Rusquant
#
#############################################################################
source("R2QConnector.R");
params <- c("VERSION", "TRADEDATE", "SERVERTIME", "LASTRECORDTIME", "NUMRECORDS", "LASTRECORD","LATERECORD",
"CONNECTION", "IPADDRESS", "IPPORT", "IPCOMMENT", "SERVER", "SESSIONID", "USER", "USERID", "ORG", "MEMORY",
"LOCALTIME", "CONNECTIONTIME", "MESSAGESSENT", "ALLSENT", "BYTESSENT", "BYTESPERSECSENT", "MESSAGESRECV", "BYTESRECV",
"ALLRECV", "BYTESPERSECRECV", "AVGSENT", "AVGRECV", "LASTPINGTIME", "LASTPINGDURATION", "AVGPINGDURATION", "MAXPINGTIME", "MAXPINGDURATION");

test <- R2QConnector$new();
if(test$connect()) {
    print("Successfully connect from R to quik");

    # Test code

    # Just echo from quik
    print("Try to get echo from quik terminal...");
    print(test$getEcho("Hellooooooo from R!!!"));
    print("");
    print("");

    print("Try to obtain info about quik terminal...");
    for(param in params) {
        info <- paste(param, test$getInfoParam(param), sep = " = ");
        print(info);
    }
    print("");
    print("");

    print("Try to test if quik terminal is connected to quik server...");
    if(test$isConnected() == 1) {
        print("Quik terminal is connected to quik server!")
        print("");
        print("");

        print("Try to get trade date from quik terminal...");
        datetime <- test$getTradeDate();
        if(is.na(datetime) == FALSE) {
            print(datetime$toString());
        }
        print("");
        print("");


        print("Try to get list of securities classes from quik terminal...");
        classes <- test$getClassesList();
        if(is.na(classes) == FALSE) {
            print(classes$toString());
        }
        print("");
        print("");


        print("Try to get info about SPBOPT class from quik terminal...");
        class <- test$getClassInfo("SPBOPT");
        if(is.na(class) == FALSE) {
            print(class$toString());
        }
        print("");
        print("");


        print("Try to get first 10 secs of SPBOPT class from quik terminal...");
        codes <- test$getClassSecurities("SPBOPT", 1, 10);
        if(is.na(codes) == FALSE) {
            print(codes$toString());
        }
        print("");
        print("");


        print("Try to info about security with code RI80000BC8 from quik terminal...");
        secInfo <- test$getSecurityInfo("SPBOPT", "RI80000BC8");
        if(is.na(secInfo) == FALSE) {
            print(secInfo$toString());
        }
        print("");
        print("");


        print("Trying to get info about securities table");
        tableInfo <- test$getNumberOfRows("securities");
        if(is.na(tableInfo) == FALSE) {
            print(tableInfo$toString());
        }
        print("");
        print("");

        print("Trying to get first item of securities table");
        item <- test$getItem("securities", 0);
        if(is.na(item) == FALSE) {
            print(item$toString());
        }
        print("");
        print("");

        print("Trying to get first 10 items of securities table");
        df <- test$getItems("securities", 0, 9);
        if(is.na(df) == FALSE) {
            lapply(as.list(df$getRecords()), function(security) {
                print(security$toString());
            });
        }
        print("");
        print("");


        print("Trying to subcribe for order book data of class:sec: QJSIM;SBER");
        descriptor <- test$subscribeQuotes("QJSIM", "SBER");
        if(is.na(descriptor) == FALSE) {
            if(test$isSubscribedToQuotes(descriptor)) {
                print("Successfully subcribe to order book data!");
                print("Try to obtain order boo snapshot from quik server...");
                # Разобраться почему необходим ожидание
                # Вероятнее всего причина в следующем:
                # Qlua функция Subscribe_Level_II_Quotes возвращает результат быстрее
                # Нежели происходит фактический заказ стакана на сервере квика
                Sys.sleep(1);
                orderBook <- test$getQuoteLevel2("QJSIM", "SBER");
                if(is.na(orderBook) == FALSE) {
                    print(orderBook$toString());
                }
                test$unsubscribeQuotes(descriptor);
            }
        }
        print("");
        print("");


        print("Trying to create datasource for class:sec;interval: QJSIM;SBER;INTERVAL_M1");
        datasource <- test$createDataSource("QJSIM", "SBER", "INTERVAL_M1");
        if(is.na(datasource) == FALSE) {
            print("Successfully create datasource!");
            print("Current count of candles: ")
            print(test$getDatasourceSize(datasource)$toString());
            print("");
            print("");
            print("Try to obtain first 10 candles from quik datasource...");
            candles <- test$getOHLCPrices(datasource, 1, 10);
            if(is.na(candles) == FALSE) {
                lapply(as.list(candles$getRecords()), function(candle) {
                    print(candle$toString());
                });
            }
            test$closeDatasource(datasource);
        }
        print("");
        print("");


        print("Try to send test transaction quik terminal...");
        transaction <- new(J("ru.rusquant.data.quik.Transaction"));
        transaction$setAccount("NL0011100043");
        transaction$setTransId(new(Long, "1500014677"));
        transaction$setAction(J("ru.rusquant.data.quik.types.ActionType")$NEW_ORDER);
        transaction$setClassCode("QJSIM");
        transaction$setSecCode("RTKM");
        transaction$setOperation(J("ru.rusquant.data.quik.types.OperationType")$BUY);
        transaction$setType(J("ru.rusquant.data.quik.types.OrderType")$MARKET);
        transaction$setQuantity(new(Double, "1.0"));
        transaction$setPrice(new(Double, "0.0"));
        transaction$setComment("Test transaction");
        transaction$setMode(J("ru.rusquant.data.quik.types.TransactionMode")$ON_TRANS_REPLAY);
        print(transaction$toString());
        transReplay <- test$sendTransaction(transaction);
        if(is.na(transReplay) == FALSE) {
        #if(FALSE) {
            print("Receive replay transaction")
            print(transReplay$toString());
            print("Try to get order for transaction...");
            Sys.sleep(5); # Need wait some time for order
            orderNum <- transReplay$getOrderNum();
            order <- test$getOrder(orderNum);
            if(is.na(order) == FALSE) {
                print(order$toString());
            }

            print("Try to get trades for order...");
            trades <- test$getTrades(orderNum);
            if(is.na(trades) == FALSE) {
                lapply(as.list(trades$getRecords()), function(trade) {
                    print(trade$toString());
                });
            }
        }
        print("");
        print("");

    } else {
        print("Quik terminal does not connected to quik server!")
    }
    print("")
    print("")

    test$disconnect();
} else {
    print(test$getConnectErrorMessage());
}