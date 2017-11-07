package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *    Data object for result of CalcBySell function.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class CountOfLots extends QuikDataObject
{
    @JsonProperty("qty")
    private Long qty;

    @JsonProperty("comission")
    private Double comission;

    public CountOfLots()
    {

    }

    @Override
    public String toString()
    {
        return "CountOfLots: {" +
                "qty=" + qty +
                ", comission=" + comission +
                '}';
    }

    public Long getQty()
    {
        return qty;
    }

    public void setQty(Long qty)
    {
        this.qty = qty;
    }

    public Double getComission()
    {
        return comission;
    }

    public void setComission(Double comission)
    {
        this.comission = comission;
    }
}
