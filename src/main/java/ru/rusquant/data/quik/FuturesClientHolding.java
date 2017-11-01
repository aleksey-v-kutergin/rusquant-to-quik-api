package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Java implementation for futures_client_holding table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class FuturesClientHolding extends QuikDataObject
{
    /** Firm identifier **/
    @JsonProperty("firmid")
    private String firmId;

    /** Trading account id **/
    @JsonProperty("trdaccid")
    private String tradingAccountId;

    /** Futures Contract Code **/
    @JsonProperty("sec_code")
    private String securityCode;

    /** Type of limit **/
    @JsonProperty("type")
    private String type;

    /** Incoming long positions **/
    @JsonProperty("startbuy")
    private Double startBuy;

    /** Incoming short positions **/
    @JsonProperty("startsell")
    private Double startSell;

    /** Incoming net positions **/
    @JsonProperty("startnet")
    private Double startNet;

    /** Current long positions **/
    @JsonProperty("todaybuy")
    private Double todayBuy;

    /** Current short positions **/
    @JsonProperty("todaysell")
    private Double todaySell;

    /** Current net positions **/
    @JsonProperty("totalnet")
    private Double totalNet;

    /** Active for purchase **/
    @JsonProperty("openbuys")
    private Double openBuys;

    /** Active for sale **/
    @JsonProperty("opensells")
    private Double openSells;

    /** Estimation of current net positions **/
    @JsonProperty("cbplused")
    private Double cbplUsed;

    /** Planned net positions **/
    @JsonProperty("cbplplanned")
    private Double cbplUsedPlanned;

    /** Variation Margin **/
    @JsonProperty("varmargin")
    private Double varMargin;

    /** Effective line item price **/
    @JsonProperty("avrposnprice")
    private Double avrPosnPrice;

    /** Line item cost **/
    @JsonProperty("positionvalue")
    private Double positionValue;

    /**
     * The variation margin that was actually calculated during the course of clearing.
     * It is displayed with an accuracy of 2 characters. In this case, in the field "varmargin" a variation margin is translated,
     * calculated taking into account the established boundaries of the price change
     **/
    @JsonProperty("real_varmargin")
    private Double realVarMargin;

    /** The total variation margin on the basis of the main clearing accrued for all positions. Displayed to within 2 characters **/
    @JsonProperty("total_varmargin")
    private Double totalVarMargin;

    /**
     * The current status of the trading session. The possible values are:
     *     "0" is not defined;
     *     "1" - the main session;
     *     "2" - the beginning of the promulking;
     *     "3" - the promulking was completed;
     *     "4" - the main clearing began;
     *     "5" - the main clearing: a new session is appointed;
     *     "6" - the main clearing was completed;
     *     "7" - the evening session was over
     **/
    @JsonProperty("session_status")
    private Integer sessionStatus;

    public FuturesClientHolding()
    {

    }

    @Override
    public String toString()
    {
        return "FuturesClientHolding: {" +
                "firmId='" + firmId + '\'' +
                ", tradingAccountId='" + tradingAccountId + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", type='" + type + '\'' +
                ", startBuy=" + startBuy +
                ", startSell=" + startSell +
                ", startNet=" + startNet +
                ", todayBuy=" + todayBuy +
                ", todaySell=" + todaySell +
                ", totalNet=" + totalNet +
                ", openBuys=" + openBuys +
                ", openSells=" + openSells +
                ", cbplUsed=" + cbplUsed +
                ", cbplUsedPlanned=" + cbplUsedPlanned +
                ", varMargin=" + varMargin +
                ", avrPosnPrice=" + avrPosnPrice +
                ", positionValue=" + positionValue +
                ", realVarMargin=" + realVarMargin +
                ", totalVarMargin=" + totalVarMargin +
                ", sessionStatus=" + sessionStatus +
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

    public String getTradingAccountId()
    {
        return tradingAccountId;
    }

    public void setTradingAccountId(String tradingAccountId)
    {
        this.tradingAccountId = tradingAccountId;
    }

    public String getSecurityCode()
    {
        return securityCode;
    }

    public void setSecurityCode(String securityCode)
    {
        this.securityCode = securityCode;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Double getStartBuy()
    {
        return startBuy;
    }

    public void setStartBuy(Double startBuy)
    {
        this.startBuy = startBuy;
    }

    public Double getStartSell()
    {
        return startSell;
    }

    public void setStartSell(Double startSell)
    {
        this.startSell = startSell;
    }

    public Double getStartNet()
    {
        return startNet;
    }

    public void setStartNet(Double startNet)
    {
        this.startNet = startNet;
    }

    public Double getTodayBuy()
    {
        return todayBuy;
    }

    public void setTodayBuy(Double todayBuy)
    {
        this.todayBuy = todayBuy;
    }

    public Double getTodaySell()
    {
        return todaySell;
    }

    public void setTodaySell(Double todaySell)
    {
        this.todaySell = todaySell;
    }

    public Double getTotalNet()
    {
        return totalNet;
    }

    public void setTotalNet(Double totalNet)
    {
        this.totalNet = totalNet;
    }

    public Double getOpenBuys()
    {
        return openBuys;
    }

    public void setOpenBuys(Double openBuys)
    {
        this.openBuys = openBuys;
    }

    public Double getOpenSells()
    {
        return openSells;
    }

    public void setOpenSells(Double openSells)
    {
        this.openSells = openSells;
    }

    public Double getCbplUsed()
    {
        return cbplUsed;
    }

    public void setCbplUsed(Double cbplUsed)
    {
        this.cbplUsed = cbplUsed;
    }

    public Double getCbplUsedPlanned()
    {
        return cbplUsedPlanned;
    }

    public void setCbplUsedPlanned(Double cbplUsedPlanned)
    {
        this.cbplUsedPlanned = cbplUsedPlanned;
    }

    public Double getVarMargin()
    {
        return varMargin;
    }

    public void setVarMargin(Double varMargin)
    {
        this.varMargin = varMargin;
    }

    public Double getAvrPosnPrice()
    {
        return avrPosnPrice;
    }

    public void setAvrPosnPrice(Double avrPosnPrice)
    {
        this.avrPosnPrice = avrPosnPrice;
    }

    public Double getPositionValue()
    {
        return positionValue;
    }

    public void setPositionValue(Double positionValue)
    {
        this.positionValue = positionValue;
    }

    public Double getRealVarMargin()
    {
        return realVarMargin;
    }

    public void setRealVarMargin(Double realVarMargin)
    {
        this.realVarMargin = realVarMargin;
    }

    public Double getTotalVarMargin()
    {
        return totalVarMargin;
    }

    public void setTotalVarMargin(Double totalVarMargin)
    {
        this.totalVarMargin = totalVarMargin;
    }

    public Integer getSessionStatus()
    {
        return sessionStatus;
    }

    public void setSessionStatus(Integer sessionStatus)
    {
        this.sessionStatus = sessionStatus;
    }
}
