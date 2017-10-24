package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Java implementation for OnOrder(order) qlua callback argument.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order extends QuikDataObject
{
    /** Order number, assigned in trading system **/
    @JsonProperty("order_num")
    private Long orderNum;

    /** Bit-mask **/
    @JsonProperty("flags")
    private Integer flags;

    /** Comment from broker. Usually: <client_code>/<order_number> **/
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

    /** Balance **/
    @JsonProperty("balance")
    private Double balance;

    /** Volume in cash **/
    @JsonProperty("value")
    private Double value;

    /** Accrued coupon yield **/
    @JsonProperty("accruedint")
    private Double accruedCouponYield;

    /** Accrued coupon yield **/
    @JsonProperty("yield")
    private Double yield;

    /** Identifier of the transaction **/
    @JsonProperty("trans_id")
    private Long transId;

    /** Code of the client **/
    @JsonProperty("client_code")
    private Long clientCode;

    /** Redemption price **/
    @JsonProperty("price2")
    private Double redemptionPrice;

    /** Payment code **/
    @JsonProperty("settlecode")
    private String paymentCode;

    /** User identifier **/
    @JsonProperty("uid")
    private Long uid;

    /** Exchange code in the trading system **/
    @JsonProperty("exchange_code")
    private String exchangeCode;

    /** Time of activation **/
    @JsonProperty("activation_time")
    private Long activationTime;

    /** Docs here are unclear. Looks similar to id of linked order **/
    @JsonProperty("linkedorder")
    private Long linkedOrder;

    /** Expiry date of the order **/
    @JsonProperty("expiry")
    private Long expiry;

    /** Code of the security **/
    @JsonProperty("sec_code")
    private String securityCode;

    /** Code of the class **/
    @JsonProperty("class_code")
    private String classCode;

    /** Date and time of order **/
    @JsonProperty("datetime")
    private DateTime dateTime;

    /** Date and time of withdraw of the order **/
    @JsonProperty("withdraw_datetime")
    private DateTime withdrawDatetime;

    /** Identifier of the settlement account / code in the clearing organization **/
    @JsonProperty("bank_acc_id")
    private String bankAccId;

    /**
     *    A method for indicating the volume of an application. The possible values are:
     *    0 - by number
     *    1 - by volume
     **/
    @JsonProperty("value_entry_type")
    private Integer valueEntryType;

    /** Term of repurchase, in calendar days **/
    @JsonProperty("repoterm")
    private Long repoTerm;

    /** The amount of REPO at the current date **/
    @JsonProperty("repovalue")
    private Double repoValue;

    /** Volume of REPO repurchase agreement **/
    @JsonProperty("repo2value")
    private Double repoVolume;

    /**
     * The rest of the repurchase amount,
     * less the amount of funds attracted or provided under the repurchase agreement in the unperformed part of the application,
     * as of the current date
     **/
    @JsonProperty("repo_value_balance")
    private Double repoValueBalance;

    /** Initial discount **/
    @JsonProperty("start_discount")
    private Double startDiscount;

    /** Reason for rejection by broker **/
    @JsonProperty("reject_reason")
    private String rejectReason;

    /** Bit field for obtaining specific parameters from western sites **/
    @JsonProperty("ext_order_flags")
    private Integer extOrderFlags;

    /** Bit field for obtaining specific parameters from western sites. 0 - means undefined **/
    @JsonProperty("min_qty")
    private Long minQty;

    /** Apparent amount. The parameter of the iceberg-orders, for ordinary orders, the value: 0 **/
    @JsonProperty("visible")
    private Long visible;

    /** Field for obtaining parameters on western exchanges. 0 - means undefined. **/

    @JsonProperty("exec_type")
    private Integer execType;

    @JsonProperty("side_qualifier")
    private Integer sideQualifier;

    @JsonProperty("acnt_type")
    private Integer acntType;

    @JsonProperty("capacity")
    private Integer capacity;

    @JsonProperty("passive_only_order")
    private Integer passiveOnlyOrder;


    public Order()
    {

    }


    @Override
    public String toString()
    {
        String str = "Order: ";
        str += "\n\t\torder_num: " + orderNum;
        str += "\n\t\tflags: " + flags;
        str += "\n\t\tbrokerref: " + brokerref;
        str += "\n\t\tuserid: " + userId;
        str += "\n\t\tfirmid: " + firmId;
        str += "\n\t\taccount: " + account;
        str += "\n\t\tprice: " + price;
        str += "\n\t\tqty: " + qty;
        str += "\n\t\tbalance: " + balance;
        str += "\n\t\tvalue: " + value;
        str += "\n\t\taccruedint: " + accruedCouponYield;
        str += "\n\t\tyield: " + yield;
        str += "\n\t\ttrans_id: " + transId;
        str += "\n\t\tclient_code: " + clientCode;
        str += "\n\t\tprice2: " + redemptionPrice;
        str += "\n\t\tsettlecode: " + paymentCode;
        str += "\n\t\tuid: " + uid;
        str += "\n\t\texchange_code: " + exchangeCode;
        str += "\n\t\tactivation_time: " + activationTime;
        str += "\n\t\tlinkedorder: " + linkedOrder;
        str += "\n\t\texpiry: " + expiry;
        str += "\n\t\tsec_code: " + securityCode;
        str += "\n\t\tclass_code: " + classCode;
        str += "\n\t\tdatetime: " + dateTime;
        str += "\n\t\twithdraw_datetime: " + withdrawDatetime;
        str += "\n\t\tbank_acc_id: " + bankAccId;
        str += "\n\t\tvalue_entry_type: " + valueEntryType;
        str += "\n\t\trepoterm: " + repoTerm;
        str += "\n\t\trepovalue: " + repoValue;
        str += "\n\t\trepo2value: " + repoVolume;
        str += "\n\t\trepo_value_balance: " + repoValueBalance;
        str += "\n\t\tstart_discount: " + startDiscount;
        str += "\n\t\treject_reason: " + rejectReason;
        str += "\n\t\text_order_flags: " + extOrderFlags;
        str += "\n\t\tmin_qty: " + minQty;
        str += "\n\t\tvisible: " + visible;
        return str;
    }

    public Long getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Long orderNum)
    {
        this.orderNum = orderNum;
    }

    public Integer getFlags()
    {
        return flags;
    }

    public void setFlags(Integer flags)
    {
        this.flags = flags;
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

    public Double getBalance()
    {
        return balance;
    }

    public void setBalance(Double balance)
    {
        this.balance = balance;
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

    public Long getTransId()
    {
        return transId;
    }

    public void setTransId(Long transId)
    {
        this.transId = transId;
    }

    public Long getClientCode()
    {
        return clientCode;
    }

    public void setClientCode(Long clientCode)
    {
        this.clientCode = clientCode;
    }

    public Double getRedemptionPrice()
    {
        return redemptionPrice;
    }

    public void setRedemptionPrice(Double redemptionPrice)
    {
        this.redemptionPrice = redemptionPrice;
    }

    public String getPaymentCode()
    {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode)
    {
        this.paymentCode = paymentCode;
    }

    public Long getUid()
    {
        return uid;
    }

    public void setUid(Long uid)
    {
        this.uid = uid;
    }

    public String getExchangeCode()
    {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode)
    {
        this.exchangeCode = exchangeCode;
    }

    public Long getActivationTime()
    {
        return activationTime;
    }

    public void setActivationTime(Long activationTime)
    {
        this.activationTime = activationTime;
    }

    public Long getLinkedOrder()
    {
        return linkedOrder;
    }

    public void setLinkedOrder(Long linkedOrder)
    {
        this.linkedOrder = linkedOrder;
    }

    public Long getExpiry()
    {
        return expiry;
    }

    public void setExpiry(Long expiry)
    {
        this.expiry = expiry;
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

    public DateTime getWithdrawDatetime()
    {
        return withdrawDatetime;
    }

    public void setWithdrawDatetime(DateTime withdrawDatetime)
    {
        this.withdrawDatetime = withdrawDatetime;
    }

    public String getBankAccId()
    {
        return bankAccId;
    }

    public void setBankAccId(String bankAccId)
    {
        this.bankAccId = bankAccId;
    }

    public Integer getValueEntryType()
    {
        return valueEntryType;
    }

    public void setValueEntryType(Integer valueEntryType)
    {
        this.valueEntryType = valueEntryType;
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

    public Double getRepoValueBalance()
    {
        return repoValueBalance;
    }

    public void setRepoValueBalance(Double repoValueBalance)
    {
        this.repoValueBalance = repoValueBalance;
    }

    public Double getStartDiscount()
    {
        return startDiscount;
    }

    public void setStartDiscount(Double startDiscount)
    {
        this.startDiscount = startDiscount;
    }

    public String getRejectReason()
    {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason)
    {
        this.rejectReason = rejectReason;
    }

    public Integer getExtOrderFlags()
    {
        return extOrderFlags;
    }

    public void setExtOrderFlags(Integer extOrderFlags)
    {
        this.extOrderFlags = extOrderFlags;
    }

    public Long getMinQty()
    {
        return minQty;
    }

    public void setMinQty(Long minQty)
    {
        this.minQty = minQty;
    }

    public Long getVisible()
    {
        return visible;
    }

    public void setVisible(Long visible)
    {
        this.visible = visible;
    }

    public Integer getExecType()
    {
        return execType;
    }

    public void setExecType(Integer execType)
    {
        this.execType = execType;
    }

    public Integer getSideQualifier()
    {
        return sideQualifier;
    }

    public void setSideQualifier(Integer sideQualifier)
    {
        this.sideQualifier = sideQualifier;
    }

    public Integer getAcntType()
    {
        return acntType;
    }

    public void setAcntType(Integer acntType)
    {
        this.acntType = acntType;
    }

    public Integer getCapacity()
    {
        return capacity;
    }

    public void setCapacity(Integer capacity)
    {
        this.capacity = capacity;
    }

    public Integer getPassiveOnlyOrder()
    {
        return passiveOnlyOrder;
    }

    public void setPassiveOnlyOrder(Integer passiveOnlyOrder)
    {
        this.passiveOnlyOrder = passiveOnlyOrder;
    }
}