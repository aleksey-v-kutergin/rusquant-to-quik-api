package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.LongResult;
import ru.rusquant.messages.response.body.ResponseBody;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class DatasourceSizeResponseBody extends ResponseBody
{
    private LongResult result;

    public DatasourceSizeResponseBody()
    {

    }

    public DatasourceSizeResponseBody(LongResult result)
    {
        this.result = result;
    }

    public LongResult getResult()
    {
        return result;
    }

    public void setResult(LongResult result)
    {
        this.result = result;
    }
}
