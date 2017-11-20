package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.BooleanResult;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class IsSubscribeQuotesResponseBody extends ResponseBody
{
    private BooleanResult result;

    public IsSubscribeQuotesResponseBody()
    {

    }

    public IsSubscribeQuotesResponseBody(BooleanResult result)
    {
        this.result = result;
    }

    public BooleanResult getResult()
    {
        return result;
    }

    public void setResult(BooleanResult result)
    {
        this.result = result;
    }
}
