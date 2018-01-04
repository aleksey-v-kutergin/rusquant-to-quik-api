package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.QuikDataObject;

/**
 * Java implementation for result of getDepo() function
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class Depo extends QuikDataObject
{
    /** The value of securities blocked for purchase **/
    @JsonProperty("depo_limit_locked_buy_value")
    private Double valueOfLotsForPurchase;

    /** Current balance on securities **/
    @JsonProperty("depo_current_balance")
    private Double depoCurrentBalance;

    /** Number of lots of securities blocked for purchase **/
    @JsonProperty("depo_limit_locked_buy")
    private Double blockedCountOfLotsForPurchase;

    /** Blocked Number of lots of securities **/
    @JsonProperty("depo_limit_locked")
    private Double blockedCountOfLots;

    /** Available quantity of securities **/
    @JsonProperty("depo_limit_available")
    private Double availableCountOfSecurities;

    /** Current limit on securities **/
    @JsonProperty("depo_current_limit")
    private Double depoCurrentLimit;

    /** Incoming balance on securities **/
    @JsonProperty("depo_open_balance")
    private Double depoOpenBalance;

    /** Incoming limit on securities **/
    @JsonProperty("depo_open_limit")
    private Double depoOpenLimit;

    public Depo()
    {

    }

    @Override
    public String toString()
    {
        return "Depo: {" +
                "valueOfLotsForPurchase=" + valueOfLotsForPurchase +
                ", depoCurrentBalance=" + depoCurrentBalance +
                ", blockedCountOfLotsForPurchase=" + blockedCountOfLotsForPurchase +
                ", blockedCountOfLots=" + blockedCountOfLots +
                ", availableCountOfSecurities=" + availableCountOfSecurities +
                ", depoCurrentLimit=" + depoCurrentLimit +
                ", depoOpenBalance=" + depoOpenBalance +
                ", depoOpenLimit=" + depoOpenLimit +
                '}';
    }

    public Double getValueOfLotsForPurchase()
    {
        return valueOfLotsForPurchase;
    }

    public void setValueOfLotsForPurchase(Double valueOfLotsForPurchase)
    {
        this.valueOfLotsForPurchase = valueOfLotsForPurchase;
    }

    public Double getDepoCurrentBalance()
    {
        return depoCurrentBalance;
    }

    public void setDepoCurrentBalance(Double depoCurrentBalance)
    {
        this.depoCurrentBalance = depoCurrentBalance;
    }

    public Double getBlockedCountOfLotsForPurchase()
    {
        return blockedCountOfLotsForPurchase;
    }

    public void setBlockedCountOfLotsForPurchase(Double blockedCountOfLotsForPurchase)
    {
        this.blockedCountOfLotsForPurchase = blockedCountOfLotsForPurchase;
    }

    public Double getBlockedCountOfLots()
    {
        return blockedCountOfLots;
    }

    public void setBlockedCountOfLots(Double blockedCountOfLots)
    {
        this.blockedCountOfLots = blockedCountOfLots;
    }

    public Double getAvailableCountOfSecurities()
    {
        return availableCountOfSecurities;
    }

    public void setAvailableCountOfSecurities(Double availableCountOfSecurities)
    {
        this.availableCountOfSecurities = availableCountOfSecurities;
    }

    public Double getDepoCurrentLimit()
    {
        return depoCurrentLimit;
    }

    public void setDepoCurrentLimit(Double depoCurrentLimit)
    {
        this.depoCurrentLimit = depoCurrentLimit;
    }

    public Double getDepoOpenBalance()
    {
        return depoOpenBalance;
    }

    public void setDepoOpenBalance(Double depoOpenBalance)
    {
        this.depoOpenBalance = depoOpenBalance;
    }

    public Double getDepoOpenLimit()
    {
        return depoOpenLimit;
    }

    public void setDepoOpenLimit(Double depoOpenLimit)
    {
        this.depoOpenLimit = depoOpenLimit;
    }
}
