package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.QuikDataObject;

/**
 * Java implementation for neg_trades table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NegTrade extends QuikDataObject {
    /**
     * Trade number
     **/
    @JsonProperty("trade_num")
    private Long tradeNumber;

    /**
     * Trade date
     **/
    @JsonProperty("trade_date")
    private Long tradeDate;

    /**
     * Settlement date
     **/
    @JsonProperty("settle_date")
    private Long settlementDate;

    /**
     * A set of bit flags:
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
     * Dealer ID
     **/
    @JsonProperty("firmid")
    private String firmId;

    /**
     * Depo account
     **/
    @JsonProperty("account")
    private String account;

    /**
     * Partner-firm identifier
     **/
    @JsonProperty("cpfirmid")
    private String cpFirmId;

    /**
     * Partner-firm account
     **/
    @JsonProperty("cpaccount")
    private String cpAccount;

    /**
     * Price
     **/
    @JsonProperty("price")
    private Double price;

    /**
     * Quantity
     **/
    @JsonProperty("qty")
    private Double qty;

    /**
     * Volume
     **/
    @JsonProperty("value")
    private Double value;

    /**
     * Settlement code
     **/
    @JsonProperty("settlecode")
    private String settlementCode;

    /**
     * Report
     **/
    @JsonProperty("report_num")
    private Long reportNumber;

    /**
     * Partner report
     **/
    @JsonProperty("cpreport_num")
    private Long cpReportNumber;

    /**
     * Coupon interest
     **/
    @JsonProperty("accruedint")
    private Double accruedInterest;

    /**
     * Trade number of the 1st part of the REPO
     **/
    @JsonProperty("repotradeno")
    private Long repoTradeNumber;

    /**
     * The price of the 1st part of REPO
     **/
    @JsonProperty("price1")
    private Double price1;

    /**
     * REPO rate (%)
     **/
    @JsonProperty("reporate")
    private Double repoRate;

    /**
     * Redemption price
     **/
    @JsonProperty("price2")
    private Double redemptionPrice;

    /**
     * Client code
     **/
    @JsonProperty("client_code")
    private String clientCode;

    /**
     * Commission of the trading system
     **/
    @JsonProperty("ts_comission")
    private Double tsComission;

    /**
     * Balance
     **/
    @JsonProperty("balance")
    private Double balance;

    /**
     * Time of completion
     **/
    @JsonProperty("settle_time")
    private Long settlementTime;

    /**
     * Commitment amount
     **/
    @JsonProperty("amount")
    private Double amount;

    /**
     * REPO amount
     **/
    @JsonProperty("repovalue")
    private Double repoValue;

    /**
     * REPO term
     **/
    @JsonProperty("repoterm")
    private Long repoTerm;

    /**
     * REPO volume of redemption
     **/
    @JsonProperty("repo2value")
    private Double repo2value;

    /**
     * Repurchase amount of REPO
     **/
    @JsonProperty("return_value")
    private Double returnValue;

    /**
     * Discount (%)
     **/
    @JsonProperty("discount")
    private Double discount;

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
     * Block collateral ("Yes" / "No")
     **/
    @JsonProperty("block_securities")
    private Integer blockSecurities;

    /**
     * Execute ("Yes" / "No")
     **/
    @JsonProperty("urgency_flag")
    private Integer urgencyFlag;

    /**
     * Type. The possible values are:
     *        "0" - "Off-system transaction"
     *        "1" - "The first part of the REPO transaction"
     *        "2" - "The second part of the REPO transaction"
     *        "3" - "Compensation fee"
     *        "4" - "Default: deferred obligations and claims"
     *        "5" - "The victim: deferred obligations and claims"
     **/
    @JsonProperty("type")
    private Integer type;

    /**
     * Direction. The possible values are:
     *        "1" - "Enroll"
     *        "2" - "Write off"
     **/
    @JsonProperty("operation_type")
    private Integer operationType;

    /**
     * Discount after the contribution (%)
     **/
    @JsonProperty("expected_discount")
    private Double expectedDiscount;

    /**
     * Amount after the deposit
     **/
    @JsonProperty("expected_quantity")
    private Double expectedQuantity;

    /**
     * The amount of REPO after the deposit
     **/
    @JsonProperty("expected_repovalue")
    private Double expectedRepoValue;

    /**
     * The cost of repurchase after the contribution
     **/
    @JsonProperty("expected_repo2value")
    private Double expectedRepo2value;

    /**
     * Refund amount after deposit
     **/
    @JsonProperty("expected_return_value")
    private Double expectedReturnValue;

    /**
     * Order number
     **/
    @JsonProperty("order_num")
    private Long orderNumber;

    /**
     * Date of conclusion
     **/
    @JsonProperty("report_trade_date")
    private Long reportTradeDate;

    /**
     * Trade settlement status. The possible values are:
     *        "1" - "Processed"
     *        "2" - "Not processed"
     *        "3" - "Is processing"
     **/
    @JsonProperty("settled")
    private Integer settled;

    /**
     * Clearing type. The possible values are:
     *        "1" - "Not set"
     *        "2" - "Simple"
     *        "3" - "Multilateral"
     **/
    @JsonProperty("clearing_type")
    private Integer clearingType;

    /**
     * Commission for report
     **/
    @JsonProperty("report_comission")
    private Double reportComission;

    /**
     * Coupon payment
     **/
    @JsonProperty("coupon_payment")
    private Double couponPayment;

    /**
     * Principal debt repayment
     **/
    @JsonProperty("principal_payment")
    private Double principalPayment;

    /**
     * Date of payment of principal debt
     **/
    @JsonProperty("principal_payment_date")
    private Double principalPaymentDate;

    /**
     * Date of the next settlement day
     **/
    @JsonProperty("nextdaysettle")
    private Long nextSettlementDay;

    /**
     * Settlement currency
     **/
    @JsonProperty("settle_currency")
    private String settlementCurrency;

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
     * The amount of compensation in the transaction currency
     **/
    @JsonProperty("compval")
    private Double compValue;

    /**
     * Identification number of the shop window trade
     **/
    @JsonProperty("parenttradeno")
    private Long parentTradeNumber;

    /**
     * Settlement organization
     **/
    @JsonProperty("bankid")
    private String bankId;

    /**
     * Position code???
     **/
    @JsonProperty("bankaccid")
    private String bankAccountId;

    /**
     * Number of securities to be executed (in lots)
     **/
    @JsonProperty("precisebalance")
    private Long preciseBalance;

    /**
     * The time of confirmation in the format "hhmmss"
     **/
    @JsonProperty("confirmtime")
    private Long confirmTime;

    /**
     * Extended transaction flags for execution. The possible values are:
     * "1" - "Confirmed by the counterparty"
     * "2" - «Confirmed»
     **/
    @JsonProperty("ex_flags")
    private Integer exFlags;

    /**
     * Order number???
     **/
    @JsonProperty("confirmreport")
    private Long confirmReport;

    public NegTrade() {

    }

    @Override
    public String toString() {
        return "NegTrade: {" +
                "tradeNumber=" + tradeNumber +
                ", tradeDate=" + tradeDate +
                ", settlementDate=" + settlementDate +
                ", flags=" + flags +
                ", brokerReference='" + brokerReference + '\'' +
                ", firmId='" + firmId + '\'' +
                ", account='" + account + '\'' +
                ", cpFirmId='" + cpFirmId + '\'' +
                ", cpAccount='" + cpAccount + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", value=" + value +
                ", settlementCode='" + settlementCode + '\'' +
                ", reportNumber=" + reportNumber +
                ", cpReportNumber=" + cpReportNumber +
                ", accruedInterest=" + accruedInterest +
                ", repoTradeNumber=" + repoTradeNumber +
                ", price1=" + price1 +
                ", repoRate=" + repoRate +
                ", redemptionPrice=" + redemptionPrice +
                ", clientCode='" + clientCode + '\'' +
                ", tsComission=" + tsComission +
                ", balance=" + balance +
                ", settlementTime=" + settlementTime +
                ", amount=" + amount +
                ", repoValue=" + repoValue +
                ", repoTerm=" + repoTerm +
                ", repo2value=" + repo2value +
                ", returnValue=" + returnValue +
                ", discount=" + discount +
                ", lowerDiscount=" + lowerDiscount +
                ", upperDiscount=" + upperDiscount +
                ", blockSecurities=" + blockSecurities +
                ", urgencyFlag=" + urgencyFlag +
                ", type=" + type +
                ", operationType=" + operationType +
                ", expectedDiscount=" + expectedDiscount +
                ", expectedQuantity=" + expectedQuantity +
                ", expectedRepoValue=" + expectedRepoValue +
                ", expectedRepo2value=" + expectedRepo2value +
                ", expectedReturnValue=" + expectedReturnValue +
                ", orderNumber=" + orderNumber +
                ", reportTradeDate=" + reportTradeDate +
                ", settled=" + settled +
                ", clearingType=" + clearingType +
                ", reportComission=" + reportComission +
                ", couponPayment=" + couponPayment +
                ", principalPayment=" + principalPayment +
                ", principalPaymentDate=" + principalPaymentDate +
                ", nextSettlementDay=" + nextSettlementDay +
                ", settlementCurrency='" + settlementCurrency + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", classCode='" + classCode + '\'' +
                ", compValue=" + compValue +
                ", parentTradeNumber=" + parentTradeNumber +
                ", bankId='" + bankId + '\'' +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", preciseBalance=" + preciseBalance +
                ", confirmTime=" + confirmTime +
                ", exFlags=" + exFlags +
                ", confirmReport=" + confirmReport +
                '}';
    }

    public Long getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(Long tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public Long getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Long tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Long getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Long settlementDate) {
        this.settlementDate = settlementDate;
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

    public String getCpFirmId() {
        return cpFirmId;
    }

    public void setCpFirmId(String cpFirmId) {
        this.cpFirmId = cpFirmId;
    }

    public String getCpAccount() {
        return cpAccount;
    }

    public void setCpAccount(String cpAccount) {
        this.cpAccount = cpAccount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getSettlementCode() {
        return settlementCode;
    }

    public void setSettlementCode(String settlementCode) {
        this.settlementCode = settlementCode;
    }

    public Long getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(Long reportNumber) {
        this.reportNumber = reportNumber;
    }

    public Long getCpReportNumber() {
        return cpReportNumber;
    }

    public void setCpReportNumber(Long cpReportNumber) {
        this.cpReportNumber = cpReportNumber;
    }

    public Double getAccruedInterest() {
        return accruedInterest;
    }

    public void setAccruedInterest(Double accruedInterest) {
        this.accruedInterest = accruedInterest;
    }

    public Long getRepoTradeNumber() {
        return repoTradeNumber;
    }

    public void setRepoTradeNumber(Long repoTradeNumber) {
        this.repoTradeNumber = repoTradeNumber;
    }

    public Double getPrice1() {
        return price1;
    }

    public void setPrice1(Double price1) {
        this.price1 = price1;
    }

    public Double getRepoRate() {
        return repoRate;
    }

    public void setRepoRate(Double repoRate) {
        this.repoRate = repoRate;
    }

    public Double getRedemptionPrice() {
        return redemptionPrice;
    }

    public void setRedemptionPrice(Double redemptionPrice) {
        this.redemptionPrice = redemptionPrice;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Double getTsComission() {
        return tsComission;
    }

    public void setTsComission(Double tsComission) {
        this.tsComission = tsComission;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(Long settlementTime) {
        this.settlementTime = settlementTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRepoValue() {
        return repoValue;
    }

    public void setRepoValue(Double repoValue) {
        this.repoValue = repoValue;
    }

    public Long getRepoTerm() {
        return repoTerm;
    }

    public void setRepoTerm(Long repoTerm) {
        this.repoTerm = repoTerm;
    }

    public Double getRepo2value() {
        return repo2value;
    }

    public void setRepo2value(Double repo2value) {
        this.repo2value = repo2value;
    }

    public Double getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Double returnValue) {
        this.returnValue = returnValue;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
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

    public Integer getUrgencyFlag() {
        return urgencyFlag;
    }

    public void setUrgencyFlag(Integer urgencyFlag) {
        this.urgencyFlag = urgencyFlag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Double getExpectedDiscount() {
        return expectedDiscount;
    }

    public void setExpectedDiscount(Double expectedDiscount) {
        this.expectedDiscount = expectedDiscount;
    }

    public Double getExpectedQuantity() {
        return expectedQuantity;
    }

    public void setExpectedQuantity(Double expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }

    public Double getExpectedRepoValue() {
        return expectedRepoValue;
    }

    public void setExpectedRepoValue(Double expectedRepoValue) {
        this.expectedRepoValue = expectedRepoValue;
    }

    public Double getExpectedRepo2value() {
        return expectedRepo2value;
    }

    public void setExpectedRepo2value(Double expectedRepo2value) {
        this.expectedRepo2value = expectedRepo2value;
    }

    public Double getExpectedReturnValue() {
        return expectedReturnValue;
    }

    public void setExpectedReturnValue(Double expectedReturnValue) {
        this.expectedReturnValue = expectedReturnValue;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getReportTradeDate() {
        return reportTradeDate;
    }

    public void setReportTradeDate(Long reportTradeDate) {
        this.reportTradeDate = reportTradeDate;
    }

    public Integer getSettled() {
        return settled;
    }

    public void setSettled(Integer settled) {
        this.settled = settled;
    }

    public Integer getClearingType() {
        return clearingType;
    }

    public void setClearingType(Integer clearingType) {
        this.clearingType = clearingType;
    }

    public Double getReportComission() {
        return reportComission;
    }

    public void setReportComission(Double reportComission) {
        this.reportComission = reportComission;
    }

    public Double getCouponPayment() {
        return couponPayment;
    }

    public void setCouponPayment(Double couponPayment) {
        this.couponPayment = couponPayment;
    }

    public Double getPrincipalPayment() {
        return principalPayment;
    }

    public void setPrincipalPayment(Double principalPayment) {
        this.principalPayment = principalPayment;
    }

    public Double getPrincipalPaymentDate() {
        return principalPaymentDate;
    }

    public void setPrincipalPaymentDate(Double principalPaymentDate) {
        this.principalPaymentDate = principalPaymentDate;
    }

    public Long getNextSettlementDay() {
        return nextSettlementDay;
    }

    public void setNextSettlementDay(Long nextSettlementDay) {
        this.nextSettlementDay = nextSettlementDay;
    }

    public String getSettlementCurrency() {
        return settlementCurrency;
    }

    public void setSettlementCurrency(String settlementCurrency) {
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

    public Double getCompValue() {
        return compValue;
    }

    public void setCompValue(Double compValue) {
        this.compValue = compValue;
    }

    public Long getParentTradeNumber() {
        return parentTradeNumber;
    }

    public void setParentTradeNumber(Long parentTradeNumber) {
        this.parentTradeNumber = parentTradeNumber;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getPreciseBalance() {
        return preciseBalance;
    }

    public void setPreciseBalance(Long preciseBalance) {
        this.preciseBalance = preciseBalance;
    }

    public Long getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Long confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getExFlags() {
        return exFlags;
    }

    public void setExFlags(Integer exFlags) {
        this.exFlags = exFlags;
    }

    public Long getConfirmReport() {
        return confirmReport;
    }

    public void setConfirmReport(Long confirmReport) {
        this.confirmReport = confirmReport;
    }
}
