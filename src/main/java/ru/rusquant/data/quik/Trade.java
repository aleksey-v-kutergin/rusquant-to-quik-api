package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.types.TradingPeriodType;

/**
 * Java implementation for orders table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade extends QuikDataObject
{
    /** Id of the transaction **/
    @JsonProperty("trans_id")
    private Long transId;

    /** User identifier **/
    @JsonProperty("uid")
    private Long uid;

    /** Number of trade **/
    @JsonProperty("trade_num")
    private Long tradeNumber;

    /** Number of order, which is one side of this trade **/
    @JsonProperty("order_num")
    private Long orderNumber;

    /** Comment from broker. Usually: <client_code>/<trade_number> **/
    @JsonProperty("brokerref")
    private String brokerref;

    /** Identifier of trader **/
    @JsonProperty("userid")
    private String userId;

    /** Identifier for firm **/
    @JsonProperty("firmid")
    private String firmId;

    /** Trading account **/
    @JsonProperty("account")
    private String account;

    /** Price **/
    @JsonProperty("price")
    private Double price;

    /** Count in lots **/
    @JsonProperty("qty")
    private Long qty;

    /** Volume in cash **/
    @JsonProperty("value")
    private Double value;

    /** Accrued coupon yield **/
    @JsonProperty("accruedint")
    private Double accruedCouponYield;

    /** Accrued coupon yield **/
    @JsonProperty("yield")
    private Double yield;

    /** Payment code **/
    @JsonProperty("settlecode")
    private String paymentCode;

    /** Bit-mask **/
    @JsonProperty("cpfirmid")
    private String cpFirmId;

    /** Code of the partner **/
    @JsonProperty("flags")
    private Integer flags;

    /** Redemption price **/
    @JsonProperty("price2")
    private Double redemptionPrice;

    /** Redemption price **/
    @JsonProperty("reporate")
    private Double repoRate;

    /** Code of the client **/
    @JsonProperty("client_code")
    private Long clientCode;

    /** Income (%) at the date of redemption **/
    @JsonProperty("accrued2")
    private Double redemptionIncome;

    /** Term of repurchase, in calendar days **/
    @JsonProperty("repoterm")
    private Long repoTerm;

    /** The amount of REPO at the current date **/
    @JsonProperty("repovalue")
    private Double repoValue;

    /** Volume of REPO repurchase agreement **/
    @JsonProperty("repo2value")
    private Double repoVolume;

    /** Initial discount (%) **/
    @JsonProperty("start_discount")
    private Double startDiscount;

    /** lower discount (%) **/
    @JsonProperty("lower_discount")
    private Double lowerDiscount;

    /** lower discount (%) **/
    @JsonProperty("upper_discount")
    private Double upperDiscount;

    /** Collateral lock (Yes / No) **/
    @JsonProperty("block_securities")
    private Integer collateralLock;

    /** The Clearing Commission **/
    @JsonProperty("clearing_comission")
    private Double clearingComission;

    /** Commission of the Stock Exchange **/
    @JsonProperty("exchange_comission")
    private Double exchangeComission;

    /** Commission of the Technical Center **/
    @JsonProperty("tech_center_comission")
    private Double techCenterComission;

    /** Broker commission **/
    @JsonProperty("broker_comission")
    private Double brokerComission;

    /** Settlement date **/
    @JsonProperty("settle_date")
    private Long settlementDate;

    /** Settlement currency **/
    @JsonProperty("settle_currency")
    private String settlementCurrency;

    /** Trade currency **/
    @JsonProperty("trade_currency")
    private String tradeCurrency;

    /** Exchange code in the trading system **/
    @JsonProperty("exchange_code")
    private String exchangeCode;

    /** Security code **/
    @JsonProperty("sec_code")
    private String securityCode;

    /** Class code **/
    @JsonProperty("class_code")
    private String classCode;

    /** Date time of trade **/
    @JsonProperty("datetime")
    private DateTime dateTime;

    /** Date time of trade cancelation **/
    @JsonProperty("canceled_datetime")
    private DateTime cancelationDateTime;

    /** Identifier of the settlement account / code in the clearing organization **/
    @JsonProperty("bank_acc_id")
    private String bankAccId;

    /** The number of the trade show in the Trading System for REPO transactions with the Central Bank and SWAP **/
    @JsonProperty("linked_trade")
    private Long linkedTrade;

    /** Account ID in NCC (account code) **/
    @JsonProperty("clearing_bank_accid")
    private String clearingBankAccId;

    /** ID of the clearing member **/
    @JsonProperty("clearing_firmid")
    private String clearingFirmId;

    /** Additional information on the transaction transferred by the trading system **/
    @JsonProperty("system_ref")
    private String systemRef;

    /** Type of the trading session **/
    @JsonProperty("period")
    private TradingPeriodType period;

    /** Type of trade **/
    @JsonProperty("kind")
    private Integer kind;

    public Trade()
    {

    }

    @Override
    public String toString()
    {
        return "Trade{" +
                "transId=" + transId +
                ", uid=" + uid +
                ", tradeNumber=" + tradeNumber +
                ", orderNumber=" + orderNumber +
                ", brokerref='" + brokerref + '\'' +
                ", userId='" + userId + '\'' +
                ", firmId='" + firmId + '\'' +
                ", account='" + account + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", value=" + value +
                ", accruedCouponYield=" + accruedCouponYield +
                ", yield=" + yield +
                ", paymentCode='" + paymentCode + '\'' +
                ", cpFirmId='" + cpFirmId + '\'' +
                ", flags=" + flags +
                ", redemptionPrice=" + redemptionPrice +
                ", repoRate=" + repoRate +
                ", clientCode=" + clientCode +
                ", redemptionIncome=" + redemptionIncome +
                ", repoTerm=" + repoTerm +
                ", repoValue=" + repoValue +
                ", repoVolume=" + repoVolume +
                ", startDiscount=" + startDiscount +
                ", lowerDiscount=" + lowerDiscount +
                ", upperDiscount=" + upperDiscount +
                ", collateralLock=" + collateralLock +
                ", clearingComission=" + clearingComission +
                ", exchangeComission=" + exchangeComission +
                ", techCenterComission=" + techCenterComission +
                ", brokerComission=" + brokerComission +
                ", settlementDate=" + settlementDate +
                ", settlementCurrency='" + settlementCurrency + '\'' +
                ", tradeCurrency='" + tradeCurrency + '\'' +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", classCode='" + classCode + '\'' +
                ", dateTime=" + dateTime +
                ", cancelationDateTime=" + cancelationDateTime +
                ", bankAccId='" + bankAccId + '\'' +
                ", linkedTrade=" + linkedTrade +
                ", clearingBankAccId='" + clearingBankAccId + '\'' +
                ", clearingFirmId='" + clearingFirmId + '\'' +
                ", systemRef='" + systemRef + '\'' +
                ", period=" + period +
                ", kind=" + kind +
                '}';
    }

    public Long getTransId()
    {
        return transId;
    }

    public void setTransId(Long transId)
    {
        this.transId = transId;
    }

    public Long getUid()
    {
        return uid;
    }

    public void setUid(Long uid)
    {
        this.uid = uid;
    }

    public Long getTradeNumber()
    {
        return tradeNumber;
    }

    public void setTradeNumber(Long tradeNumber)
    {
        this.tradeNumber = tradeNumber;
    }

    public Long getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public String getBrokerref()
    {
        return brokerref;
    }

    public void setBrokerref(String brokerref)
    {
        this.brokerref = brokerref;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getFirmId()
    {
        return firmId;
    }

    public void setFirmId(String firmId)
    {
        this.firmId = firmId;
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

    public Long getQty()
    {
        return qty;
    }

    public void setQty(Long qty)
    {
        this.qty = qty;
    }

    public Double getValue()
    {
        return value;
    }

    public void setValue(Double value)
    {
        this.value = value;
    }

    public Double getAccruedCouponYield()
    {
        return accruedCouponYield;
    }

    public void setAccruedCouponYield(Double accruedCouponYield)
    {
        this.accruedCouponYield = accruedCouponYield;
    }

    public Double getYield()
    {
        return yield;
    }

    public void setYield(Double yield)
    {
        this.yield = yield;
    }

    public String getPaymentCode()
    {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode)
    {
        this.paymentCode = paymentCode;
    }

    public String getCpFirmId()
    {
        return cpFirmId;
    }

    public void setCpFirmId(String cpFirmId)
    {
        this.cpFirmId = cpFirmId;
    }

    public Integer getFlags()
    {
        return flags;
    }

    public void setFlags(Integer flags)
    {
        this.flags = flags;
    }

    public Double getRedemptionPrice()
    {
        return redemptionPrice;
    }

    public void setRedemptionPrice(Double redemptionPrice)
    {
        this.redemptionPrice = redemptionPrice;
    }

    public Double getRepoRate()
    {
        return repoRate;
    }

    public void setRepoRate(Double repoRate)
    {
        this.repoRate = repoRate;
    }

    public Long getClientCode()
    {
        return clientCode;
    }

    public void setClientCode(Long clientCode)
    {
        this.clientCode = clientCode;
    }

    public Double getRedemptionIncome()
    {
        return redemptionIncome;
    }

    public void setRedemptionIncome(Double redemptionIncome)
    {
        this.redemptionIncome = redemptionIncome;
    }

    public Long getRepoTerm()
    {
        return repoTerm;
    }

    public void setRepoTerm(Long repoTerm)
    {
        this.repoTerm = repoTerm;
    }

    public Double getRepoValue()
    {
        return repoValue;
    }

    public void setRepoValue(Double repoValue)
    {
        this.repoValue = repoValue;
    }

    public Double getRepoVolume()
    {
        return repoVolume;
    }

    public void setRepoVolume(Double repoVolume)
    {
        this.repoVolume = repoVolume;
    }

    public Double getStartDiscount()
    {
        return startDiscount;
    }

    public void setStartDiscount(Double startDiscount)
    {
        this.startDiscount = startDiscount;
    }

    public Double getLowerDiscount()
    {
        return lowerDiscount;
    }

    public void setLowerDiscount(Double lowerDiscount)
    {
        this.lowerDiscount = lowerDiscount;
    }

    public Double getUpperDiscount()
    {
        return upperDiscount;
    }

    public void setUpperDiscount(Double upperDiscount)
    {
        this.upperDiscount = upperDiscount;
    }

    public Integer getCollateralLock()
    {
        return collateralLock;
    }

    public void setCollateralLock(Integer collateralLock)
    {
        this.collateralLock = collateralLock;
    }

    public Double getClearingComission()
    {
        return clearingComission;
    }

    public void setClearingComission(Double clearingComission)
    {
        this.clearingComission = clearingComission;
    }

    public Double getExchangeComission()
    {
        return exchangeComission;
    }

    public void setExchangeComission(Double exchangeComission)
    {
        this.exchangeComission = exchangeComission;
    }

    public Double getTechCenterComission()
    {
        return techCenterComission;
    }

    public void setTechCenterComission(Double techCenterComission)
    {
        this.techCenterComission = techCenterComission;
    }

    public Double getBrokerComission()
    {
        return brokerComission;
    }

    public void setBrokerComission(Double brokerComission)
    {
        this.brokerComission = brokerComission;
    }

    public Long getSettlementDate()
    {
        return settlementDate;
    }

    public void setSettlementDate(Long settlementDate)
    {
        this.settlementDate = settlementDate;
    }

    public String getSettlementCurrency()
    {
        return settlementCurrency;
    }

    public void setSettlementCurrency(String settlementCurrency)
    {
        this.settlementCurrency = settlementCurrency;
    }

    public String getTradeCurrency()
    {
        return tradeCurrency;
    }

    public void setTradeCurrency(String tradeCurrency)
    {
        this.tradeCurrency = tradeCurrency;
    }

    public String getExchangeCode()
    {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode)
    {
        this.exchangeCode = exchangeCode;
    }

    public String getSecurityCode()
    {
        return securityCode;
    }

    public void setSecurityCode(String securityCode)
    {
        this.securityCode = securityCode;
    }

    public String getClassCode()
    {
        return classCode;
    }

    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }

    public DateTime getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime)
    {
        this.dateTime = dateTime;
    }

    public DateTime getCancelationDateTime()
    {
        return cancelationDateTime;
    }

    public void setCancelationDateTime(DateTime cancelationDateTime)
    {
        this.cancelationDateTime = cancelationDateTime;
    }

    public String getBankAccId()
    {
        return bankAccId;
    }

    public void setBankAccId(String bankAccId)
    {
        this.bankAccId = bankAccId;
    }

    public Long getLinkedTrade()
    {
        return linkedTrade;
    }

    public void setLinkedTrade(Long linkedTrade)
    {
        this.linkedTrade = linkedTrade;
    }

    public String getClearingBankAccId()
    {
        return clearingBankAccId;
    }

    public void setClearingBankAccId(String clearingBankAccId)
    {
        this.clearingBankAccId = clearingBankAccId;
    }

    public String getClearingFirmId()
    {
        return clearingFirmId;
    }

    public void setClearingFirmId(String clearingFirmId)
    {
        this.clearingFirmId = clearingFirmId;
    }

    public String getSystemRef()
    {
        return systemRef;
    }

    public void setSystemRef(String systemRef)
    {
        this.systemRef = systemRef;
    }

    public TradingPeriodType getPeriod()
    {
        return period;
    }

    public void setPeriod(TradingPeriodType period)
    {
        this.period = period;
    }

    public Integer getKind()
    {
        return kind;
    }

    public void setKind(Integer kind)
    {
        this.kind = kind;
    }
}
