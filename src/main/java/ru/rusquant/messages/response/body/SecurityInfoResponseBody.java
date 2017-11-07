package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.table.Security;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class SecurityInfoResponseBody extends ResponseBody
{
    private Security security;

    public SecurityInfoResponseBody()
    {

    }

    public SecurityInfoResponseBody(Security security)
    {
        this.security = security;
    }

    public Security getSecurity()
    {
        return security;
    }

    public void setSecurity(Security security)
    {
        this.security = security;
    }
}
