package ru.rusquant.messages.request.body.quik;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.descriptor.DatasourceDescriptor;
import ru.rusquant.messages.request.body.RequestBody;

/**
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonAutoDetect
public class AllCandlesRequestBody extends RequestBody {

    private DatasourceDescriptor descriptor;

    public AllCandlesRequestBody() {

    }

    public AllCandlesRequestBody(DatasourceDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public DatasourceDescriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(DatasourceDescriptor descriptor) {
        this.descriptor = descriptor;
    }
}
