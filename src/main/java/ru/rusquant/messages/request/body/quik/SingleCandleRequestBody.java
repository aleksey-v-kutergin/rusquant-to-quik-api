package ru.rusquant.messages.request.body.quik;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.DatasourceDescriptor;
import ru.rusquant.messages.request.body.RequestBody;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class SingleCandleRequestBody extends RequestBody
{
    private DatasourceDescriptor descriptor;
    private Long candleIndex;

    public SingleCandleRequestBody()
    {

    }

    public SingleCandleRequestBody(DatasourceDescriptor descriptor, Long candleIndex)
    {
        this.descriptor = descriptor;
        this.candleIndex = candleIndex;
    }

    public DatasourceDescriptor getDescriptor()
    {
        return descriptor;
    }

    public void setDescriptor(DatasourceDescriptor descriptor)
    {
        this.descriptor = descriptor;
    }

    public Long getCandleIndex()
    {
        return candleIndex;
    }

    public void setCandleIndex(Long candleIndex)
    {
        this.candleIndex = candleIndex;
    }
}
