package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Java implementation for depo_limits table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class DepoLimit extends QuikDataObject
{
    /** Security code **/
    @JsonProperty("sec_code")
    private String securityCode;

    /** Securities account  **/
    @JsonProperty("trdaccid")
    private String tradingAccountId;

    /** Company ID **/
    @JsonProperty("firmid")
    private String firmId;

    /** Client code **/
    @JsonProperty("client_code")
    private String clientCode;

    /** Incoming balance on securities **/
    @JsonProperty("openbal")
    private Double openBalance;

    /** Incoming limit on securities **/
    @JsonProperty("openlimit")
    private Double openLimit;

    /** Current balance on securities **/
    @JsonProperty("currentbal")
    private Double currentBalance;

    /** Current limit on securities **/
    @JsonProperty("currentlimit")
    private Double currentLimit;

    /** Number of lots blocked for sale **/
    @JsonProperty("locked_sell")
    private Long lockedCountOfSellLots;

    /** Number of lots blocked for purchase **/
    @JsonProperty("locked_buy")
    private Long lockedCountOfBuyLots;

    /** The value of securities blocked for purchase **/
    @JsonProperty("locked_buy_value")
    private Double lockedBuyValue;

    /** The value of securities that are blocked for sale **/
    @JsonProperty("locked_sell_value")
    private Double lockedSellValue;

    /** Purchase price **/
    @JsonProperty("awg_position_price")
    private Double awgPositionPrice;

    /**
     * The limit type. The possible values are:
     *   "0" - the usual limits
     *    otherwise - technological limits
     **/
    @JsonProperty("limit_kind")
    private Integer limitKind;

    public DepoLimit()
    {

    }

    @Override
    public String toString()
    {
        return "DepoLimit: {" +
                "securityCode='" + securityCode + '\'' +
                ", tradingAccountId='" + tradingAccountId + '\'' +
                ", firmId='" + firmId + '\'' +
                ", clientCode='" + clientCode + '\'' +
                ", openBalance=" + openBalance +
                ", openLimit=" + openLimit +
                ", currentBalance=" + currentBalance +
                ", currentLimit=" + currentLimit +
                ", lockedCountOfSellLots=" + lockedCountOfSellLots +
                ", lockedCountOfBuyLots=" + lockedCountOfBuyLots +
                ", lockedBuyValue=" + lockedBuyValue +
                ", lockedSellValue=" + lockedSellValue +
                ", awgPositionPrice=" + awgPositionPrice +
                ", limitKind=" + limitKind +
                '}';
    }

    public String getSecurityCode()
    {
        return securityCode;
    }

    public void setSecurityCode(String securityCode)
    {
        this.securityCode = securityCode;
    }

    public String getTradingAccountId()
    {
        return tradingAccountId;
    }

    public void setTradingAccountId(String tradingAccountId)
    {
        this.tradingAccountId = tradingAccountId;
    }

    public String getFirmId()
    {
        return firmId;
    }

    public void setFirmId(String firmId)
    {
        this.firmId = firmId;
    }

    public String getClientCode()
    {
        return clientCode;
    }

    public void setClientCode(String clientCode)
    {
        this.clientCode = clientCode;
    }

    public Double getOpenBalance()
    {
        return openBalance;
    }

    public void setOpenBalance(Double openBalance)
    {
        this.openBalance = openBalance;
    }

    public Double getOpenLimit()
    {
        return openLimit;
    }

    public void setOpenLimit(Double openLimit)
    {
        this.openLimit = openLimit;
    }

    public Double getCurrentBalance()
    {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance)
    {
        this.currentBalance = currentBalance;
    }

    public Double getCurrentLimit()
    {
        return currentLimit;
    }

    public void setCurrentLimit(Double currentLimit)
    {
        this.currentLimit = currentLimit;
    }

    public Long getLockedCountOfSellLots()
    {
        return lockedCountOfSellLots;
    }

    public void setLockedCountOfSellLots(Long lockedCountOfSellLots)
    {
        this.lockedCountOfSellLots = lockedCountOfSellLots;
    }

    public Long getLockedCountOfBuyLots()
    {
        return lockedCountOfBuyLots;
    }

    public void setLockedCountOfBuyLots(Long lockedCountOfBuyLots)
    {
        this.lockedCountOfBuyLots = lockedCountOfBuyLots;
    }

    public Double getLockedBuyValue()
    {
        return lockedBuyValue;
    }

    public void setLockedBuyValue(Double lockedBuyValue)
    {
        this.lockedBuyValue = lockedBuyValue;
    }

    public Double getLockedSellValue()
    {
        return lockedSellValue;
    }

    public void setLockedSellValue(Double lockedSellValue)
    {
        this.lockedSellValue = lockedSellValue;
    }

    public Double getAwgPositionPrice()
    {
        return awgPositionPrice;
    }

    public void setAwgPositionPrice(Double awgPositionPrice)
    {
        this.awgPositionPrice = awgPositionPrice;
    }

    public Integer getLimitKind()
    {
        return limitKind;
    }

    public void setLimitKind(Integer limitKind)
    {
        this.limitKind = limitKind;
    }
}
