package ru.rusquant.data.quik.descriptor;


/**
 * Class-descriptor to order order book data for security at quik server.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class QuotesDescriptor extends Descriptor {

    public QuotesDescriptor() {

    }

    public QuotesDescriptor(Long id, String classCode, String securityCode) {
        super(id, classCode, securityCode);
    }

    @Override
    public String toString() {
        return "QuotesDescriptor: {" +
                "id=" + id +
                ", classCode='" + classCode + '\'' +
                ", securityCode='" + securityCode + '\'' +
                '}';
    }
}
