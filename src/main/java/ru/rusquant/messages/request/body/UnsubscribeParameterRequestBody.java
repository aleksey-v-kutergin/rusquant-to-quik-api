package ru.rusquant.messages.request.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.ParameterDescriptor;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class UnsubscribeParameterRequestBody extends RequestBody
{
    private ParameterDescriptor descriptor;

    public UnsubscribeParameterRequestBody()
    {

    }

    public UnsubscribeParameterRequestBody(ParameterDescriptor descriptor)
    {
        this.descriptor = descriptor;
    }

    public ParameterDescriptor getDescriptor()
    {
        return descriptor;
    }

    public void setDescriptor(ParameterDescriptor descriptor)
    {
        this.descriptor = descriptor;
    }
}
