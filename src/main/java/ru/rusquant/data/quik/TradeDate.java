package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Java implementation for trade date lua table.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeDate extends QuikDataObject
{
    /** Trade date in DD.MM.YYYY **/
    @JsonProperty("date")
    private String date;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("month")
    private Integer month;

    @JsonProperty("day")
    private Integer day;

    public TradeDate()
    {

    }

    @Override
    public String toString()
    {
        return "TradeDate: {" +
                "date='" + date + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public Integer getMonth()
    {
        return month;
    }

    public void setMonth(Integer month)
    {
        this.month = month;
    }

    public Integer getDay()
    {
        return day;
    }

    public void setDay(Integer day)
    {
        this.day = day;
    }
}
