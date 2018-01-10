package ru.rusquant.messages.request.factory;

import ru.rusquant.data.quik.descriptor.DatasourceDescriptor;
import ru.rusquant.data.quik.descriptor.Descriptor;
import ru.rusquant.data.quik.descriptor.ParameterDescriptor;
import ru.rusquant.data.quik.descriptor.QuotesDescriptor;
import ru.rusquant.data.quik.Transaction;
import ru.rusquant.data.quik.types.*;
import ru.rusquant.messages.request.body.RequestSubject;
import ru.rusquant.messages.request.body.RequestBody;
import ru.rusquant.messages.request.body.quik.*;

import java.util.List;


/**
 * Factory for request's body
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class RequestBodyFactory {

    public RequestBody createRequestBody(RequestSubject subject, List<?> args) {
        switch (subject) {
            case CONNECTION_SATE: {
                return new ConnectionStateRequestBody();
            }
            case ECHO: {
                validateEchoArgs(args);
                String echoMsg = (String) args.get(0);
                return new EchoRequestBody(echoMsg);
            }
            case INFO_PARAMETER: {
                validateInfoParamArgs(args);
                String parameterName = (String) args.get(0);
                return new InfoParameterRequestBody(InfoParamType.valueOf(InfoParamType.class, parameterName));
            }
            case TRANSACTION: {
                validateTransactionArgs(args);
                return new TransactionRequestBody((Transaction) args.get(0));
            }
            case ORDER: {
                validateOrderArgs(args);
                Long orderId = (Long) args.get(0);
                return new OrderRequestBody(orderId);
            }
            case TRADE: {
                validateTradeArgs(args);
                Long tradeId = (Long) args.get(0);
                return new TradesRequestBody(tradeId);
            }
            case TABLE_INFO: {
                validateTableInfoArgs(args);
                String tableName = (String) args.get(0);
                return new QuikTableInfoRequestBody(QuikTableType.forValue(tableName));
            }
            case TABLE_ITEM: {
                validateTableItemArgs(args);
                String tableName = (String) args.get(0);
                Integer index = (Integer) args.get(1);
                return new QuikTableItemRequestBody(QuikTableType.forValue(tableName), index);
            }
            case TABLE_ITEMS: {
                validateTableItemsArgs(args);
                String tableName = (String) args.get(0);
                Integer firstIndex = (Integer) args.get(1);
                Integer lastIndex = (Integer) args.get(2);
                return new QuikTableItemsRequestBody(QuikTableType.forValue(tableName), firstIndex, lastIndex);
            }
            case TRADING_PARAMETER: {
                validateTradingParameterArgs(args);
                String classCode = (String) args.get(0);
                String securityCode = (String) args.get(1);
                ParameterType parameter = ParameterType.valueOf(ParameterType.class, (String) args.get(2));
                String version = (String) args.get(3);
                return new TradingParameterRequestBody(classCode, securityCode, parameter, version);
            }
            case SUBSCRIBE_TRADING_PARAMETER: {
                validateSubscribeParameterArgs(args);
                String classCode = (String) args.get(0);
                String securityCode = (String) args.get(1);
                String parameterName = (String) args.get(2);
                return new SubscribeParameterRequestBody(classCode, securityCode, parameterName);
            }
            case UNSUBSCRIBE_TRADING_PARAMETER: {
                validateParameterDescriptorArg(args);
                return new UnsubscribeParameterRequestBody((ParameterDescriptor) args.get(0));
            }
            case TRADE_DATE: {
                return new TradeDateRequestBody();
            }
            case SECURITY_INFO: {
                validateSecurityInfoArgs(args);
                String classCode = (String) args.get(0);
                String securityCode = (String) args.get(1);
                return new SecurityInfoRequestBody(classCode, securityCode);
            }
            case MAX_LOT_COUNT: {
                validateMaxCountOfLotsArgs(args);
                String classCode = (String) args.get(0);
                String securityCode = (String) args.get(1);
                String clientCode = (String) args.get(2);
                String account = (String) args.get(3);
                Double price = (Double) args.get(4);
                Boolean isBuy = (Boolean) args.get(5);
                Boolean isMarket = (Boolean) args.get(6);
                return new MaxCountOfLotsRequestBody(classCode, securityCode, clientCode, account, price, isBuy, isMarket);
            }
            case CLASS_INFO: {
                validateClassInfoArgs(args);
                String classCode = (String) args.get(0);
                return new SecurityClassInfoRequestBody(classCode);
            }
            case CLASSES_LIST: {
                return new ClassesListRequestBody();
            }
            case CLASS_SECURITIES: {
                validateClassSecuritiesArgs(args);
                String classCode = (String) args.get(0);
                Integer firstIndex = (Integer) args.get(1);
                Integer lastIndex = (Integer) args.get(2);
                return new ClassSecuritiesRequestBody(classCode, firstIndex, lastIndex);
            }
            case SUBSCRIBE_QUOTES: {
                validateQuotesArgs(args);
                String classCode = (String) args.get(0);
                String securityCode = (String) args.get(1);
                return new SubscribeQuotesRequestBody(classCode, securityCode);
            }
            case UNSUBSCRIBE_QUOTES: {
                validateQuotesDescriptor(args);
                return new UnsubscribeQuotesRequestBody((QuotesDescriptor) args.get(0));
            }
            case IS_SUBSCRIBED_QUOTES: {
                validateQuotesDescriptor(args);
                return new IsSubscribeQuotesRequestBody((QuotesDescriptor) args.get(0));
            }
            case QUOTES: {
                validateQuotesArgs(args);
                String classCode = (String) args.get(0);
                String securityCode = (String) args.get(1);
                return new QuotesRequestBody(classCode, securityCode);
            }
            case CREATE_DATASOURCE: {
                validateDatasourceArgs(args);
                String classCode = (String) args.get(0);
                String securityCode = (String) args.get(1);
                TimeScale interval = TimeScale.valueOf(TimeScale.class, (String) args.get(2));
                if (args.size() == 3) {
                    return new CreateDatasourceRequestBody(classCode, securityCode, interval);
                } else if (args.size() == 4) {
                    DSParameterType parameter = DSParameterType.valueOf(DSParameterType.class, (String) args.get(3));
                    return new CreateDatasourceRequestBody(classCode, securityCode, interval, parameter);
                }
            }
            case CLOSE_DATASOURCE: {
                validateDatasourceDescriptorArg(args);
                return new CloseDatasourceRequestBody((DatasourceDescriptor) args.get(0));
            }
            case DATASOURCE_SIZE: {
                validateDatasourceDescriptorArg(args);
                return new DatasourceSizeRequestBody((DatasourceDescriptor) args.get(0));
            }
            case SINGLE_CANDLE: {
                validateSingleCandleArgs(args);
                DatasourceDescriptor descriptor = (DatasourceDescriptor) args.get(0);
                Long candleIndex = (Long) args.get(1);
                return new SingleCandleRequestBody(descriptor, candleIndex);
            }
            case CANDLES_SET: {
                validateCandlesSetArgs(args);
                DatasourceDescriptor descriptor = (DatasourceDescriptor) args.get(0);
                Integer firstIndex = (Integer) args.get(1);
                Integer lastIndex = (Integer) args.get(2);
                return new CandlesSetRequestBody(descriptor, firstIndex, lastIndex);
            }
            case MONEY: {
                validateMoneyArgs(args);
                String clientCode = (String) args.get(0);
                String firmId = (String) args.get(1);
                String tag = (String) args.get(2);
                String currencyCode = (String) args.get(3);
                return new MoneyRequestBody(clientCode, firmId, tag, currencyCode);
            }
            case DEPO: {
                validateDepoArgs(args);
                String clientCode = (String) args.get(0);
                String firmId = (String) args.get(1);
                String securityCode = (String) args.get(2);
                String account = (String) args.get(3);
                return new DepoRequestBody(clientCode, firmId, securityCode, account);
            }
            default: {
                return null;
            }
        }
    }

    /**
     * Determine if the passed args array can be treated as a single argument.
     **/
    private void validateSingleArg(List<?> args) throws IllegalArgumentException {
        if (args == null) {
            throw new IllegalArgumentException("Passed args array is null!");
        }
        if (args.size() != 1) {
            String msg = "Length of the passed args array is different from 1!";
            msg += " ";
            msg += "Target request body may contain only one parameter!";
            throw new IllegalArgumentException(msg);
        }
        if (args.get(0) == null) {
            throw new IllegalArgumentException("Single element of the passed args array in null!");
        }
    }

    private void validateMultipleArgs(List<?> args, int requiredArgsCount) throws IllegalArgumentException {
        if (args == null) {
            throw new IllegalArgumentException("Passed args array is null!");
        }
        if (args.size() != requiredArgsCount) {
            String msg = "Length of the passed args array is different from required args count " + requiredArgsCount + "!";
            throw new IllegalArgumentException(msg);
        }
        for (int i = 0; i < args.size(); i++) {
            if (args.get(i) == null) {
                throw new IllegalArgumentException("Argument with number " + (i + 1) + " of the passed args array in null!");
            }
        }
    }

    private void validateStringArgs(List<?> args, String subject) {
        for (int i = 0; i < args.size(); i++) {
            if (!(args.get(i) instanceof String)) {
                String msg = "Argument with number " + (i + 1) + " of " + subject + " request is not a string!";
                throw new IllegalArgumentException(msg);
            }
        }
    }

    private void validateEchoArgs(List<?> args) {
        validateSingleArg(args);
        validateStringArgs(args, "echo");
    }

    private void validateInfoParamArgs(List<?> args) {
        validateSingleArg(args);
        validateStringArgs(args, "info parameter");
        String parameter = (String) args.get(0);
        if (!InfoParamType.contains(parameter)) {
            String msg = "Passed parameter+: " + parameter;
            msg += " does not belong to set of available info parameters: \n" + InfoParamType.getAvailableParameters();
            throw new IllegalArgumentException(msg);
        }
    }


    private void validateTransactionArgs(List<?> args) {
        validateSingleArg(args);
        if (!(args.get(0) instanceof Transaction)) {
            String msg = "Class of argument with number " + 1 + " of send transaction request is not a Transaction!";
            throw new IllegalArgumentException(msg);
        }
        validateTransaction((Transaction) args.get(0));
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction.getTransId() == null) {
            throw new IllegalArgumentException("Undefined required parameter! Id of the transaction is undefined!");
        }
        if (transaction.getAction() == null) {
            throw new IllegalArgumentException("Undefined required parameter! Action type of the transaction is undefined!");
        }
        if (transaction.getClassCode() == null) {
            throw new IllegalArgumentException("Undefined required parameter! Class code of the transaction is undefined!");
        }
        if (transaction.getSecCode() == null) {
            throw new IllegalArgumentException("Undefined required parameter! Security code of the transaction is undefined!");
        }
        if (transaction.getOperation() == null) {
            throw new IllegalArgumentException("Undefined required parameter! Operation type of the transaction is undefined!");
        }
        if (transaction.getType() == null) {
            throw new IllegalArgumentException("Undefined required parameter! Type of the transaction is undefined!");
        }
        if (transaction.getQuantity() == null) {
            throw new IllegalArgumentException("Undefined required parameter! Quantity of the transaction is undefined!");
        }
        if (transaction.getAccount() == null) {
            throw new IllegalArgumentException("Undefined required parameter! Account of the transaction is undefined!");
        }
        if (transaction.getPrice() == null) {
            throw new IllegalArgumentException("Undefined required parameter! Price of the transaction is undefined!");
        }
        if (transaction.getMode() == null) {
            throw new IllegalArgumentException("Undefined required parameter! Mode of the transaction is undefined!");
        }
    }


    private void validateOrderArgs(List<?> args) {
        validateSingleArg(args);
        if (!(args.get(0) instanceof Long)) {
            String msg = "Passed argument of order request is not a long type!";
            msg += " According to signature of getOrder(Long orderNumber)";
            msg += " argument of order request has to be a long!";
            throw new IllegalArgumentException(msg);
        }
    }


    private void validateTradeArgs(List<?> args) {
        validateSingleArg(args);
        if (!(args.get(0) instanceof Long)) {
            String msg = "Passed argument of trade request is not a long type!";
            msg += " According to signature of getTrades(Long orderNumber)";
            msg += " argument of trade request has to be a long!";
            throw new IllegalArgumentException(msg);
        }
    }

    private void validateQuikTableName(String tableName) {
        if (QuikTableType.forValue(tableName) == null) {
            String supportedTables = "[ ";
            int counter = 1;
            for (QuikTableType type : QuikTableType.values()) {
                supportedTables += "\t\t" + type.toValue();
                if (counter < InfoParamType.values().length) {
                    supportedTables += ", ";
                }
                counter++;
            }
            supportedTables += " ]";
            String msg = "Passed table name: " + tableName;
            msg += " does not belong to set of supported QUIK tables: \n" + supportedTables;
            throw new IllegalArgumentException(msg);
        }
    }

    private void validateTableInfoArgs(List<?> args) {
        validateSingleArg(args);
        validateStringArgs(args, "table info");
        String tableName = (String) args.get(0);
        validateQuikTableName(tableName);
    }


    private void validateTableItemArgs(List<?> args) {
        validateMultipleArgs(args, 2);
        if (!(args.get(0) instanceof String)) {
            String msg = "Argument with number " + 1 + " of " + "table item" + " request is not a string!";
            throw new IllegalArgumentException(msg);
        }
        String tableName = (String) args.get(0);
        validateQuikTableName(tableName);

        if (!(args.get(1) instanceof Integer)) {
            String msg = "Argument with number " + 2 + " of " + "table item" + " request is not an integer!";
            throw new IllegalArgumentException(msg);
        }
    }


    private void validateTableItemsArgs(List<?> args) {
        validateMultipleArgs(args, 3);
        if (!(args.get(0) instanceof String)) {
            String msg = "Argument with number " + 1 + " of " + "table items" + " request is not a string!";
            throw new IllegalArgumentException(msg);
        }
        String tableName = (String) args.get(0);
        validateQuikTableName(tableName);
        for(int i = 1; i < args.size(); i++) {
            if (!(args.get(i) instanceof Integer)) {
                String msg = "Argument with number " + (i + 1) + " of " + "table items" + " request is not an integer!";
                throw new IllegalArgumentException(msg);
            }
        }
    }



    private void validateTradingParameterArgs(List<?> args) {
        validateMultipleArgs(args, 4);
        validateStringArgs(args, "trading parameter");
        String parameter = (String) args.get(2);
        if (!ParameterType.contains(parameter)) {
            String msg = "Passed parameter: " + parameter;
            msg += " does not belong to set of available parameters of current trading table: \n" + ParameterType.getAvailableParameters();
            throw new IllegalArgumentException(msg);
        }
    }

    private void validateSubscribeParameterArgs(List<?> args) {
        validateMultipleArgs(args, 3);
        validateStringArgs(args, "subscribe trading parameter");
        String parameter = (String) args.get(2);
        if (!ParameterType.contains(parameter)) {
            String msg = "Passed parameter: " + parameter;
            msg += " does not belong to set of available parameters of current trading table: \n" + ParameterType.getAvailableParameters();
            throw new IllegalArgumentException(msg);
        }
    }

    private void validateQuotesArgs(List<?> args) {
        validateMultipleArgs(args, 2);
        validateStringArgs(args, "quotes");
    }

    private void validateSecurityInfoArgs(List<?> args) {
        validateMultipleArgs(args, 2);
        validateStringArgs(args, "security info");
    }

    private void validateClassInfoArgs(List<?> args) {
        validateSingleArg(args);
        validateStringArgs(args, "class info");
    }

    private void validateClassSecuritiesArgs(List<?> args) {
        validateMultipleArgs(args, 3);
        if (!(args.get(0) instanceof String)) {
            String msg = "Argument with number " + 1 + " of " + "class securities" + " request is not a string!";
            throw new IllegalArgumentException(msg);
        }
        for(int i = 1; i < args.size(); i++) {
            if (!(args.get(i) instanceof Integer)) {
                String msg = "Argument with number " + (i + 1) + " of " + "class securities" + " request is not an integer!";
                throw new IllegalArgumentException(msg);
            }
        }
    }

    private void validateMaxCountOfLotsArgs(List<?> args) {
        validateMultipleArgs(args, 7);
        for (int i = 0; i < 7; i++) {
            if (i < 4 && !(args.get(i) instanceof String)) {
                String msg = "Argument with number " + (i + 1) + " of max count of lots request is not a string!";
                throw new IllegalArgumentException(msg);
            }
            if (i == 4 && !(args.get(4) instanceof Double)) {
                String msg = "Argument with number " + (i + 1) + " of max count of lots request is not a double!";
                throw new IllegalArgumentException(msg);
            }
            if (i > 4 && !(args.get(i) instanceof Boolean)) {
                String msg = "Argument with number " + (i + 1) + " of max count of lots request is not a boolean!";
                throw new IllegalArgumentException(msg);
            }
        }
    }

    private void validateDatasourceArgs(List<?> args) {
        if (args.size() > 3) {
            validateMultipleArgs(args, 4);
        } else {
            validateMultipleArgs(args, 3);
        }
        validateStringArgs(args, "create datasource");
        String intervalName = (String) args.get(2);
        if (!TimeScale.contains(intervalName)) {
            String msg = "Passed time scale: " + intervalName;
            msg += " does not belong to set of available trading time scales: \n" + TimeScale.getAvailableTimeScales();
            throw new IllegalArgumentException(msg);
        }
        if (args.size() > 3) {
            String dsParameter = (String) args.get(3);
            if (!DSParameterType.contains(dsParameter)) {
                String msg = "Passed datasource parameter: " + dsParameter;
                msg += " does not belong to set of available parameters of datasource: \n" + DSParameterType.getAvailableDSParameters();
                throw new IllegalArgumentException(msg);
            }
        }
    }

    private void validateDescriptorArg(List<?> args) {
        Long id = ((Descriptor) args.get(0)).getId();
        if (id == null) {
            String msg = "Undefined required parameter! Id of the descriptor is undefined!";
            throw new IllegalArgumentException(msg);
        }
    }

    private void validateParameterDescriptorArg(List<?> args) {
        validateSingleArg(args);
        validateDescriptorArg(args);
        if (!(args.get(0) instanceof ParameterDescriptor)) {
            String msg = "Class of passed argument is not a ParameterDescriptor!";
            throw new IllegalArgumentException(msg);
        }
    }

    private void validateQuotesDescriptor(List<?> args) {
        validateSingleArg(args);
        validateDescriptorArg(args);
        if (!(args.get(0) instanceof QuotesDescriptor)) {
            String msg = "Class of passed argument is not a QuotesDescriptor!";
            throw new IllegalArgumentException(msg);
        }
    }

    private void validateDatasourceDescriptorArg(List<?> args) {
        validateSingleArg(args);
        validateDescriptorArg(args);
        if (!(args.get(0) instanceof DatasourceDescriptor)) {
            String msg = "Class of passed argument is not a DatasourceDescriptor!";
            throw new IllegalArgumentException(msg);
        }
    }

    private void validateSingleCandleArgs(List<?> args) {
        validateMultipleArgs(args, 2);
        validateDescriptorArg(args.subList(0, 1));
        if (!(args.get(0) instanceof DatasourceDescriptor)) {
            String msg = "Class of passed argument is not a DatasourceDescriptor!";
            throw new IllegalArgumentException(msg);
        }
        if (!(args.get(1) instanceof Long)) {
            String msg = "Argument with number " + 2 + " of " + "single candle" + " request is not an long!";
            throw new IllegalArgumentException(msg);
        }
    }

    private void validateCandlesSetArgs(List<?> args) {
        validateMultipleArgs(args, 3);
        validateDescriptorArg(args.subList(0, 1));
        if (!(args.get(0) instanceof DatasourceDescriptor)) {
            String msg = "Class of passed argument is not a DatasourceDescriptor!";
            throw new IllegalArgumentException(msg);
        }
        for (int i = 1; i < args.size(); i++) {
            if (!(args.get(i) instanceof Integer)) {
                String msg = "Argument with number " + (i + 1) + " of " + "candles set" + " request is not an integer!";
                throw new IllegalArgumentException(msg);
            }
        }
    }

    private void validateMoneyArgs(List<?> args) {
        validateMultipleArgs(args, 4);
        validateStringArgs(args, "money");
    }

    private void validateDepoArgs(List<?> args) {
        validateMultipleArgs(args, 4);
        validateStringArgs(args, "money");
    }

}
