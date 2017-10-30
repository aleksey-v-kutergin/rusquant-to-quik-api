package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.TradesDataFrame;


/**
 *   Response contains a collection of trades for order.
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class TradesResponseBody extends ResponseBody
{
    private TradesDataFrame tradesDataFrame;

    public TradesResponseBody()
    {

    }

    public TradesResponseBody(TradesDataFrame tradesDataFrame)
    {
        this.tradesDataFrame = tradesDataFrame;
    }

    public TradesDataFrame getTradesDataFrame()
    {
        return tradesDataFrame;
    }

    public void setTradesDataFrame(TradesDataFrame tradesDataFrame)
    {
        this.tradesDataFrame = tradesDataFrame;
    }
}