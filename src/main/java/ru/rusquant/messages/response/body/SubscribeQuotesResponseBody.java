package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.QuotesDescriptor;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class SubscribeQuotesResponseBody extends ResponseBody
{
    private QuotesDescriptor descriptor;

    public SubscribeQuotesResponseBody()
    {

    }

    public SubscribeQuotesResponseBody(QuotesDescriptor descriptor)
    {
        this.descriptor = descriptor;
    }

    public QuotesDescriptor getDescriptor()
    {
        return descriptor;
    }

    public void setDescriptor(QuotesDescriptor descriptor)
    {
        this.descriptor = descriptor;
    }
}
