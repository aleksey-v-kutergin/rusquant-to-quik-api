package ru.rusquant.data.quik.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum TimeScale {
    /**
     * Tick data
     **/
    INTERVAL_TICK,
    /**
     * 1 minute data
     **/
    INTERVAL_M1,
    /**
     * 2 minute data
     **/
    INTERVAL_M2,
    /**
     * 3 minute data
     **/
    INTERVAL_M3,
    /**
     * 4 minute data
     **/
    INTERVAL_M4,
    /**
     * 5 minute data
     **/
    INTERVAL_M5,
    /**
     * 6 minute data
     **/
    INTERVAL_M6,
    /**
     * 10 minute data
     **/
    INTERVAL_M10,
    /**
     * 15 minute data
     **/
    INTERVAL_M15,
    /**
     * 20 minute data
     **/
    INTERVAL_M20,
    /**
     * 30 minute data
     **/
    INTERVAL_M30,
    /**
     * 1 hour data
     **/
    INTERVAL_H1,
    /**
     * 2 hour data
     **/
    INTERVAL_H2,
    /**
     * 4 hour data
     **/
    INTERVAL_H4,
    /**
     * 1 day data
     **/
    INTERVAL_D1,
    /**
     * 1 week data
     **/
    INTERVAL_W1,
    /**
     * 1 month data
     **/
    INTERVAL_MN1;

    private static Map<Integer, TimeScale> namesMap = new HashMap<>();

    static {
        namesMap.put(1, INTERVAL_M1);
        namesMap.put(2, INTERVAL_M2);
        namesMap.put(3, INTERVAL_M3);
        namesMap.put(4, INTERVAL_M4);
        namesMap.put(5, INTERVAL_M5);
        namesMap.put(6, INTERVAL_M6);
        namesMap.put(10, INTERVAL_M10);
        namesMap.put(15, INTERVAL_M15);
        namesMap.put(20, INTERVAL_M20);
        namesMap.put(30, INTERVAL_M30);
        namesMap.put(60, INTERVAL_H1);
        namesMap.put(120, INTERVAL_H2);
        namesMap.put(240, INTERVAL_H4);
        namesMap.put(1440, INTERVAL_D1);
        namesMap.put(10080, INTERVAL_W1);
        namesMap.put(23200, INTERVAL_MN1);
    }

    /**
     * Serialization
     **/
    @JsonValue
    public Integer toValue() {
        for (Map.Entry<Integer, TimeScale> entry : namesMap.entrySet()) {
            if (this == entry.getValue()) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Deserialization
     **/
    @JsonCreator
    public static TimeScale forValue(Integer value) {
        if (value == null) return null;
        return namesMap.get(value);
    }


    public static boolean contains(String timeScaleName) {
        for (TimeScale type : TimeScale.values()) {
            if (type.toString().equalsIgnoreCase(timeScaleName)) {
                return true;
            }
        }
        return false;
    }

    public static String getAvailableTimeScales() {
        String availableTimeScales = "[ ";
        int counter = 1;
        for (TimeScale type : TimeScale.values()) {
            availableTimeScales += "\t\t" + type.toString().toUpperCase();
            if (counter < TimeScale.values().length) {
                availableTimeScales += ", ";
            }
            counter++;
        }
        availableTimeScales += " ]";
        return availableTimeScales;
    }
}
