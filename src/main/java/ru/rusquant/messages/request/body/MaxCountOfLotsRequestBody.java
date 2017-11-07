package ru.rusquant.messages.request.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class MaxCountOfLotsRequestBody extends RequestBody
{
    private String classCode;
    private String securityCode;
    private String clientCode;
    private String account;
    private Double price;

    @JsonProperty("isBuy")
    private Boolean isBuy;

    @JsonProperty("isMarket")
    private Boolean isMarket;

    public MaxCountOfLotsRequestBody()
    {

    }

    public MaxCountOfLotsRequestBody(String classCode, String securityCode, String clientCode, String account, Double price, Boolean isBuy, Boolean isMarket)
    {
        this.classCode = classCode;
        this.securityCode = securityCode;
        this.clientCode = clientCode;
        this.account = account;
        this.price = price;
        this.isBuy = isBuy;
        this.isMarket = isMarket;
    }

    public String getClassCode()
    {
        return classCode;
    }

    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }

    public String getSecurityCode()
    {
        return securityCode;
    }

    public void setSecurityCode(String securityCode)
    {
        this.securityCode = securityCode;
    }

    public String getClientCode()
    {
        return clientCode;
    }

    public void setClientCode(String clientCode)
    {
        this.clientCode = clientCode;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Boolean getBuy()
    {
        return isBuy;
    }

    public void setBuy(Boolean buy)
    {
        isBuy = buy;
    }

    public Boolean getMarket()
    {
        return isMarket;
    }

    public void setMarket(Boolean market)
    {
        isMarket = market;
    }
}
