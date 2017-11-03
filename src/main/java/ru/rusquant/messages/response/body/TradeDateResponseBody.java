package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.TradeDate;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class TradeDateResponseBody extends ResponseBody
{
    private TradeDate tradeDate;

    public TradeDateResponseBody()
    {

    }

    public TradeDateResponseBody(TradeDate tradeDate)
    {
        this.tradeDate = tradeDate;
    }

    public TradeDate getTradeDate()
    {
        return tradeDate;
    }

    public void setTradeDate(TradeDate tradeDate)
    {
        this.tradeDate = tradeDate;
    }
}
