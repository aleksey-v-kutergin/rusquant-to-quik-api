package ru.rusquant.messages.request.body.quik;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.descriptor.QuotesDescriptor;
import ru.rusquant.messages.request.body.RequestBody;

/**
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonAutoDetect
public class IsSubscribeQuotesRequestBody extends RequestBody {

    private QuotesDescriptor descriptor;

    public IsSubscribeQuotesRequestBody() {

    }

    public IsSubscribeQuotesRequestBody(QuotesDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public QuotesDescriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(QuotesDescriptor descriptor) {
        this.descriptor = descriptor;
    }
}
