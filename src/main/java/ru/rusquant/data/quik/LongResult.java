package ru.rusquant.data.quik;

/**
 * Wrapper-object for long\int result of the qlua function.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class LongResult extends QuikDataObject {

    private Long value = 0L;

    public LongResult() {

    }

    public LongResult(Long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongResult: {" +
                "value=" + value +
                '}';
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
