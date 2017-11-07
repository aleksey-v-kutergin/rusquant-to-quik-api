package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.CountOfLots;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class MaxCountOfLotsResponseBody extends ResponseBody
{
    private CountOfLots countOfLots;

    public MaxCountOfLotsResponseBody()
    {

    }

    public MaxCountOfLotsResponseBody(CountOfLots countOfLots)
    {
        this.countOfLots = countOfLots;
    }

    public CountOfLots getCountOfLots()
    {
        return countOfLots;
    }

    public void setCountOfLots(CountOfLots countOfLots)
    {
        this.countOfLots = countOfLots;
    }
}
