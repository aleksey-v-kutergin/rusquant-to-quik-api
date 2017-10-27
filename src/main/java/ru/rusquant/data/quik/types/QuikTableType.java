package ru.rusquant.data.quik.types;

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
    ORDERS_TABLE,
    TRADES_TABLE;

    private static Map<String, QuikTableType> namesMap = new HashMap<>();

    static
    {
        namesMap.put("orders", QuikTableType.ORDERS_TABLE);
        namesMap.put("trades", QuikTableType.TRADES_TABLE);
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
    public static QuikTableType forValue(String value)
    {
        if(value == null) return  null;
        return namesMap.get(value.toLowerCase());
    }
}
