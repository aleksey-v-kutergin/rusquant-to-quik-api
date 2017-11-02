package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.table.Order;

public class OrderResponseBody extends ResponseBody
{
    private Order order;

    public OrderResponseBody()
    {

    }

    public OrderResponseBody(Order order)
    {
        this.order = order;
    }


    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }
}
