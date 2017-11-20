package ru.rusquant.messages.request.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.QuotesDescriptor;


/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class UnsubscribeQuotesRequestBody extends RequestBody
{
    private QuotesDescriptor descriptor;

    public UnsubscribeQuotesRequestBody()
    {

    }

    public UnsubscribeQuotesRequestBody(QuotesDescriptor descriptor)
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
