package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.DateTime;
import ru.rusquant.data.quik.QuikDataObject;
import ru.rusquant.data.quik.types.TradingPeriodType;

/**
 * Java implementation for all_trades table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnonymousTrade extends QuikDataObject
{
    /** Number of trade **/
    @JsonProperty("trade_num")
    private Long tradeNumber;

    /** Code of the partner **/
    @JsonProperty("flags")
    private Integer flags;

    /** Price **/
    @JsonProperty("price")
    private Double price;

    /** Count in lots **/
    @JsonProperty("qty")
    private Long qty;

    /** Volume in cash **/
    @JsonProperty("value")
    private Double value;

    /** Accrued coupon yield **/
    @JsonProperty("accruedint")
    private Double accruedCouponYield;

    /** Accrued coupon yield **/
    @JsonProperty("yield")
    private Double yield;

    /** Payment code **/
    @JsonProperty("settlecode")
    private String paymentCode;

    /** Redemption price **/
    @JsonProperty("reporate")
    private Double repoRate;

    /** Term of repurchase, in calendar days **/
    @JsonProperty("repoterm")
    private Long repoTerm;

    /** The amount of REPO at the current date **/
    @JsonProperty("repovalue")
    private Double repoValue;

    /** Volume of REPO repurchase agreement **/
    @JsonProperty("repo2value")
    private Double repoVolume;

    /** Security code **/
    @JsonProperty("sec_code")
    private String securityCode;

    /** Class code **/
    @JsonProperty("class_code")
    private String classCode;

    /** Date time of trade **/
    @JsonProperty("datetime")
    private DateTime dateTime;

    /** Type of the trading session **/
    @JsonProperty("period")
    private TradingPeriodType period;

    /** Open interest **/
    @JsonProperty("open_interest")
    private Double openInterest;

    /** Exchange code in the trading system **/
    @JsonProperty("exchange_code")
    private String exchangeCode;

    public AnonymousTrade()
    {

    }

    @Override
    public String toString()
    {
        return "AnonymousTrade: {" +
                "tradeNumber=" + tradeNumber +
                ", flags=" + flags +
                ", price=" + price +
                ", qty=" + qty +
                ", value=" + value +
                ", accruedCouponYield=" + accruedCouponYield +
                ", yield=" + yield +
                ", paymentCode='" + paymentCode + '\'' +
                ", repoRate=" + repoRate +
                ", repoTerm=" + repoTerm +
                ", repoValue=" + repoValue +
                ", repoVolume=" + repoVolume +
                ", securityCode='" + securityCode + '\'' +
                ", classCode='" + classCode + '\'' +
                ", dateTime=" + dateTime +
                ", period=" + period +
                ", openInterest=" + openInterest +
                ", exchangeCode='" + exchangeCode + '\'' +
                '}';
    }

    public Long getTradeNumber()
    {
        return tradeNumber;
    }

    public void setTradeNumber(Long tradeNumber)
    {
        this.tradeNumber = tradeNumber;
    }

    public Integer getFlags()
    {
        return flags;
    }

    public void setFlags(Integer flags)
    {
        this.flags = flags;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
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

    public Double getAccruedCouponYield()
    {
        return accruedCouponYield;
    }

    public void setAccruedCouponYield(Double accruedCouponYield)
    {
        this.accruedCouponYield = accruedCouponYield;
    }

    public Double getYield()
    {
        return yield;
    }

    public void setYield(Double yield)
    {
        this.yield = yield;
    }

    public String getPaymentCode()
    {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode)
    {
        this.paymentCode = paymentCode;
    }

    public Double getRepoRate()
    {
        return repoRate;
    }

    public void setRepoRate(Double repoRate)
    {
        this.repoRate = repoRate;
    }

    public Long getRepoTerm()
    {
        return repoTerm;
    }

    public void setRepoTerm(Long repoTerm)
    {
        this.repoTerm = repoTerm;
    }

    public Double getRepoValue()
    {
        return repoValue;
    }

    public void setRepoValue(Double repoValue)
    {
        this.repoValue = repoValue;
    }

    public Double getRepoVolume()
    {
        return repoVolume;
    }

    public void setRepoVolume(Double repoVolume)
    {
        this.repoVolume = repoVolume;
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

    public DateTime getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime)
    {
        this.dateTime = dateTime;
    }

    public TradingPeriodType getPeriod()
    {
        return period;
    }

    public void setPeriod(TradingPeriodType period)
    {
        this.period = period;
    }

    public Double getOpenInterest()
    {
        return openInterest;
    }

    public void setOpenInterest(Double openInterest)
    {
        this.openInterest = openInterest;
    }

    public String getExchangeCode()
    {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode)
    {
        this.exchangeCode = exchangeCode;
    }
}
