package ru.rusquant.data.quik.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Types of order.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public enum OrderType {
    /**
     * For QUIK has to be serialize in M
     **/
    MARKET,

    /**
     * For QUIK has to be serialize in L
     **/
    LIMIT;

    /**
     * Mapping for custom names and enum constants
     **/
    private static Map<String, OrderType> namesMap = new HashMap<>();

    static {
        namesMap.put("M", OrderType.MARKET);
        namesMap.put("L", OrderType.LIMIT);
    }

    /**
     * To serialize enum constant to custom string
     **/
    @JsonValue
    public String toValue() {
        for (Map.Entry<String, OrderType> entry : namesMap.entrySet()) {
            if (this == entry.getValue()) {
                return entry.getKey();
            }
        }
        return null; // Or fail
    }

    /**
     * To deserialize enum from custom string
     **/
    @JsonCreator
    public static OrderType forValue(String value) {
        if (value == null) return null;
        return namesMap.get(value.toUpperCase());
    }
}
