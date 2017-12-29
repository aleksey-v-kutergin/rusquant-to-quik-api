package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.table.Depo;
import ru.rusquant.messages.response.body.ResponseBody;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class DepoResponseBody extends ResponseBody
{
    private Depo depo;

    public DepoResponseBody()
    {

    }

    public DepoResponseBody(Depo depo)
    {
        this.depo = depo;
    }

    public Depo getDepo()
    {
        return depo;
    }

    public void setDepo(Depo depo)
    {
        this.depo = depo;
    }
}
