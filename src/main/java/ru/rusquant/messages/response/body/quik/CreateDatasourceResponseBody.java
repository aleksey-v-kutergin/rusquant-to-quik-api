package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.DatasourceDescriptor;
import ru.rusquant.messages.response.body.ResponseBody;

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
