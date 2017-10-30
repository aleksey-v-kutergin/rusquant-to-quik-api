package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Java implementation for firm table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class Firm extends QuikDataObject
{

    /** Firm identifier **/
    @JsonProperty("firmid")
    private String firmId;

    /** Firm name**/
    @JsonProperty("firm_name")
    private String firmName;

    /** Status **/
    @JsonProperty("status")
    private Integer status;

    /** Exchange **/
    @JsonProperty("exchange")
    private String exchange;

    public Firm()
    {

    }

    @Override
    public String toString()
    {
        return "Firm: {" +
                "firmId='" + firmId + '\'' +
                ", firmName='" + firmName + '\'' +
                ", status=" + status +
                ", exchange='" + exchange + '\'' +
                '}';
    }

    public String getFirmId()
    {
        return firmId;
    }

    public void setFirmId(String firmId)
    {
        this.firmId = firmId;
    }

    public String getFirmName()
    {
        return firmName;
    }

    public void setFirmName(String firmName)
    {
        this.firmName = firmName;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getExchange()
    {
        return exchange;
    }

    public void setExchange(String exchange)
    {
        this.exchange = exchange;
    }
}
