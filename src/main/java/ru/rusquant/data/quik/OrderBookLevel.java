package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Java implementation for order book level.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderBookLevel extends QuikDataObject
{
    /** Number of the price level starting form mid **/
    @JsonProperty("number")
    private Long number;

    /** Price for order book level **/
    @JsonProperty("price")
    private Double price;

    /** Quantity of order at this order book level **/
    @JsonProperty("quantity")
    private Long quantity;

    public OrderBookLevel()
    {

    }

    public OrderBookLevel(Long number, Double price, Long quantity)
    {
        this.number = number;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString()
    {
        return "OrderBookLevel: {" +
                "number=" + number +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public Long getNumber()
    {
        return number;
    }

    public void setNumber(Long number)
    {
        this.number = number;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Long getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Long quantity)
    {
        this.quantity = quantity;
    }
}
