package ru.rusquant.messages.request.body.quik;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.types.InfoParamType;
import ru.rusquant.messages.request.body.RequestBody;

/**
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonAutoDetect
public class InfoParameterRequestBody extends RequestBody {
    private InfoParamType infoParamType;

    public InfoParameterRequestBody() {

    }

    public InfoParameterRequestBody(InfoParamType infoParamType) {
        this.infoParamType = infoParamType;
    }

    public InfoParamType getInfoParamType() {
        return infoParamType;
    }

    public void setInfoParamType(InfoParamType infoParamType) {
        this.infoParamType = infoParamType;
    }
}
