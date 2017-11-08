package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.CodesArray;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class ClassesListResponseBody extends ResponseBody
{
    private CodesArray codes;

    public ClassesListResponseBody()
    {

    }

    public ClassesListResponseBody(CodesArray codes)
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
