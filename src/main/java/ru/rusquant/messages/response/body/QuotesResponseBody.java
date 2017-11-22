package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.OrderBook;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class QuotesResponseBody extends ResponseBody
{
    private OrderBook orderBook;

    public QuotesResponseBody()
    {

    }

    public QuotesResponseBody(OrderBook orderBook)
    {
        this.orderBook = orderBook;
    }

    public OrderBook getOrderBook()
    {
        return orderBook;
    }

    public void setOrderBook(OrderBook orderBook)
    {
        this.orderBook = orderBook;
    }
}
