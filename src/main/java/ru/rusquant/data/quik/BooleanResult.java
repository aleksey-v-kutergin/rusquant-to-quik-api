package ru.rusquant.data.quik;

/**
 * Wrapper-object for boolean result of the qlua function.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class BooleanResult extends QuikDataObject {
    private Boolean value = Boolean.FALSE;

    public BooleanResult() {

    }

    @Override
    public String toString() {
        return "BooleanResult: {" +
                "value=" + value +
                '}';
    }

    public BooleanResult(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
