package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.QuikDataObject;

/**
 * Java implementation for firm table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirmHolding extends QuikDataObject
{
    /** Firm identifier **/
    @JsonProperty("firmid")
    private String firmId;

    /** Code of security **/
    @JsonProperty("sec_code")
    private String securityCode;

    /** Incoming balance **/
    @JsonProperty("openbal")
    private Double openBalance;

    /** Current balance **/
    @JsonProperty("currentpos")
    private Double currentPosition;

    /** Volume of active orders for purchase, in securities **/
    @JsonProperty("plannedposbuy")
    private Long plannedPositionInBuyOrders;

    /** Volume of active applications for sale, in securities **/
    @JsonProperty("plannedpossell")
    private Long plannedPositionInSellOrders;

    /** Purchased **/
    @JsonProperty("usqtyb")
    private Double usqtyb;

    /** Sales **/
    @JsonProperty("usqtys")
    private Double usqtys;

    public FirmHolding()
    {

    }

    @Override
    public String toString()
    {
        return "FirmHolding: {" +
                "firmId='" + firmId + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", openBalance=" + openBalance +
                ", currentPosition=" + currentPosition +
                ", plannedPositionInBuyOrders=" + plannedPositionInBuyOrders +
                ", plannedPositionInSellOrders=" + plannedPositionInSellOrders +
                ", usqtyb=" + usqtyb +
                ", usqtys=" + usqtys +
                '}';
    }

    public String getFirmId()
    {
        return firmId;
    }

    public void setFirmId(String firmId)
    {
        this.firmId = firmId;
    }

    public String getSecurityCode()
    {
        return securityCode;
    }

    public void setSecurityCode(String securityCode)
    {
        this.securityCode = securityCode;
    }

    public Double getOpenBalance()
    {
        return openBalance;
    }

    public void setOpenBalance(Double openBalance)
    {
        this.openBalance = openBalance;
    }

    public Double getCurrentPosition()
    {
        return currentPosition;
    }

    public void setCurrentPosition(Double currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    public Long getPlannedPositionInBuyOrders()
    {
        return plannedPositionInBuyOrders;
    }

    public void setPlannedPositionInBuyOrders(Long plannedPositionInBuyOrders)
    {
        this.plannedPositionInBuyOrders = plannedPositionInBuyOrders;
    }

    public Long getPlannedPositionInSellOrders()
    {
        return plannedPositionInSellOrders;
    }

    public void setPlannedPositionInSellOrders(Long plannedPositionInSellOrders)
    {
        this.plannedPositionInSellOrders = plannedPositionInSellOrders;
    }

    public Double getUsqtyb()
    {
        return usqtyb;
    }

    public void setUsqtyb(Double usqtyb)
    {
        this.usqtyb = usqtyb;
    }

    public Double getUsqtys()
    {
        return usqtys;
    }

    public void setUsqtys(Double usqtys)
    {
        this.usqtys = usqtys;
    }
}
