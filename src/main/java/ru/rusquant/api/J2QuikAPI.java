package ru.rusquant.api;

import ru.rusquant.data.quik.*;
import ru.rusquant.data.quik.descriptor.DatasourceDescriptor;
import ru.rusquant.data.quik.descriptor.ParameterDescriptor;
import ru.rusquant.data.quik.descriptor.QuotesDescriptor;


/**
 * Interface contains prototypes for all methods to work with QUIK terminal.
 * Each class, willing to act as api to terminal has to implement this interface.
 * Each method of api returns corresponding subtype of base quik data object or error data object if error occurs during operation.
 * Such hierarchy of data object allows to provide user with valid result or report about error.
 * All users of the API are free to decide how to map api data objects for their purposes.
 * <p>
 * Example:
 * <p>
 * String someUserMethod() {
 *    QuikDataObject result api.getEcho("hello world!")
 *    if(result instanceof ErrorObject) {
 *      // report about error
 *    } else {
 *      // Do something useful :)
 *    }
 * }
 * <p>
 * This all is because i wanna handle all exceptions from client at api level.
 * User of the api gets a valid result or error. Nothing more, nothing less.
 * <p>
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public interface J2QuikAPI {

    /**====================== Service function  ======================**/

    /**
     * Just say hello world!:)
     **/
    QuikDataObject getEcho(String message);

    /**
     * Returns the state of channel between QUIK terminal and QUIK (Broker) server.
     *
     * @return: 1 - if terminal is connected
     * 0 - otherwise
     **/
    QuikDataObject isConnected();

    /**
     * Returns values of the parameters listed in QUIK-terminal info window (System/About/Information window)
     * Available parameters can be found in QLUA specification and in InfoParamType class.
     * If value of some parameter is not a available in terminal (for instance terminal is not connected),
     * function set value of such parameter to empty string.
     **/
    QuikDataObject getInfoParam(String paramName);


    /**====================== Functions for accessing the rows of arbitrary QUIK tables  ======================**/

    /**
     * Implementation of getNumberOf(STRING TableName) function.
     * Returns the number of rows for table <tableName>
     **/
    QuikDataObject getNumberOfRows(String tableName);

    /**
     * Implementation of TABLE getItem (STRING TableName, DOUBLE Index) function.
     * Returns a row with rowNumber = index and name = tableName.
     * index belongs to range [0, rowsCount], where rowsCount - result of getNumberOfRows(tableName).
     **/
    QuikDataObject getItem(String tableName, Integer index);

    /**
     * QLua specification does not contains such function, but it is convenient
     * to have an ability to get all items of table in a single request
     **/
    QuikDataObject getItems(String tableName, Integer firstIndex, Integer lastIndex);

    /**
     * The function allows quick selection of items from the terminal storage
     * and returns a table with indexes of elements that satisfy the search condition.
     **/
    QuikDataObject searchItems(String tableName, Long startIndex, Long endIndex, String params);


    /**====================== Functions for accessing lists of available parameters  ======================**/

    /**
     * The function is designed to obtain a list of class codes sent from the server during the communication session.
     * The class codes in the list are separated by a comma ",". At the end of the received line, the symbol "," is always appended.
     **/
    QuikDataObject getClassesList();

    /**
     * The function is designed to obtain information about the class.
     **/
    QuikDataObject getClassInfo(String classCode);

    /**
     * The function returns a list of security codes within class.
     * The paper codes in the list are separated by a comma ",". At the end of the received line, the symbol "," is always appended.
     * Range limitation [firstIndex, lastIndex] is due to buffer size limitation
     **/
    QuikDataObject getClassSecurities(String classCode, Integer firstIndex, Integer lastIndex);


    /**====================== Lua script interaction functions and QUIK Workstation  ======================**/

    /**
     * The function is designed to obtain information on monetary limits.
     **/
    QuikDataObject getMoney(String clientCode, String firmId, String tag, String currencyCode);

    /**
     * The function is designed to obtain information on monetary limits with particular type.
     **/
    QuikDataObject getMoneyEx(String clientCode, String firmId, String tag, String currencyCode, Integer limitKind);

    /**
     * The function is designed to obtain information on paper limits.
     **/
    QuikDataObject getDepo(String clientCode, String firmId, String tag, String account);

    /**
     * The function is designed to obtain information on paper limits with particular type.
     **/
    QuikDataObject getDepoEx(String clientCode, String firmId, String tag, String account, Integer limitKind);

    /**
     * The function is intended for obtaining information on futures limits.
     **/
    QuikDataObject getFuturesLimit(String firmId, String accountId, Integer limitType, String currencyCode);

    /**
     * The function is intended for obtaining information on futures limits.
     **/
    QuikDataObject getFuturesHolding(String firmId, String accountId, String securityCode, Integer posType);

    /**
     * The function is intended for obtaining information on paper.
     **/
    QuikDataObject getSecurityInfo(String classCode, String securityCode);

    /**
     * The function returns the date of the current trading session.
     **/
    QuikDataObject getTradeDate();

    /**
     * The function is designed to get a glass of the specified class and paper.
     **/
    QuikDataObject getQuoteLevel2(String classCode, String securityCode);

    /**
     * The function to subscribe the quotes (order book) data at quik server.
     **/
    QuikDataObject subscribeQuotes(String classCode, String securityCode);

    /**
     * The function to unsubscribe the quotes (order book) data at quik server.
     **/
    QuikDataObject unsubscribeQuotes(QuotesDescriptor descriptor);

    /**
     * The function to check status of the subscription to quotes (order book) data at quik server.
     **/
    QuikDataObject isSubscribedToQuotes(QuotesDescriptor descriptor);

    /**
     * The function is designed to obtain the values of all the parameters of the exchange information from the Current Trading Table.
     * With this function, you can get any of the values of the Current Trading Table for the given class and paper codes.
     **/
    QuikDataObject getParamEx(String classCode, String securityCode, String paramName);

    /**
     * The function to subscribe the parameters of the Current Trading Table.
     **/
    QuikDataObject subscribeParameter(String classCode, String securityCode, String paramName);

    /**
     * The function to unsubscribe the parameters of the Current Trading Table.
     **/
    QuikDataObject unsubscribeParameter(ParameterDescriptor descriptor);

    /**
     * The function is designed to obtain the values of all the parameters of the exchange information from the Current Trading Table
     * with the possibility to refuse further obtaining certain parameters ordered using the ParamRequest function.
     * To refuse to receive a parameter, use the CancelParamRequest function.
     **/
    QuikDataObject getParamEx2(String classCode, String securityCode, String paramName);

    /**
     * Java-implementation of sendTransaction() function, specified in QLua docs
     **/
    QuikDataObject sendTransaction(Transaction transaction);

    /**
     * Returns object, received in OnOrder(order) callback
     **/
    QuikDataObject getOrder(Long orderNumber);

    /**
     * Return trades for order
     **/
    QuikDataObject getTrades(Long orderNumber);

    /**
     * Implementation of CalcBuySell function.
     * The function is designed to calculate the maximum possible number of lots in the application.
     **/
    QuikDataObject getMaxCountOfLotsInOrder(String classCode, String securityCode, String clientCode, String account, Double price, Boolean isBuy, Boolean isMarket);

    /**
     * The function is designed to retrieve the values of the parameters of the "Customer Portfolio" table,
     * corresponding to the identifier of the trade participant "firmid" and the client code "client_code".
     **/
    QuikDataObject getPortfolioInfo(String firmId, String clientCode);

    /**
     * The function is designed to retrieve the values of the parameters of the "Customer Portfolio" table,
     * corresponding to the identifier of the trade participant "firmid" and the client code "client_code"
     * and kind of limit "limit_kind".
     **/
    QuikDataObject getPortfolioInfoEx(String firmId, String clientCode, Integer limitKind);

    /**
     * The function is intended for obtaining the parameters of the table "Buy / Sell".
     **/
    QuikDataObject getBuySellInfo(String firmId, String clientCode, String classCode, String securityCode, Double price);

    /**
     * The function is intended for obtaining the parameters of the table "Buy / Sell".
     **/
    QuikDataObject getBuySellInfoEx(String firmId, String clientCode, String classCode, String securityCode, Double price);


    /**====================== Access to OHLC prices  ======================**/

    /**
     * The function is designed to create a table Lua and allows you to work with candles received from the QUIK server,
     * and also to react to their modification.
     **/
    QuikDataObject createDataSource(String classCode, String securityCode, String interval, String parameter);

    QuikDataObject createDataSource(String classCode, String securityCode, String interval);

    /**
     * Functions as a parameter take the candlestick index and return the corresponding value.
     * Candlestick time returns to within milliseconds
     **/
    QuikDataObject getOHLCPrice(DatasourceDescriptor datasource, Long index);

    /**
     * To get all currently available OHLC prices in range.
     **/
    QuikDataObject getOHLCPrices(DatasourceDescriptor datasource, Integer firstIndex, Integer lastIndex);

    /**
     * For getting current count of data in datasource;
     **/
    QuikDataObject getDatasourceSize(DatasourceDescriptor datasource);

    /**
     * For closing data source at server;
     **/
    QuikDataObject closeDatasource(DatasourceDescriptor datasource);
}
