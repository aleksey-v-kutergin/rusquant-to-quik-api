package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Java implementation for neg_deal_reports table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NegDealReport extends QuikDataObject
{
    /** Report number **/
    @JsonProperty("report_num")
    private Long reportNumber;

    /** Report date **/
    @JsonProperty("report_date")
    private Long reportDate;

    /** Flags **/
    @JsonProperty("flags")
    private Integer flags;

    /** User identifier **/
    @JsonProperty("userid")
    private String userId;

    /** Firm identifier **/
    @JsonProperty("firmid")
    private String firmId;

    /** Depo account **/
    @JsonProperty("account")
    private String account;

    /** Partner-firm identifier **/
    @JsonProperty("cpfirmid")
    private String cpFirmId;

    /** Partner-firm trading account **/
    @JsonProperty("cpaccount")
    private String cpAccount;

    /** Quantity of securities in lots **/
    @JsonProperty("qty")
    private Long qty;

    /** Volume of trade in RUB **/
    @JsonProperty("value")
    private Double value;

    /** Order withdraw time **/
    @JsonProperty("withdraw_time")
    private Long withdrawTime;

    /** Report type **/
    @JsonProperty("report_type")
    private Integer reportType;

    /** Report kind **/
    @JsonProperty("report_kind")
    private Integer reportKind;

    /** Volume of trade commission in RUB **/
    @JsonProperty("commission")
    private Double commission;

    /** Security code **/
    @JsonProperty("sec_code")
    private String securityCode;

    /** Security class code **/
    @JsonProperty("class_code")
    private String classCode;

    /** Report time??? **/
    @JsonProperty("report_time")
    private Long reportTime;

    /** Report date time **/
    @JsonProperty("report_date_time")
    private DateTime reportDateTime;

    public NegDealReport()
    {

    }

    @Override
    public String toString()
    {
        return "NegDealReport: {" +
                "reportNumber=" + reportNumber +
                ", reportDate=" + reportDate +
                ", flags=" + flags +
                ", userId='" + userId + '\'' +
                ", firmId='" + firmId + '\'' +
                ", account='" + account + '\'' +
                ", cpFirmId='" + cpFirmId + '\'' +
                ", cpAccount='" + cpAccount + '\'' +
                ", qty=" + qty +
                ", value=" + value +
                ", withdrawTime=" + withdrawTime +
                ", reportType=" + reportType +
                ", reportKind=" + reportKind +
                ", commission=" + commission +
                ", securityCode='" + securityCode + '\'' +
                ", classCode='" + classCode + '\'' +
                ", reportTime=" + reportTime +
                ", reportDateTime=" + reportDateTime +
                '}';
    }

    public Long getReportNumber()
    {
        return reportNumber;
    }

    public void setReportNumber(Long reportNumber)
    {
        this.reportNumber = reportNumber;
    }

    public Long getReportDate()
    {
        return reportDate;
    }

    public void setReportDate(Long reportDate)
    {
        this.reportDate = reportDate;
    }

    public Integer getFlags()
    {
        return flags;
    }

    public void setFlags(Integer flags)
    {
        this.flags = flags;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getFirmId()
    {
        return firmId;
    }

    public void setFirmId(String firmId)
    {
        this.firmId = firmId;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getCpFirmId()
    {
        return cpFirmId;
    }

    public void setCpFirmId(String cpFirmId)
    {
        this.cpFirmId = cpFirmId;
    }

    public String getCpAccount()
    {
        return cpAccount;
    }

    public void setCpAccount(String cpAccount)
    {
        this.cpAccount = cpAccount;
    }

    public Long getQty()
    {
        return qty;
    }

    public void setQty(Long qty)
    {
        this.qty = qty;
    }

    public Double getValue()
    {
        return value;
    }

    public void setValue(Double value)
    {
        this.value = value;
    }

    public Long getWithdrawTime()
    {
        return withdrawTime;
    }

    public void setWithdrawTime(Long withdrawTime)
    {
        this.withdrawTime = withdrawTime;
    }

    public Integer getReportType()
    {
        return reportType;
    }

    public void setReportType(Integer reportType)
    {
        this.reportType = reportType;
    }

    public Integer getReportKind()
    {
        return reportKind;
    }

    public void setReportKind(Integer reportKind)
    {
        this.reportKind = reportKind;
    }

    public Double getCommission()
    {
        return commission;
    }

    public void setCommission(Double commission)
    {
        this.commission = commission;
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

    public Long getReportTime()
    {
        return reportTime;
    }

    public void setReportTime(Long reportTime)
    {
        this.reportTime = reportTime;
    }

    public DateTime getReportDateTime()
    {
        return reportDateTime;
    }

    public void setReportDateTime(DateTime reportDateTime)
    {
        this.reportDateTime = reportDateTime;
    }
}
