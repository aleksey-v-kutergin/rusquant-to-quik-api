package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.QuikDataObject;

/**
 * Java implementation for trade_accounts table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradingAccount extends QuikDataObject {
    /**
     * Firm identifier
     **/
    @JsonProperty("firmid")
    private String firmId;

    /**
     * List of class codes separated by "|"
     **/
    @JsonProperty("class_codes")
    private String classCodes;

    /**
     * Identifier for trading account
     **/
    @JsonProperty("trdaccid")
    private String tradingAccountId;

    /**
     * Trading account type
     **/
    @JsonProperty("trdacc_type")
    private Integer tradingAccountType;

    /**
     * Status of the trading account. The possible values are:
     * "0" - operations are allowed
     * "1" - operations are prohibited
     **/
    @JsonProperty("status")
    private Integer status;

    /**
     * Section type. The possible values are:
     * "0" is the collateral section
     * otherwise - for trade sections
     **/
    @JsonProperty("firmuse")
    private Integer firmUse;

    /**
     * Custody account number in the depository
     **/
    @JsonProperty("depaccid")
    private String depaccId;

    /**
     * Additional position code for funds
     **/
    @JsonProperty("bank_acc_id")
    private String bankAccountId;

    /**
     * Description
     **/
    @JsonProperty("description")
    private String description;

    /**
     * Ban on unsecured sales. The possible values are:
     * "0" - No
     * "1" - Yes
     **/
    @JsonProperty("fullcoveredsell")
    private Integer fullCoveredSell;

    /**
     * Main trading account number
     **/
    @JsonProperty("main_trdaccid")
    private String mainTradingAccountId;

    /**
     * Settlement organization for "T0"
     **/
    @JsonProperty("bankid_t0")
    private String bankIdT0;

    /**
     * Settlement organization for "T+"
     **/
    @JsonProperty("bankid_tplus")
    private String bankIdTplus;

    /**
     * Securities account section
     **/
    @JsonProperty("depunitid")
    private String depUnitId;

    public TradingAccount() {

    }

    @Override
    public String toString() {
        return "TradingAccount: {" +
                "firmId='" + firmId + '\'' +
                ", classCodes='" + classCodes + '\'' +
                ", tradingAccountId='" + tradingAccountId + '\'' +
                ", tradingAccountType=" + tradingAccountType +
                ", status=" + status +
                ", firmUse=" + firmUse +
                ", depaccId='" + depaccId + '\'' +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", description='" + description + '\'' +
                ", fullCoveredSell=" + fullCoveredSell +
                ", mainTradingAccountId='" + mainTradingAccountId + '\'' +
                ", bankIdT0='" + bankIdT0 + '\'' +
                ", bankIdTplus='" + bankIdTplus + '\'' +
                ", depUnitId='" + depUnitId + '\'' +
                '}';
    }

    public String getFirmId() {
        return firmId;
    }

    public void setFirmId(String firmId) {
        this.firmId = firmId;
    }

    public String getClassCodes() {
        return classCodes;
    }

    public void setClassCodes(String classCodes) {
        this.classCodes = classCodes;
    }

    public String getTradingAccountId() {
        return tradingAccountId;
    }

    public void setTradingAccountId(String tradingAccountId) {
        this.tradingAccountId = tradingAccountId;
    }

    public Integer getTradingAccountType() {
        return tradingAccountType;
    }

    public void setTradingAccountType(Integer tradingAccountType) {
        this.tradingAccountType = tradingAccountType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFirmUse() {
        return firmUse;
    }

    public void setFirmUse(Integer firmUse) {
        this.firmUse = firmUse;
    }

    public String getDepaccId() {
        return depaccId;
    }

    public void setDepaccId(String depaccId) {
        this.depaccId = depaccId;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFullCoveredSell() {
        return fullCoveredSell;
    }

    public void setFullCoveredSell(Integer fullCoveredSell) {
        this.fullCoveredSell = fullCoveredSell;
    }

    public String getMainTradingAccountId() {
        return mainTradingAccountId;
    }

    public void setMainTradingAccountId(String mainTradingAccountId) {
        this.mainTradingAccountId = mainTradingAccountId;
    }

    public String getBankIdT0() {
        return bankIdT0;
    }

    public void setBankIdT0(String bankIdT0) {
        this.bankIdT0 = bankIdT0;
    }

    public String getBankIdTplus() {
        return bankIdTplus;
    }

    public void setBankIdTplus(String bankIdTplus) {
        this.bankIdTplus = bankIdTplus;
    }

    public String getDepUnitId() {
        return depUnitId;
    }

    public void setDepUnitId(String depUnitId) {
        this.depUnitId = depUnitId;
    }
}
