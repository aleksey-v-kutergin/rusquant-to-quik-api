package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Java implementation for account_positions table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountPosition extends QuikDataObject
{
    /** Company ID **/
    @JsonProperty("firmid")
    private String firmId;

    /** Currency code **/
    @JsonProperty("currcode")
    private String currencyCode;

    /** Payment tag **/
    @JsonProperty("tag")
    private String tag;

    /** Description **/
    @JsonProperty("description")
    private String description;

    /** Incoming balance **/
    @JsonProperty("openbal")
    private Double openBalance;

    /** Current balance **/
    @JsonProperty("currentpos")
    private Double currentPosition;

    /** Planned balance **/
    @JsonProperty("plannedpos")
    private Double plannedPosition;

    /** External limit on money **/
    @JsonProperty("limit1")
    private Double limit1;

    /** Internal (personal) money limit **/
    @JsonProperty("limit2")
    private Double limit2;

    /** Position in buy orders **/
    @JsonProperty("orderbuy")
    private Long positionInBuyOrders;

    /** Position in sell orders **/
    @JsonProperty("ordersell")
    private Long positionInSellOrders;

    /** Net position **/
    @JsonProperty("netto")
    private Double netPosition;

    /** Planned position **/
    @JsonProperty("plannedbal")
    private Double plannedBalance;

    /** Debit **/
    @JsonProperty("debit")
    private Double debit;

    /** Credit **/
    @JsonProperty("credit")
    private Double credit;

    /** Account ID **/
    @JsonProperty("bank_acc_id")
    private String bankAccountId;

    /** Margin requirement for the start of trades **/
    @JsonProperty("margincall")
    private Double margincall;

    /** Planned position after settlement **/
    @JsonProperty("settlebal")
    private Double settleBalance;

    public AccountPosition()
    {

    }

    @Override
    public String toString()
    {
        return "AccountPosition: {" +
                "firmId='" + firmId + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                ", openBalance=" + openBalance +
                ", currentPosition=" + currentPosition +
                ", plannedPosition=" + plannedPosition +
                ", limit1=" + limit1 +
                ", limit2=" + limit2 +
                ", positionInBuyOrders=" + positionInBuyOrders +
                ", positionInSellOrders=" + positionInSellOrders +
                ", netPosition=" + netPosition +
                ", plannedBalance=" + plannedBalance +
                ", debit=" + debit +
                ", credit=" + credit +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", margincall=" + margincall +
                ", settleBalance=" + settleBalance +
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

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Double getOpenBalance()
    {
        return openBalance;
    }

    public void setOpenBalance(Double openBalance)
    {
        this.openBalance = openBalance;
    }

    public Double getCurrentPosition()
    {
        return currentPosition;
    }

    public void setCurrentPosition(Double currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    public Double getPlannedPosition()
    {
        return plannedPosition;
    }

    public void setPlannedPosition(Double plannedPosition)
    {
        this.plannedPosition = plannedPosition;
    }

    public Double getLimit1()
    {
        return limit1;
    }

    public void setLimit1(Double limit1)
    {
        this.limit1 = limit1;
    }

    public Double getLimit2()
    {
        return limit2;
    }

    public void setLimit2(Double limit2)
    {
        this.limit2 = limit2;
    }

    public Long getPositionInBuyOrders()
    {
        return positionInBuyOrders;
    }

    public void setPositionInBuyOrders(Long positionInBuyOrders)
    {
        this.positionInBuyOrders = positionInBuyOrders;
    }

    public Long getPositionInSellOrders()
    {
        return positionInSellOrders;
    }

    public void setPositionInSellOrders(Long positionInSellOrders)
    {
        this.positionInSellOrders = positionInSellOrders;
    }

    public Double getNetPosition()
    {
        return netPosition;
    }

    public void setNetPosition(Double netPosition)
    {
        this.netPosition = netPosition;
    }

    public Double getPlannedBalance()
    {
        return plannedBalance;
    }

    public void setPlannedBalance(Double plannedBalance)
    {
        this.plannedBalance = plannedBalance;
    }

    public Double getDebit()
    {
        return debit;
    }

    public void setDebit(Double debit)
    {
        this.debit = debit;
    }

    public Double getCredit()
    {
        return credit;
    }

    public void setCredit(Double credit)
    {
        this.credit = credit;
    }

    public String getBankAccountId()
    {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId)
    {
        this.bankAccountId = bankAccountId;
    }

    public Double getMargincall()
    {
        return margincall;
    }

    public void setMargincall(Double margincall)
    {
        this.margincall = margincall;
    }

    public Double getSettleBalance()
    {
        return settleBalance;
    }

    public void setSettleBalance(Double settleBalance)
    {
        this.settleBalance = settleBalance;
    }
}
