package ru.rusquant.data.quik;

/**
 * ErrorObject data object.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class ErrorObject extends QuikDataObject {
    private String errorMessage;

    public ErrorObject() {

    }

    public ErrorObject(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
