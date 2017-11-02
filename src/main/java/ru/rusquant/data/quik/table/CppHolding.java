package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.QuikDataObject;

/**
 * Java implementation for ccp_holdings table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CppHolding extends QuikDataObject
{
    /** Identifier for firm **/
    @JsonProperty("firmid")
    private String firmId;

    /** The number of the depo account in the Depository (NDC) **/
    @JsonProperty("depo_account")
    private String depoAccount;

    /** Trading account **/
    @JsonProperty("account")
    private String account;

    /** Identifier of the settlement account / code in the clearing organization **/
    @JsonProperty("bank_acc_id")
    private String bankAccountId;

    /** Settlement date **/
    @JsonProperty("settle_date")
    private Long settlementDate;

    /** Number of securities in transactions **/
    @JsonProperty("qty")
    private Long qty;

    /** Number of securities in purchase orders **/
    @JsonProperty("qty_buy")
    private Long qtyBuy;

    /** The quantity of securities in the bids for sale **/
    @JsonProperty("qty_sell")
    private Long qtySell;

    /** Net position **/
    @JsonProperty("netto")
    private Double netPosition;

    /** Debit **/
    @JsonProperty("debit")
    private Double debit;

    /** Credit **/
    @JsonProperty("credit")
    private Double credit;

    /** Code of the security **/
    @JsonProperty("sec_code")
    private String securityCode;

    /** Code of the class **/
    @JsonProperty("class_code")
    private String classCode;

    public CppHolding()
    {

    }

    @Override
    public String toString()
    {
        return "CppHolding: {" +
                "firmId='" + firmId + '\'' +
                ", depoAccount='" + depoAccount + '\'' +
                ", account='" + account + '\'' +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", settlementDate=" + settlementDate +
                ", qty=" + qty +
                ", qtyBuy=" + qtyBuy +
                ", qtySell=" + qtySell +
                ", netPosition=" + netPosition +
                ", debit=" + debit +
                ", credit=" + credit +
                ", securityCode='" + securityCode + '\'' +
                ", classCode='" + classCode + '\'' +
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

    public String getDepoAccount()
    {
        return depoAccount;
    }

    public void setDepoAccount(String depoAccount)
    {
        this.depoAccount = depoAccount;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
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

    public Long getQty()
    {
        return qty;
    }

    public void setQty(Long qty)
    {
        this.qty = qty;
    }

    public Long getQtyBuy()
    {
        return qtyBuy;
    }

    public void setQtyBuy(Long qtyBuy)
    {
        this.qtyBuy = qtyBuy;
    }

    public Long getQtySell()
    {
        return qtySell;
    }

    public void setQtySell(Long qtySell)
    {
        this.qtySell = qtySell;
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

    public String getSecurityCode()
    {
        return securityCode;
    }

    public void setSecurityCode(String securityCode)
    {
        this.securityCode = securityCode;
    }

    public String getClassCode()
    {
        return classCode;
    }

    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }
}
