package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.QuikDataObject;

/**
 * Java implementation for account_balance table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalance extends QuikDataObject {
    /**
     * Firm identifier
     **/
    @JsonProperty("firmid")
    private String firmId;

    /**
     * Code of security
     **/
    @JsonProperty("sec_code")
    private String securityCode;

    /**
     * Securities account
     **/
    @JsonProperty("trdaccid")
    private String tradingAccountId;

    /**
     * Depo account id
     **/
    @JsonProperty("depaccid")
    private String depoAccountId;

    /**
     * Incoming balance
     **/
    @JsonProperty("openbal")
    private Double openBalance;

    /**
     * Current balance
     **/
    @JsonProperty("currentpos")
    private Double currentPosition;

    /**
     * Scheduled sale
     **/
    @JsonProperty("plannedpossell")
    private Double plannedPositionSell;

    /**
     * Planned purchase
     **/
    @JsonProperty("plannedposbuy")
    private Double plannedPositionBuy;

    /**
     * The control balance of simple clearing is equal to the incoming balance minus the planned position
     * for sale included in the simple clearing
     **/
    @JsonProperty("planbal")
    private Double checkBalance;

    /**
     * Purchased
     **/
    @JsonProperty("usqtyb")
    private Double usqtyb;

    /**
     * Sales
     **/
    @JsonProperty("usqtys")
    private Double usqtys;

    /**
     * The planned balance is equal to the current balance minus the planned position for sale
     **/
    @JsonProperty("planned")
    private Double plannedBalance;

    /**
     * Planned position after settlement
     **/
    @JsonProperty("settlebal")
    private Double settleBalance;

    /**
     * Identifier of the settlement account / code in the clearing organization
     **/
    @JsonProperty("bank_acc_id")
    private String bankAccountId;

    /**
     * Sign of the collateral account. The possible values are:
     *     "0" - for regular accounts
     *     "1" - for the security account
     **/
    @JsonProperty("firmuse")
    private Integer firmUse;

    public AccountBalance() {

    }

    @Override
    public String toString() {
        return "AccountBalance: {" +
                "firmId='" + firmId + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", tradingAccountId='" + tradingAccountId + '\'' +
                ", depoAccountId='" + depoAccountId + '\'' +
                ", openBalance=" + openBalance +
                ", currentPosition=" + currentPosition +
                ", plannedPositionSell=" + plannedPositionSell +
                ", plannedPositionBuy=" + plannedPositionBuy +
                ", checkBalance=" + checkBalance +
                ", usqtyb=" + usqtyb +
                ", usqtys=" + usqtys +
                ", plannedBalance=" + plannedBalance +
                ", settleBalance=" + settleBalance +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", firmUse=" + firmUse +
                '}';
    }

    public String getFirmId() {
        return firmId;
    }

    public void setFirmId(String firmId) {
        this.firmId = firmId;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getTradingAccountId() {
        return tradingAccountId;
    }

    public void setTradingAccountId(String tradingAccountId) {
        this.tradingAccountId = tradingAccountId;
    }

    public String getDepoAccountId() {
        return depoAccountId;
    }

    public void setDepoAccountId(String depoAccountId) {
        this.depoAccountId = depoAccountId;
    }

    public Double getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(Double openBalance) {
        this.openBalance = openBalance;
    }

    public Double getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Double currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Double getPlannedPositionSell() {
        return plannedPositionSell;
    }

    public void setPlannedPositionSell(Double plannedPositionSell) {
        this.plannedPositionSell = plannedPositionSell;
    }

    public Double getPlannedPositionBuy() {
        return plannedPositionBuy;
    }

    public void setPlannedPositionBuy(Double plannedPositionBuy) {
        this.plannedPositionBuy = plannedPositionBuy;
    }

    public Double getCheckBalance() {
        return checkBalance;
    }

    public void setCheckBalance(Double checkBalance) {
        this.checkBalance = checkBalance;
    }

    public Double getUsqtyb() {
        return usqtyb;
    }

    public void setUsqtyb(Double usqtyb) {
        this.usqtyb = usqtyb;
    }

    public Double getUsqtys() {
        return usqtys;
    }

    public void setUsqtys(Double usqtys) {
        this.usqtys = usqtys;
    }

    public Double getPlannedBalance() {
        return plannedBalance;
    }

    public void setPlannedBalance(Double plannedBalance) {
        this.plannedBalance = plannedBalance;
    }

    public Double getSettleBalance() {
        return settleBalance;
    }

    public void setSettleBalance(Double settleBalance) {
        this.settleBalance = settleBalance;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Integer getFirmUse() {
        return firmUse;
    }

    public void setFirmUse(Integer firmUse) {
        this.firmUse = firmUse;
    }
}
