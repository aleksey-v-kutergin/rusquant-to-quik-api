package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Java implementation for ccp_positions table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CppPosition extends QuikDataObject
{
    /** Firm identifier **/
    @JsonProperty("firmid")
    private String firmId;

    /** Identifier of the settlement account / code in the clearing organization **/
    @JsonProperty("bank_acc_id")
    private String bankAccountId;

    /** Settlement date **/
    @JsonProperty("settle_date")
    private Long settlementDate;

    /** Net position **/
    @JsonProperty("netto")
    private Double netPosition;

    /** Debit **/
    @JsonProperty("debit")
    private Double debit;

    /** Credit **/
    @JsonProperty("credit")
    private Double credit;

    public CppPosition()
    {

    }

    @Override
    public String toString()
    {
        return "CppPosition: {" +
                "firmId='" + firmId + '\'' +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", settlementDate=" + settlementDate +
                ", netPosition=" + netPosition +
                ", debit=" + debit +
                ", credit=" + credit +
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

    public String getBankAccountId()
    {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId)
    {
        this.bankAccountId = bankAccountId;
    }

    public Long getSettlementDate()
    {
        return settlementDate;
    }

    public void setSettlementDate(Long settlementDate)
    {
        this.settlementDate = settlementDate;
    }

    public Double getNetPosition()
    {
        return netPosition;
    }

    public void setNetPosition(Double netPosition)
    {
        this.netPosition = netPosition;
    }

    public Double getDebit()
    {
        return debit;
    }

    public void setDebit(Double debit)
    {
        this.debit = debit;
    }

    public Double getCredit()
    {
        return credit;
    }

    public void setCredit(Double credit)
    {
        this.credit = credit;
    }
}
