package ru.rusquant.messages.request.body;

/**
 *   Subject of the request.
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public enum RequestSubject
{
    /** Quik request subjects **/
	ECHO,
	ORDER,
	CONNECTION_SATE,
	INFO_PARAMETER,
	TRANSACTION,
	TRADE,
	TABLE_INFO,
    TABLE_ITEM,
    TABLE_ITEMS,
    TRADING_PARAMETER,
    TRADE_DATE,
    SECURITY_INFO,
    MAX_LOT_COUNT,
    CLASS_INFO,
    CLASSES_LIST,
    CLASS_SECURITIES,
    SUBSCRIBE_TRADING_PARAMETER,
    UNSUBSCRIBE_TRADING_PARAMETER,
    SUBSCRIBE_QUOTES,
    UNSUBSCRIBE_QUOTES,
    IS_SUBSCRIBED_QUOTES,
    QUOTES,
    CREATE_DATASOURCE,
    CLOSE_DATASOURCE,
    DATASOURCE_SIZE,
    ALL_CANDLES,
    SINGLE_CANDLE,
    DEPO,
    MONEY


    /** MQL5 request subjects **/
}
