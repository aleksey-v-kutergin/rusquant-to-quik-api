package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.Candle;
import ru.rusquant.messages.response.body.ResponseBody;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class SingleCandleResponseBody extends ResponseBody
{
    private Candle candle;

    public SingleCandleResponseBody()
    {

    }

    public SingleCandleResponseBody(Candle candle)
    {
        this.candle = candle;
    }

    public Candle getCandle()
    {
        return candle;
    }

    public void setCandle(Candle candle)
    {
        this.candle = candle;
    }
}
