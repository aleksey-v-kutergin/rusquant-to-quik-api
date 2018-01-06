package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.QuikDataObject;

/**
 * Java implementation for money_limits table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoneyLimit extends QuikDataObject {
    /**
     * Currency code
     **/
    @JsonProperty("currcode")
    private String currencyCode;

    /**
     * Payment Tag
     **/
    @JsonProperty("tag")
    private String tag;

    /**
     * Company ID
     **/
    @JsonProperty("firmid")
    private String firmId;

    /**
     * Client code
     **/
    @JsonProperty("client_code")
    private String clientCode;

    /**
     * Incoming balance on money
     **/
    @JsonProperty("openbal")
    private Double openBalance;

    /**
     * Incoming limit on money
     **/
    @JsonProperty("openlimit")
    private Double openLimit;

    /**
     * Current balance on money
     **/
    @JsonProperty("currentbal")
    private Double currentBalance;

    /**
     * Current limit on money
     **/
    @JsonProperty("currentlimit")
    private Double currentLimit;

    /**
     * Blocked quantity
     **/
    @JsonProperty("locked")
    private Double lockedValue;

    /**
     * The value of assets in applications for purchase of non-marginal securities
     **/
    @JsonProperty("locked_value_coef")
    private Long lockedValueInBuyOrders;

    /**
     * The value of assets in applications for the purchase of margin securities
     **/
    @JsonProperty("locked_margin_value")
    private Long lockedValueInMarginOrders;

    /**
     * Leverage
     **/
    @JsonProperty("leverage")
    private Double leverage;

    /**
     * The limit type. The possible values are:
     *    "0" - the usual limits
     *     otherwise - technological limits
     **/
    @JsonProperty("limit_kind")
    private Integer limitKind;

    public MoneyLimit() {

    }

    @Override
    public String toString() {
        return "MoneyLimit: {" +
                "currencyCode='" + currencyCode + '\'' +
                ", tag='" + tag + '\'' +
                ", firmId='" + firmId + '\'' +
                ", clientCode='" + clientCode + '\'' +
                ", openBalance=" + openBalance +
                ", openLimit=" + openLimit +
                ", currentBalance=" + currentBalance +
                ", currentLimit=" + currentLimit +
                ", lockedValue=" + lockedValue +
                ", lockedValueInBuyOrders=" + lockedValueInBuyOrders +
                ", lockedValueInMarginOrders=" + lockedValueInMarginOrders +
                ", leverage=" + leverage +
                ", limitKind=" + limitKind +
                '}';
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFirmId() {
        return firmId;
    }

    public void setFirmId(String firmId) {
        this.firmId = firmId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Double getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(Double openBalance) {
        this.openBalance = openBalance;
    }

    public Double getOpenLimit() {
        return openLimit;
    }

    public void setOpenLimit(Double openLimit) {
        this.openLimit = openLimit;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getCurrentLimit() {
        return currentLimit;
    }

    public void setCurrentLimit(Double currentLimit) {
        this.currentLimit = currentLimit;
    }

    public Double getLockedValue() {
        return lockedValue;
    }

    public void setLockedValue(Double lockedValue) {
        this.lockedValue = lockedValue;
    }

    public Long getLockedValueInBuyOrders() {
        return lockedValueInBuyOrders;
    }

    public void setLockedValueInBuyOrders(Long lockedValueInBuyOrders) {
        this.lockedValueInBuyOrders = lockedValueInBuyOrders;
    }

    public Long getLockedValueInMarginOrders() {
        return lockedValueInMarginOrders;
    }

    public void setLockedValueInMarginOrders(Long lockedValueInMarginOrders) {
        this.lockedValueInMarginOrders = lockedValueInMarginOrders;
    }

    public Double getLeverage() {
        return leverage;
    }

    public void setLeverage(Double leverage) {
        this.leverage = leverage;
    }

    public Integer getLimitKind() {
        return limitKind;
    }

    public void setLimitKind(Integer limitKind) {
        this.limitKind = limitKind;
    }
}
