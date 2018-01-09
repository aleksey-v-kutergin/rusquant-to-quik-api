package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.QuikDataObject;

/**
 * Java implementation for futures_client_limits table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class FuturesClientLimit extends QuikDataObject {

    /**
     * Firm identifier
     **/
    @JsonProperty("firmid")
    private String firmId;

    /**
     * Trading account id
     **/
    @JsonProperty("trdaccid")
    private String tradingAccountId;

    /**
     * The limit type. The possible values are:
     *        "0" - "Cash"
     *        "1" - "Security money"
     *        "2" - "Total"
     *        "3" - "Clearing rubles"
     *        "4" - "Clearing collateral rubles"
     *        "5" - "Limit of open positions in the spot market"
     **/
    @JsonProperty("limit_type")
    private Integer limitType;

    /**
     * Coefficient of liquidity
     **/
    @JsonProperty("liquidity_coef")
    private Double liquidityCoeficient;

    /**
     * Previous limit of open positions in the spot market
     **/
    @JsonProperty("cbp_prev_limit")
    private Double cbpPreviousLimit;

    /**
     * Limit of open positions
     **/
    @JsonProperty("cbplimit")
    private Double cbpLimit;

    /**
     * Current net positions
     **/
    @JsonProperty("cbplused")
    private Double cbplUsed;

    /**
     * Planned net positions
     **/
    @JsonProperty("cbplplanned")
    private Double cbplPlanned;

    /**
     * Variation Margin
     **/
    @JsonProperty("varmargin")
    private Double varMargin;

    /**
     * Accrued coupon yield
     **/
    @JsonProperty("accruedint")
    private Double accruedInterest;

    /**
     * Current net positions (under orders)
     **/
    @JsonProperty("cbplused_for_orders")
    private Double cbplUsedForOrders;

    /**
     * Current net positions (for open positions)
     **/
    @JsonProperty("cbplused_for_positions")
    private Double cbplUsedForPositions;

    /**
     * Premium on options
     **/
    @JsonProperty("options_premium")
    private Double optionsPremium;

    /**
     * Exchange charges
     **/
    @JsonProperty("ts_comission")
    private Double tsComission;

    /**
     * Customer guarantee ratio
     **/
    @JsonProperty("kgo")
    private Double kgo;

    /**
     * Currency in which the restriction is broadcast
     **/
    @JsonProperty("currcode")
    private String currCode;

    /**
     * The variation margin that was actually calculated during the course of clearing.
     * It is displayed with an accuracy of 2 characters. In this case, in the field "varmargin" a variation margin is translated, calculated taking into account the established boundaries of the price change
     **/
    @JsonProperty("real_varmargin")
    private Double realVarMargin;

    public FuturesClientLimit() {

    }

    @Override
    public String toString() {
        return "FuturesClientLimit: {" +
                "firmId='" + firmId + '\'' +
                ", tradingAccountId='" + tradingAccountId + '\'' +
                ", limitType=" + limitType +
                ", liquidityCoeficient=" + liquidityCoeficient +
                ", cbpPreviousLimit=" + cbpPreviousLimit +
                ", cbpLimit=" + cbpLimit +
                ", cbplUsed=" + cbplUsed +
                ", cbplPlanned=" + cbplPlanned +
                ", varMargin=" + varMargin +
                ", accruedInterest=" + accruedInterest +
                ", cbplUsedForOrders=" + cbplUsedForOrders +
                ", cbplUsedForPositions=" + cbplUsedForPositions +
                ", optionsPremium=" + optionsPremium +
                ", tsComission=" + tsComission +
                ", kgo=" + kgo +
                ", currCode='" + currCode + '\'' +
                ", realVarMargin=" + realVarMargin +
                '}';
    }

    public String getFirmId() {
        return firmId;
    }

    public void setFirmId(String firmId) {
        this.firmId = firmId;
    }

    public String getTradingAccountId() {
        return tradingAccountId;
    }

    public void setTradingAccountId(String tradingAccountId) {
        this.tradingAccountId = tradingAccountId;
    }

    public Integer getLimitType() {
        return limitType;
    }

    public void setLimitType(Integer limitType) {
        this.limitType = limitType;
    }

    public Double getLiquidityCoeficient() {
        return liquidityCoeficient;
    }

    public void setLiquidityCoeficient(Double liquidityCoeficient) {
        this.liquidityCoeficient = liquidityCoeficient;
    }

    public Double getCbpPreviousLimit() {
        return cbpPreviousLimit;
    }

    public void setCbpPreviousLimit(Double cbpPreviousLimit) {
        this.cbpPreviousLimit = cbpPreviousLimit;
    }

    public Double getCbpLimit() {
        return cbpLimit;
    }

    public void setCbpLimit(Double cbpLimit) {
        this.cbpLimit = cbpLimit;
    }

    public Double getCbplUsed() {
        return cbplUsed;
    }

    public void setCbplUsed(Double cbplUsed) {
        this.cbplUsed = cbplUsed;
    }

    public Double getCbplPlanned() {
        return cbplPlanned;
    }

    public void setCbplPlanned(Double cbplPlanned) {
        this.cbplPlanned = cbplPlanned;
    }

    public Double getVarMargin() {
        return varMargin;
    }

    public void setVarMargin(Double varMargin) {
        this.varMargin = varMargin;
    }

    public Double getAccruedInterest() {
        return accruedInterest;
    }

    public void setAccruedInterest(Double accruedInterest) {
        this.accruedInterest = accruedInterest;
    }

    public Double getCbplUsedForOrders() {
        return cbplUsedForOrders;
    }

    public void setCbplUsedForOrders(Double cbplUsedForOrders) {
        this.cbplUsedForOrders = cbplUsedForOrders;
    }

    public Double getCbplUsedForPositions() {
        return cbplUsedForPositions;
    }

    public void setCbplUsedForPositions(Double cbplUsedForPositions) {
        this.cbplUsedForPositions = cbplUsedForPositions;
    }

    public Double getOptionsPremium() {
        return optionsPremium;
    }

    public void setOptionsPremium(Double optionsPremium) {
        this.optionsPremium = optionsPremium;
    }

    public Double getTsComission() {
        return tsComission;
    }

    public void setTsComission(Double tsComission) {
        this.tsComission = tsComission;
    }

    public Double getKgo() {
        return kgo;
    }

    public void setKgo(Double kgo) {
        this.kgo = kgo;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public Double getRealVarMargin() {
        return realVarMargin;
    }

    public void setRealVarMargin(Double realVarMargin) {
        this.realVarMargin = realVarMargin;
    }
}
