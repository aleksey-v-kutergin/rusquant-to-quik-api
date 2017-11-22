package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;

/**
 * Java implementation for order book.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderBook extends QuikDataObject
{
    /** Depth of the order book at bid. In the absence of demand, the value "0" **/
    @JsonProperty("bid_count")
    private Long bidCount;

    /** Depth of the order book at bid. In the absence of demand, the value "0" **/
    @JsonProperty("offer_count")
    private Long offerCount;

    /** Bid-levels **/
    @JsonDeserialize(as = OrderBookLevel[].class)
    @JsonProperty("bid")
    private OrderBookLevel[] bid;

    /** Ask-levels **/
    @JsonDeserialize(as = OrderBookLevel[].class)
    @JsonProperty("offer")
    private OrderBookLevel[] ask;

    public OrderBook()
    {

    }

    public OrderBook(Long bidCount, Long offerCount, OrderBookLevel[] bid, OrderBookLevel[] ask)
    {
        this.bidCount = bidCount;
        this.offerCount = offerCount;
        this.bid = bid;
        this.ask = ask;
    }

    @Override
    public String toString()
    {
        return "OrderBook: {" +
                "bidCount=" + bidCount +
                ", offerCount=" + offerCount +
                ", bid=" + Arrays.toString(bid) +
                ", ask=" + Arrays.toString(ask) +
                '}';
    }

    public Long getBidCount()
    {
        return bidCount;
    }

    public void setBidCount(Long bidCount)
    {
        this.bidCount = bidCount;
    }

    public Long getOfferCount()
    {
        return offerCount;
    }

    public void setOfferCount(Long offerCount)
    {
        this.offerCount = offerCount;
    }

    public OrderBookLevel[] getBid()
    {
        return bid;
    }

    public void setBid(OrderBookLevel[] bid)
    {
        this.bid = bid;
    }

    public OrderBookLevel[] getAsk()
    {
        return ask;
    }

    public void setAsk(OrderBookLevel[] ask)
    {
        this.ask = ask;
    }
}
