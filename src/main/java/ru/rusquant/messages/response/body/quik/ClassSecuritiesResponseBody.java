package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.CodesArray;
import ru.rusquant.messages.response.body.ResponseBody;

/**
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class ClassSecuritiesResponseBody extends ResponseBody {
    private CodesArray codes;

    public ClassSecuritiesResponseBody() {

    }

    public ClassSecuritiesResponseBody(CodesArray codes) {
        this.codes = codes;
    }

    public CodesArray getCodes() {
        return codes;
    }

    public void setCodes(CodesArray codes) {
        this.codes = codes;
    }
}
