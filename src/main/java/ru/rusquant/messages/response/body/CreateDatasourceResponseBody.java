package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.DatasourceDescriptor;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class CreateDatasourceResponseBody extends ResponseBody
{
    private DatasourceDescriptor descriptor;

    public CreateDatasourceResponseBody()
    {

    }

    public CreateDatasourceResponseBody(DatasourceDescriptor descriptor)
    {
        this.descriptor = descriptor;
    }

    public DatasourceDescriptor getDescriptor()
    {
        return descriptor;
    }

    public void setDescriptor(DatasourceDescriptor descriptor)
    {
        this.descriptor = descriptor;
    }
}
