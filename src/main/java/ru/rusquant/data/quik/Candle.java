package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Java implementation for candle.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Candle extends QuikDataObject {
    /**
     * Open price
     **/
    @JsonProperty("open")
    private Double open;

    /**
     * Close price
     **/
    @JsonProperty("close")
    private Double close;

    /**
     * Max price
     **/
    @JsonProperty("high")
    private Double high;

    /**
     * Min price
     **/
    @JsonProperty("low")
    private Double low;

    /**
     * Volume of last trade
     **/
    @JsonProperty("volume")
    private Double volume;

    /**
     * Date and time
     **/
    @JsonProperty("datetime")
    private DateTime dateTime;

    /**
     * Indicator for calculating the indicator in the presence of a candle.
     * The possible values are:
     * "0" - the indicator is not calculated
     * "1" - the indicator is calculated
     **/
    @JsonProperty("doesExist")
    private Integer doesExist;

    public Candle() {

    }

    @Override
    public String toString() {
        return "Candle: {" +
                "open=" + open +
                ", close=" + close +
                ", high=" + high +
                ", low=" + low +
                ", volume=" + volume +
                ", dateTime=" + dateTime +
                ", doesExist=" + doesExist +
                '}';
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getDoesExist() {
        return doesExist;
    }

    public void setDoesExist(Integer doesExist) {
        this.doesExist = doesExist;
    }
}
