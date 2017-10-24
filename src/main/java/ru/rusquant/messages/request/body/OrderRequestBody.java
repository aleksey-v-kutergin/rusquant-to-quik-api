package ru.rusquant.messages.request.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class OrderRequestBody extends RequestBody
{
    private Long orderNumber;

    public OrderRequestBody()
    {

    }

    public OrderRequestBody(Long orderNumber)
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
