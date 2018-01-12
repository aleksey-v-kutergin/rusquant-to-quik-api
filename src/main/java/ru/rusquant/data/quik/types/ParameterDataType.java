package ru.rusquant.data.quik.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum ParameterDataType {
    DOUBLE,
    LONG,
    CHAR,
    ENUM,
    TIME,
    DATE;

    /**
     * Mapping for custom names and enum constants
     **/
    private static Map<String, ParameterDataType> namesMap = new HashMap<>();

    static {
        namesMap.put("1", ParameterDataType.DOUBLE);
        namesMap.put("2", ParameterDataType.LONG);
        namesMap.put("3", ParameterDataType.CHAR);
        namesMap.put("4", ParameterDataType.ENUM);
        namesMap.put("5", ParameterDataType.TIME);
        namesMap.put("6", ParameterDataType.DATE);
    }

    /**
     * To serialize enum constant to custom string
     **/
    @JsonValue
    public String toValue() {
        for (Map.Entry<String, ParameterDataType> entry : namesMap.entrySet()) {
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
    public static ParameterDataType forValue(String value) {
        if (value == null) return null;
        return namesMap.get(value.toUpperCase());
    }
}
