package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.dataframe.TradesDataFrame;
import ru.rusquant.messages.response.body.ResponseBody;


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
