package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *    Class represents Quick's datetime table.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class DateTime extends QuikDataObject
{
    private Integer day;
    private Integer hour;
    private Integer mcs;
    private Integer min;
    private Integer month;
    private Integer ms;
    private Integer sec;
    private Integer year;

    @JsonProperty("week_day")
    private Integer weekDay;

    public DateTime()
    {

    }

    public Integer getDay()
    {
        return day;
    }

    public void setDay(Integer day)
    {
        this.day = day;
    }

    public Integer getHour()
    {
        return hour;
    }

    public void setHour(Integer hour)
    {
        this.hour = hour;
    }

    public Integer getMcs()
    {
        return mcs;
    }

    public void setMcs(Integer mcs)
    {
        this.mcs = mcs;
    }

    public Integer getMin()
    {
        return min;
    }

    public void setMin(Integer min)
    {
        this.min = min;
    }

    public Integer getMonth()
    {
        return month;
    }

    public void setMonth(Integer month)
    {
        this.month = month;
    }

    public Integer getMs()
    {
        return ms;
    }

    public void setMs(Integer ms)
    {
        this.ms = ms;
    }

    public Integer getSec()
    {
        return sec;
    }

    public void setSec(Integer sec)
    {
        this.sec = sec;
    }

    public Integer getWeekDay()
    {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay)
    {
        this.weekDay = weekDay;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }
}
