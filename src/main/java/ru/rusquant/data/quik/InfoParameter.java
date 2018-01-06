package ru.rusquant.data.quik;

/**
 * Terminal's info parameter data object.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class InfoParameter extends QuikDataObject {
    private String parameterName;
    private String parameterValue;

    public InfoParameter() {

    }

    public InfoParameter(String parameterName, String parameterValue) {
        this.parameterValue = parameterValue;
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
}
