package ru.rusquant.data.quik.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 *    Types of tables in Quik terminal.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public enum  QuikTableType
{
    FIRMS,
    CLASSES,
    SECURITIES,
    TRADE_ACCOUNTS,
    CLIENT_CODES,
    ALL_TRADES,
    ACCOUNT_POSITIONS,
    ORDERS,
    FUTURES_CLIENT_HOLDING,
    FUTURES_CLIENT_LIMITS,
    MONEY_LIMITS,
    DEPO_LIMITS,
    TRADES,
    STOP_ORDERS,
    NEG_DEALS,
    NEG_TRADES,
    NEG_DEAL_REPORTS,
    FIRM_HOLDING,
    ACCOUNT_BALANCE,
    CPP_POSITIONS,
    CPP_HOLDINGS;

    private static Map<String, QuikTableType> namesMap = new HashMap<>();

    static
    {
        namesMap.put("firms",                   QuikTableType.FIRMS);
        namesMap.put("classes",                 QuikTableType.CLASSES);
        namesMap.put("securities",              QuikTableType.SECURITIES);
        namesMap.put("trade_accounts",          QuikTableType.TRADE_ACCOUNTS);
        namesMap.put("client_codes",            QuikTableType.CLIENT_CODES);
        namesMap.put("all_trades",              QuikTableType.ALL_TRADES);
        namesMap.put("account_positions",       QuikTableType.ACCOUNT_POSITIONS);
        namesMap.put("orders",                  QuikTableType.ORDERS);
        namesMap.put("futures_client_holding",  QuikTableType.FUTURES_CLIENT_HOLDING);
        namesMap.put("futures_client_limits",   QuikTableType.FUTURES_CLIENT_LIMITS);
        namesMap.put("money_limits",            QuikTableType.MONEY_LIMITS);
        namesMap.put("depo_limits",             QuikTableType.DEPO_LIMITS);
        namesMap.put("trades",                  QuikTableType.TRADES);
        namesMap.put("stop_orders",             QuikTableType.STOP_ORDERS);
        namesMap.put("neg_deals",               QuikTableType.NEG_DEALS);
        namesMap.put("neg_trades",              QuikTableType.NEG_TRADES);
        namesMap.put("neg_deal_reports",        QuikTableType.NEG_DEAL_REPORTS);
        namesMap.put("firm_holding",            QuikTableType.FIRM_HOLDING);
        namesMap.put("account_balance",         QuikTableType.ACCOUNT_BALANCE);
        namesMap.put("ccp_positions",           QuikTableType.CPP_POSITIONS);
        namesMap.put("ccp_holdings",            QuikTableType.CPP_HOLDINGS);
    }


    /** For serialization **/
    @JsonValue
    public String toValue()
    {
        for(Map.Entry<String, QuikTableType> entry : namesMap.entrySet())
        {
            if(this == entry.getValue()) { return entry.getKey(); }
        }
        return null;
    }


    /** For deserialization **/
    @JsonCreator
    public static QuikTableType forValue(String value)
    {
        if(value == null) return  null;
        return namesMap.get(value.toLowerCase());
    }
}
