package ru.rusquant.data.quik.types;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Types of operations, which can be performed by transactions in Quik terminal.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public enum OperationType {
    /**
     * For QUIK has to be serialize in B
     **/
    BUY,

    /**
     * For QUIK has to be serialize in S
     **/
    SELL;

    private static Map<String, OperationType> namesMap = new HashMap<>();

    static {
        namesMap.put("B", OperationType.BUY);
        namesMap.put("S", OperationType.SELL);
    }


    /**
     * To serialize enum constant to custom string
     **/
    @JsonValue
    public String toValue() {
        for (Map.Entry<String, OperationType> entry : namesMap.entrySet()) {
            if (this == entry.getValue()) {
                return entry.getKey();
            }
        }
        return null; // Or fail
    }

    /**
     * To deserialize enum from custom string
     **/
    public static OperationType forValue(String value) {
        if (value == null) return null;
        return namesMap.get(value.toUpperCase());
    }
}
