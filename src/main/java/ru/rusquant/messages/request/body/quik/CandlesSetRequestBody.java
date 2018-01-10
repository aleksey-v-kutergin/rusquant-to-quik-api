package ru.rusquant.messages.request.body.quik;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.descriptor.DatasourceDescriptor;
import ru.rusquant.messages.request.body.RequestBody;

/**
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonAutoDetect
public class CandlesSetRequestBody extends RequestBody {

    private DatasourceDescriptor descriptor;
    private Integer firstIndex;
    private Integer lastIndex;

    public CandlesSetRequestBody() {

    }

    public CandlesSetRequestBody(DatasourceDescriptor descriptor, Integer firstIndex, Integer lastIndex) {
        this.descriptor = descriptor;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
    }

    public DatasourceDescriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(DatasourceDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public Integer getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(Integer firstIndex) {
        this.firstIndex = firstIndex;
    }

    public Integer getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(Integer lastIndex) {
        this.lastIndex = lastIndex;
    }
}
