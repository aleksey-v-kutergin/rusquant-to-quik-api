package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.ParameterDescriptor;
import ru.rusquant.messages.response.body.ResponseBody;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class SubscribeParameterResponseBody extends ResponseBody
{
    private ParameterDescriptor descriptor;

    public SubscribeParameterResponseBody()
    {

    }

    public SubscribeParameterResponseBody(ParameterDescriptor descriptor)
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
