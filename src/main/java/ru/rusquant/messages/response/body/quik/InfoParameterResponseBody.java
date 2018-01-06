package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.InfoParameter;
import ru.rusquant.messages.response.body.ResponseBody;

public class InfoParameterResponseBody extends ResponseBody {
    private InfoParameter infoParameter;

    public InfoParameterResponseBody() {

    }

    public InfoParameterResponseBody(InfoParameter infoParameter) {
        this.infoParameter = infoParameter;
    }


    public InfoParameter getInfoParameter() {
        return infoParameter;
    }

    public void setInfoParameter(InfoParameter infoParameter) {
        this.infoParameter = infoParameter;
    }
}
