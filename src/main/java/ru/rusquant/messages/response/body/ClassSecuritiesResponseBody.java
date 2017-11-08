package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.CodesArray;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class ClassSecuritiesResponseBody extends ResponseBody
{
    private CodesArray codes;

    public ClassSecuritiesResponseBody()
    {

    }

    public ClassSecuritiesResponseBody(CodesArray codes)
    {
        this.codes = codes;
    }

    public CodesArray getCodes()
    {
        return codes;
    }

    public void setCodes(CodesArray codes)
    {
        this.codes = codes;
    }
}
