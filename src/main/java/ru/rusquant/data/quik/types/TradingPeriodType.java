package ru.rusquant.data.quik.types;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Types of trading session
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public enum TradingPeriodType {
    OPEN,
    MAIN,
    CLOSE;


    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
