package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.DateTime;
import ru.rusquant.data.quik.QuikDataObject;

/**
 * Java implementation for neg_deal table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class NegDeal extends QuikDataObject {

    /**
     * Neg deal number
     **/
    @JsonProperty("neg_deal_num")
    private Long negDealNumber;

    /**
     * Post time of deal
     **/
    @JsonProperty("neg_deal_time")
    private Long negDealTime;

    /**
     * Set of bit flags:
     *       - bit 0 (0x1) The application is active, otherwise - not active
     *       - bit 1 (0x2) The request has been withdrawn. If the flag is not set and the value of bit "0" is "0", then the request is executed
     *       - bit 2 (0x4) The application for sale, otherwise - for purchase. This flag for transactions and transactions for execution determines the direction of the transaction (BUY / SELL)
     *       - bit 3 (0x8) The order is limited, otherwise - market
     *       - bit 4 (0x10) Allow / prohibit transactions at different prices
     *       - Bit 5 (0x20) Fulfill an order immediately or withdraw (FILL OR KILL)
     *       - bit 6 (0x40) The application of the market-maker. For targeted applications - the application was sent to the counterparty
     *       - bit 7 (0x80) For address requests - the application was received from the counterparty
     *       - bit 8 (0x100) Remove the remainder
     *       - bit 9 (0x200) Iceberg application
     **/
    @JsonProperty("flags")
    private Integer flags;

    /**
     * Comment, usually: <customer code> / <order number>
     **/
    @JsonProperty("brokerref")
    private String brokerReference;

    /**
     * Trader
     **/
    @JsonProperty("userid")
    private String userId;

    /**
     * Dealer ID
     **/
    @JsonProperty("firmid")
    private String firmId;

    /**
     * Partner trader
     **/
    @JsonProperty("cpuserid")
    private String cpUserId;

    /**
     * Partner-firm code
     **/
    @JsonProperty("cpfirmid")
    private String cpFirmId;

    /**
     * Trading account
     **/
    @JsonProperty("account")
    private String account;

    /**
     * Price
     **/
    @JsonProperty("price")
    private Double price;

    /**
     * Quantity???
     **/
    @JsonProperty("qty")
    private Long qty;

    /**
     * Reference???
     **/
    @JsonProperty("matchref")
    private String matchReference;

    /**
     * Settlement code
     **/
    @JsonProperty("settlecode")
    private String settlementCode;

    /**
     * Profitability
     **/
    @JsonProperty("yield")
    private Double yield;

    /**
     * Coupon interest
     **/
    @JsonProperty("accruedint")
    private Double accruedInterest;

    /**
     * Volume???
     **/
    @JsonProperty("value")
    private Double value;

    /**
     * Redemption price
     **/
    @JsonProperty("price2")
    private Double redemptionPrice;

    /**
     * REPO rate (%)
     **/
    @JsonProperty("reporate")
    private Double repoRate;

    /**
     * Refund rate (%)
     **/
    @JsonProperty("refundrate")
    private Double refundRate;

    /**
     * Transaction identifier
     **/
    @JsonProperty("trans_id")
    private Long transId;

    /**
     * Client code
     **/
    @JsonProperty("client_code")
    private String clientCode;

    /**
     * Type of repo request entry. The possible values are:
     * "0" - "Not defined";
     * "1" - "Price1 + Rate";
     * "2" - "Bet + Price2";
     * "3" - "Price1 + Price2";
     * "4" - "REPO Amount + Amount";
     * "5" - "Amount of REPO + Discount";
     * "6" - "Quantity + Discount";
     * "7" - "Amount of REPO";
     * "8" - "Quantity"
     **/
    @JsonProperty("repoentry")
    private Integer repoEntry;

    /**
     * Amount of REPO
     **/
    @JsonProperty("repovalue")
    private Double repoValue;

    /**
     * Repurchase amount of REPO
     **/
    @JsonProperty("repo2value")
    private Double repo2value;

    /**
     * REPO term
     **/
    @JsonProperty("repoterm")
    private Long repoTerm;

    /**
     * Initial discount (%)
     **/
    @JsonProperty("start_discount")
    private Double startDiscount;

    /**
     * Lower discount (%)
     **/
    @JsonProperty("lower_discount")
    private Double lowerDiscount;

    /**
     * Upper discount (%)
     **/
    @JsonProperty("upper_discount")
    private Double upperDiscount;

    /**
     * Collateral lock ("Yes" / "No")
     **/
    @JsonProperty("block_securities")
    private Integer blockSecurities;

    /**
     * User identifier
     **/
    @JsonProperty("uid")
    private Long uid;

    /**
     * Time of withdrawal
     **/
    @JsonProperty("withdraw_time")
    private Long withdrawTime;

    /**
     * Date of neg deal
     **/
    @JsonProperty("neg_deal_date")
    private Long negDealDate;

    /**
     * Balance???
     **/
    @JsonProperty("balance")
    private Double balance;

    /**
     * Initial amount of REPO
     **/
    @JsonProperty("origin_repovalue")
    private Double originRepoValue;

    /**
     * Initial quantity
     **/
    @JsonProperty("origin_qty")
    private Double originQty;

    /**
     * Initial discount interest rate
     **/
    @JsonProperty("origin_discount")
    private Double originDiscount;

    /**
     * Neg deal activation date
     **/
    @JsonProperty("neg_deal_activation_date")
    private Long negDealActivationDate;

    /**
     * Neg deal activation time
     **/
    @JsonProperty("neg_deal_activation_time")
    private Long negDealActivationTime;

    /**
     * Counter addressless application
     **/
    @JsonProperty("quoteno")
    private Long quoteno;

    /**
     * Settlement currency
     **/
    @JsonProperty("settle_currency")
    private Long settlementCurrency;

    /**
     * Security code
     **/
    @JsonProperty("sec_code")
    private String securityCode;

    /**
     * Security class code
     **/
    @JsonProperty("class_code")
    private String classCode;

    /**
     * Identifier of the settlement account / code in the clearing organization
     **/
    @JsonProperty("bank_acc_id")
    private String bankAccountId;

    /**
     * Date of withdrawal of the order in the format "YYYYMMDD"
     **/
    @JsonProperty("withdraw_date")
    private Long withdrawDate;

    /**
     * Number of the previous order. Displayed with an accuracy of "0"
     **/
    @JsonProperty("linkedorder")
    private Long linkedOrder;

    /**
     * Activation date time
     **/
    @JsonProperty("activation_date_time")
    private DateTime activationDateTime;

    /**
     * withdraw date time
     **/
    @JsonProperty("withdraw_date_time")
    private DateTime withdrawDateTime;

    /**
     * Date and time
     **/
    @JsonProperty("date_time")
    private DateTime dateTime;

    public NegDeal() {

    }

    @Override
    public String toString() {
        return "NegDeal: {" +
                "negDealNumber=" + negDealNumber +
                ", negDealTime=" + negDealTime +
                ", flags=" + flags +
                ", brokerReference='" + brokerReference + '\'' +
                ", userId='" + userId + '\'' +
                ", firmId='" + firmId + '\'' +
                ", cpUserId='" + cpUserId + '\'' +
                ", cpFirmId='" + cpFirmId + '\'' +
                ", account='" + account + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", matchReference='" + matchReference + '\'' +
                ", settlementCode='" + settlementCode + '\'' +
                ", yield=" + yield +
                ", accruedInterest=" + accruedInterest +
                ", value=" + value +
                ", redemptionPrice=" + redemptionPrice +
                ", repoRate=" + repoRate +
                ", refundRate=" + refundRate +
                ", transId=" + transId +
                ", clientCode='" + clientCode + '\'' +
                ", repoEntry=" + repoEntry +
                ", repoValue=" + repoValue +
                ", repo2value=" + repo2value +
                ", repoTerm=" + repoTerm +
                ", startDiscount=" + startDiscount +
                ", lowerDiscount=" + lowerDiscount +
                ", upperDiscount=" + upperDiscount +
                ", blockSecurities=" + blockSecurities +
                ", uid=" + uid +
                ", withdrawTime=" + withdrawTime +
                ", negDealDate=" + negDealDate +
                ", balance=" + balance +
                ", originRepoValue=" + originRepoValue +
                ", originQty=" + originQty +
                ", originDiscount=" + originDiscount +
                ", negDealActivationDate=" + negDealActivationDate +
                ", negDealActivationTime=" + negDealActivationTime +
                ", quoteno=" + quoteno +
                ", settlementCurrency=" + settlementCurrency +
                ", securityCode='" + securityCode + '\'' +
                ", classCode='" + classCode + '\'' +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", withdrawDate=" + withdrawDate +
                ", linkedOrder=" + linkedOrder +
                ", activationDateTime=" + activationDateTime +
                ", withdrawDateTime=" + withdrawDateTime +
                ", dateTime=" + dateTime +
                '}';
    }

    public Long getNegDealNumber() {
        return negDealNumber;
    }

    public void setNegDealNumber(Long negDealNumber) {
        this.negDealNumber = negDealNumber;
    }

    public Long getNegDealTime() {
        return negDealTime;
    }

    public void setNegDealTime(Long negDealTime) {
        this.negDealTime = negDealTime;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public String getBrokerReference() {
        return brokerReference;
    }

    public void setBrokerReference(String brokerReference) {
        this.brokerReference = brokerReference;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirmId() {
        return firmId;
    }

    public void setFirmId(String firmId) {
        this.firmId = firmId;
    }

    public String getCpUserId() {
        return cpUserId;
    }

    public void setCpUserId(String cpUserId) {
        this.cpUserId = cpUserId;
    }

    public String getCpFirmId() {
        return cpFirmId;
    }

    public void setCpFirmId(String cpFirmId) {
        this.cpFirmId = cpFirmId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getMatchReference() {
        return matchReference;
    }

    public void setMatchReference(String matchReference) {
        this.matchReference = matchReference;
    }

    public String getSettlementCode() {
        return settlementCode;
    }

    public void setSettlementCode(String settlementCode) {
        this.settlementCode = settlementCode;
    }

    public Double getYield() {
        return yield;
    }

    public void setYield(Double yield) {
        this.yield = yield;
    }

    public Double getAccruedInterest() {
        return accruedInterest;
    }

    public void setAccruedInterest(Double accruedInterest) {
        this.accruedInterest = accruedInterest;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getRedemptionPrice() {
        return redemptionPrice;
    }

    public void setRedemptionPrice(Double redemptionPrice) {
        this.redemptionPrice = redemptionPrice;
    }

    public Double getRepoRate() {
        return repoRate;
    }

    public void setRepoRate(Double repoRate) {
        this.repoRate = repoRate;
    }

    public Double getRefundRate() {
        return refundRate;
    }

    public void setRefundRate(Double refundRate) {
        this.refundRate = refundRate;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Integer getRepoEntry() {
        return repoEntry;
    }

    public void setRepoEntry(Integer repoEntry) {
        this.repoEntry = repoEntry;
    }

    public Double getRepoValue() {
        return repoValue;
    }

    public void setRepoValue(Double repoValue) {
        this.repoValue = repoValue;
    }

    public Double getRepo2value() {
        return repo2value;
    }

    public void setRepo2value(Double repo2value) {
        this.repo2value = repo2value;
    }

    public Long getRepoTerm() {
        return repoTerm;
    }

    public void setRepoTerm(Long repoTerm) {
        this.repoTerm = repoTerm;
    }

    public Double getStartDiscount() {
        return startDiscount;
    }

    public void setStartDiscount(Double startDiscount) {
        this.startDiscount = startDiscount;
    }

    public Double getLowerDiscount() {
        return lowerDiscount;
    }

    public void setLowerDiscount(Double lowerDiscount) {
        this.lowerDiscount = lowerDiscount;
    }

    public Double getUpperDiscount() {
        return upperDiscount;
    }

    public void setUpperDiscount(Double upperDiscount) {
        this.upperDiscount = upperDiscount;
    }

    public Integer getBlockSecurities() {
        return blockSecurities;
    }

    public void setBlockSecurities(Integer blockSecurities) {
        this.blockSecurities = blockSecurities;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(Long withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public Long getNegDealDate() {
        return negDealDate;
    }

    public void setNegDealDate(Long negDealDate) {
        this.negDealDate = negDealDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getOriginRepoValue() {
        return originRepoValue;
    }

    public void setOriginRepoValue(Double originRepoValue) {
        this.originRepoValue = originRepoValue;
    }

    public Double getOriginQty() {
        return originQty;
    }

    public void setOriginQty(Double originQty) {
        this.originQty = originQty;
    }

    public Double getOriginDiscount() {
        return originDiscount;
    }

    public void setOriginDiscount(Double originDiscount) {
        this.originDiscount = originDiscount;
    }

    public Long getNegDealActivationDate() {
        return negDealActivationDate;
    }

    public void setNegDealActivationDate(Long negDealActivationDate) {
        this.negDealActivationDate = negDealActivationDate;
    }

    public Long getNegDealActivationTime() {
        return negDealActivationTime;
    }

    public void setNegDealActivationTime(Long negDealActivationTime) {
        this.negDealActivationTime = negDealActivationTime;
    }

    public Long getQuoteno() {
        return quoteno;
    }

    public void setQuoteno(Long quoteno) {
        this.quoteno = quoteno;
    }

    public Long getSettlementCurrency() {
        return settlementCurrency;
    }

    public void setSettlementCurrency(Long settlementCurrency) {
        this.settlementCurrency = settlementCurrency;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(Long withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public Long getLinkedOrder() {
        return linkedOrder;
    }

    public void setLinkedOrder(Long linkedOrder) {
        this.linkedOrder = linkedOrder;
    }

    public DateTime getActivationDateTime() {
        return activationDateTime;
    }

    public void setActivationDateTime(DateTime activationDateTime) {
        this.activationDateTime = activationDateTime;
    }

    public DateTime getWithdrawDateTime() {
        return withdrawDateTime;
    }

    public void setWithdrawDateTime(DateTime withdrawDateTime) {
        this.withdrawDateTime = withdrawDateTime;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
