package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.QuikDataObject;

/**
 * Java implementation for result of getMoney() function
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class Money extends QuikDataObject {
    /**
     * Incoming cash limit
     **/
    @JsonProperty("money_open_limit")
    private Double incomingCashLimit;

    /**
     * The cost of non-marital securities in purchase orders
     **/
    @JsonProperty("money_limit_locked_nonmarginal_value")
    private Double costOfNonmarginalSecurities;

    /**
     * Blocked in the purchase orders amount of money
     **/
    @JsonProperty("money_limit_locked")
    private Double blockedAmountOfMoney;

    /**
     * Incoming cash balance
     **/
    @JsonProperty("money_open_balance")
    private Double incomingCashBalance;

    /**
     * Current cash limit
     **/
    @JsonProperty("money_current_limit")
    private Double currentCashLimit;

    /**
     * Current balance on funds
     **/
    @JsonProperty("money_current_balance")
    private Double currentBalanceOnFunds;

    /**
     * Available amount of money
     **/
    @JsonProperty("money_limit_available")
    private Double availableAmountOfMoney;

    public Money() {

    }

    @Override
    public String toString() {
        return "Money: {" +
                "incomingCashLimit=" + incomingCashLimit +
                ", costOfNonmarginalSecurities=" + costOfNonmarginalSecurities +
                ", blockedAmountOfMoney=" + blockedAmountOfMoney +
                ", incomingCashBalance=" + incomingCashBalance +
                ", currentCashLimit=" + currentCashLimit +
                ", currentBalanceOnFunds=" + currentBalanceOnFunds +
                ", availableAmountOfMoney=" + availableAmountOfMoney +
                '}';
    }

    public Double getIncomingCashLimit() {
        return incomingCashLimit;
    }

    public void setIncomingCashLimit(Double incomingCashLimit) {
        this.incomingCashLimit = incomingCashLimit;
    }

    public Double getCostOfNonmarginalSecurities() {
        return costOfNonmarginalSecurities;
    }

    public void setCostOfNonmarginalSecurities(Double costOfNonmarginalSecurities) {
        this.costOfNonmarginalSecurities = costOfNonmarginalSecurities;
    }

    public Double getBlockedAmountOfMoney() {
        return blockedAmountOfMoney;
    }

    public void setBlockedAmountOfMoney(Double blockedAmountOfMoney) {
        this.blockedAmountOfMoney = blockedAmountOfMoney;
    }

    public Double getIncomingCashBalance() {
        return incomingCashBalance;
    }

    public void setIncomingCashBalance(Double incomingCashBalance) {
        this.incomingCashBalance = incomingCashBalance;
    }

    public Double getCurrentCashLimit() {
        return currentCashLimit;
    }

    public void setCurrentCashLimit(Double currentCashLimit) {
        this.currentCashLimit = currentCashLimit;
    }

    public Double getCurrentBalanceOnFunds() {
        return currentBalanceOnFunds;
    }

    public void setCurrentBalanceOnFunds(Double currentBalanceOnFunds) {
        this.currentBalanceOnFunds = currentBalanceOnFunds;
    }

    public Double getAvailableAmountOfMoney() {
        return availableAmountOfMoney;
    }

    public void setAvailableAmountOfMoney(Double availableAmountOfMoney) {
        this.availableAmountOfMoney = availableAmountOfMoney;
    }
}
