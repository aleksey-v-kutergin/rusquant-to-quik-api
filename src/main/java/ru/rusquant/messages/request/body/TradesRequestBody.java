package ru.rusquant.messages.request.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 *   Request for trades by order number.
 *   In general it could be several trades, triggered by order.
 *   Thus, one need to query a collection of trades
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class TradesRequestBody extends RequestBody
{
    private Long orderNumber;

    public TradesRequestBody()
    {

    }

    public TradesRequestBody(Long orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public Long getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber)
    {
        this.orderNumber = orderNumber;
    }
}
