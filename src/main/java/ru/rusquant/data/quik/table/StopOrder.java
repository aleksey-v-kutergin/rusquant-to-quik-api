package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.DateTime;
import ru.rusquant.data.quik.QuikDataObject;

/**
 * Java implementation for stop_orders table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class StopOrder extends QuikDataObject {

    /**
     * Registration number of the stop request on the QUIK server
     **/
    @JsonProperty("order_num")
    private Long orderNumber;

    /**
     * Time of issue
     **/
    @JsonProperty("ordertime")
    private Long orderTime;

    /**
     * Set of bit flags:
     *       - bit 0 (0x1) The application is active, otherwise it is not active
     *       - bit 1 (0x2) The request has been withdrawn. If not set and the value of bit 0 is 0, then the application is executed
     *       - bit 2 (0x4) Application for sale, otherwise - for purchase
     *       - bit 3 (0x8) Limited order
     *       - bit 5 (0x20) Stop request waiting for activation
     *       - bit 6 (0x40) Stop application from another server
     *       - bit 8 (0x100) Set in the case of a stop order of the take-profit type on the application, in the case where the initial bid is partially executed and the activation condition for the executed part of the bid was fulfilled
     *       - bit 9 (0x200) Stop application activated manually
     *       - bit 10 (0x400) The stop order worked, but was rejected by the trading system
     *       - bit 11 (0x800) Stop-order has worked, but has not passed the limit control
     *       - bit 12 (0x1000) The stop order has been withdrawn, since the related application has been withdrawn
     *       - bit 13 (0x2000) The stop order is canceled, because the linked order is executed
     *       - bit 15 (0x8000) Calculation of the minimum-maximum
     **/
    @JsonProperty("flags")
    private Integer flags;

    /**
     * Comment, usually: <customer code> / <order number>
     **/
    @JsonProperty("brokerref")
    private String brokerRef;

    /**
     * Dealer ID
     **/
    @JsonProperty("firmid")
    private String firmId;

    /**
     * Trading account
     **/
    @JsonProperty("account")
    private String account;

    /**
     * The stop price direction. The possible values are:
     *        "4" - "<="
     *        "5" - "> ="
     **/
    @JsonProperty("condition")
    private Integer condition;

    /**
     * Stop price
     **/
    @JsonProperty("condition_price")
    private Double conditionPrice;

    /**
     * Price
     **/
    @JsonProperty("price")
    private Double price;

    /**
     * Count in lots
     **/
    @JsonProperty("qty")
    private Long qty;

    /**
     * The number of the application in the trading system registered on the arrival of the condition of the stop price
     **/
    @JsonProperty("linkedorder")
    private Long linkedOrder;

    /**
     * Expiry date of the order
     **/
    @JsonProperty("expiry")
    private Long expiry;

    /**
     * Transaction identifier
     **/
    @JsonProperty("trans_id")
    private Long transactionId;

    /**
     * Client code
     **/
    @JsonProperty("client_code")
    private String clientCode;

    /**
     * Associated order number
     **/
    @JsonProperty("co_order_num")
    private Long coOrderNumber;

    /**
     * Associated order price
     **/
    @JsonProperty("co_order_price")
    private Double coOrderPrice;

    /**
     * Stop order type. The possible values are:
     *        "1" is the stop-limit
     *        "2" is a condition for another instrument
     *        "3" - with related application
     *        "6" - take profit
     *        "7" - stop-limit for the execution of an active application
     *        "8" - take-profit for the execution of an active application
     *        "9" - take-profit and stop-limit
     **/
    @JsonProperty("stop_order_type")
    private Integer stopOrderType;

    /**
     * Date of issue
     **/
    @JsonProperty("orderdate")
    private Long orderDate;

    /**
     * Condition Transaction
     **/
    @JsonProperty("alltrade_num")
    private Long alltradeNumber;

    /**
     * Set of bit flags (NUMBER)
     *       - Bit 0 (0x1) Use the rest of the main application
     *       - bit 1 (0x2) In case of partial execution of the application, withdraw a stop order
     *       - bit 2 (0x4) Activate stop order when partial execution of the linked application
     *       - bit 3 (0x8) Indent is set in percentage, otherwise - in price points
     *       - bit 4 (0x10) The protection spread is set in percentage, otherwise - at price points
     *       - Bit 5 (0x20) The validity of the stop order is limited to today's day
     *       - bit 6 (0x40) The time interval for the stop order has been set
     *       - Bit 7 (0x80) Execution of Take Profit at Market Price
     *       - bit 8 (0x100) Executing a stop order at the market price
     **/
    @JsonProperty("stopflags")
    private Integer stopFlags;

    /**  **/
    @JsonProperty("Offset from min / max")
    private Double offset;

    /**
     * Protective spread
     **/
    @JsonProperty("spread")
    private Double spread;

    /**
     * Active quantity
     **/
    @JsonProperty("balance")
    private Double balance;

    /**
     * User identifier
     **/
    @JsonProperty("uid")
    private Long uid;

    /**
     * Filled amount
     **/
    @JsonProperty("filled_qty")
    private Long filledQty;

    /**
     * Time of withdrawal
     **/
    @JsonProperty("withdraw_time")
    private Long withdrawTime;

    /**
     * Stop-limit price (for applications such as "Take Profit and Stop Limit")
     **/
    @JsonProperty("condition_price2")
    private Double conditionPrice2;

    /**
     * Time of the beginning of the validity period of the application of the "Take-profit and stop-limit" type
     **/
    @JsonProperty("active_from_time")
    private Long activeFromTime;

    /**
     * Time of expiry of the application period of the "Take-profit and stop-limit" type
     **/
    @JsonProperty("active_to_time")
    private Long activeToTime;

    /**
     * Security code
     **/
    @JsonProperty("sec_code")
    private String secCode;

    /**
     * Security class code
     **/
    @JsonProperty("class_code")
    private String classCode;

    /**
     * Stop price security code
     **/
    @JsonProperty("condition_sec_code")
    private String conditionSecurityCode;

    /**
     * Stop price security class code
     **/
    @JsonProperty("condition_class_code")
    private String conditionClassCode;

    /**
     * ID of the user who withdrew the stop order
     **/
    @JsonProperty("canceled_uid")
    private Long canceledUserid;

    /**
     * Date and time to put a stop order
     **/
    @JsonProperty("order_date_time")
    private DateTime orderDateTime;

    /**
     * Withdraw date and of stop order
     **/
    @JsonProperty("withdraw_datetime")
    private DateTime withdrawDatetime;

    public StopOrder() {

    }

    @Override
    public String toString() {
        return "StopOrder{" +
                "orderNumber=" + orderNumber +
                ", orderTime=" + orderTime +
                ", flags=" + flags +
                ", brokerRef='" + brokerRef + '\'' +
                ", firmId='" + firmId + '\'' +
                ", account='" + account + '\'' +
                ", condition=" + condition +
                ", conditionPrice=" + conditionPrice +
                ", price=" + price +
                ", qty=" + qty +
                ", linkedOrder=" + linkedOrder +
                ", expiry=" + expiry +
                ", transactionId=" + transactionId +
                ", clientCode='" + clientCode + '\'' +
                ", coOrderNumber=" + coOrderNumber +
                ", coOrderPrice=" + coOrderPrice +
                ", stopOrderType=" + stopOrderType +
                ", orderDate=" + orderDate +
                ", alltradeNumber=" + alltradeNumber +
                ", stopFlags=" + stopFlags +
                ", offset=" + offset +
                ", spread=" + spread +
                ", balance=" + balance +
                ", uid=" + uid +
                ", filledQty=" + filledQty +
                ", withdrawTime=" + withdrawTime +
                ", conditionPrice2=" + conditionPrice2 +
                ", activeFromTime=" + activeFromTime +
                ", activeToTime=" + activeToTime +
                ", secCode='" + secCode + '\'' +
                ", classCode='" + classCode + '\'' +
                ", conditionSecurityCode='" + conditionSecurityCode + '\'' +
                ", conditionClassCode='" + conditionClassCode + '\'' +
                ", canceledUserid=" + canceledUserid +
                ", orderDateTime=" + orderDateTime +
                ", withdrawDatetime=" + withdrawDatetime +
                '}';
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Long orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public String getBrokerRef() {
        return brokerRef;
    }

    public void setBrokerRef(String brokerRef) {
        this.brokerRef = brokerRef;
    }

    public String getFirmId() {
        return firmId;
    }

    public void setFirmId(String firmId) {
        this.firmId = firmId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public Double getConditionPrice() {
        return conditionPrice;
    }

    public void setConditionPrice(Double conditionPrice) {
        this.conditionPrice = conditionPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getLinkedOrder() {
        return linkedOrder;
    }

    public void setLinkedOrder(Long linkedOrder) {
        this.linkedOrder = linkedOrder;
    }

    public Long getExpiry() {
        return expiry;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Long getCoOrderNumber() {
        return coOrderNumber;
    }

    public void setCoOrderNumber(Long coOrderNumber) {
        this.coOrderNumber = coOrderNumber;
    }

    public Double getCoOrderPrice() {
        return coOrderPrice;
    }

    public void setCoOrderPrice(Double coOrderPrice) {
        this.coOrderPrice = coOrderPrice;
    }

    public Integer getStopOrderType() {
        return stopOrderType;
    }

    public void setStopOrderType(Integer stopOrderType) {
        this.stopOrderType = stopOrderType;
    }

    public Long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Long orderDate) {
        this.orderDate = orderDate;
    }

    public Long getAlltradeNumber() {
        return alltradeNumber;
    }

    public void setAlltradeNumber(Long alltradeNumber) {
        this.alltradeNumber = alltradeNumber;
    }

    public Integer getStopFlags() {
        return stopFlags;
    }

    public void setStopFlags(Integer stopFlags) {
        this.stopFlags = stopFlags;
    }

    public Double getOffset() {
        return offset;
    }

    public void setOffset(Double offset) {
        this.offset = offset;
    }

    public Double getSpread() {
        return spread;
    }

    public void setSpread(Double spread) {
        this.spread = spread;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getFilledQty() {
        return filledQty;
    }

    public void setFilledQty(Long filledQty) {
        this.filledQty = filledQty;
    }

    public Long getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(Long withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public Double getConditionPrice2() {
        return conditionPrice2;
    }

    public void setConditionPrice2(Double conditionPrice2) {
        this.conditionPrice2 = conditionPrice2;
    }

    public Long getActiveFromTime() {
        return activeFromTime;
    }

    public void setActiveFromTime(Long activeFromTime) {
        this.activeFromTime = activeFromTime;
    }

    public Long getActiveToTime() {
        return activeToTime;
    }

    public void setActiveToTime(Long activeToTime) {
        this.activeToTime = activeToTime;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getConditionSecurityCode() {
        return conditionSecurityCode;
    }

    public void setConditionSecurityCode(String conditionSecurityCode) {
        this.conditionSecurityCode = conditionSecurityCode;
    }

    public String getConditionClassCode() {
        return conditionClassCode;
    }

    public void setConditionClassCode(String conditionClassCode) {
        this.conditionClassCode = conditionClassCode;
    }

    public Long getCanceledUserid() {
        return canceledUserid;
    }

    public void setCanceledUserid(Long canceledUserid) {
        this.canceledUserid = canceledUserid;
    }

    public DateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(DateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public DateTime getWithdrawDatetime() {
        return withdrawDatetime;
    }

    public void setWithdrawDatetime(DateTime withdrawDatetime) {
        this.withdrawDatetime = withdrawDatetime;
    }
}
