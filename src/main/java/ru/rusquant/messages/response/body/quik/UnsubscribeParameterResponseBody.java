package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.BooleanResult;
import ru.rusquant.messages.response.body.ResponseBody;

/**
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class UnsubscribeParameterResponseBody extends ResponseBody {
    private BooleanResult result;

    public UnsubscribeParameterResponseBody() {

    }

    public UnsubscribeParameterResponseBody(BooleanResult result) {
        this.result = result;
    }

    public BooleanResult getResult() {
        return result;
    }

    public void setResult(BooleanResult result) {
        this.result = result;
    }
}
