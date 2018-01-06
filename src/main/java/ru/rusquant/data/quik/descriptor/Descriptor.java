package ru.rusquant.data.quik.descriptor;

import ru.rusquant.data.quik.QuikDataObject;

/**
 * Base class for descriptors.
 * Since, one needs:
 * 1. Subscribe for something at server side: parameter\quote\OHCL prices
 * 2. Unsubscribe when work is done
 * <p>
 * To store a link to a server-cached subscription to a parameter\quote\datasource on the client's side
 * and manipulate it (close), it's convenient to use the descriptor object.
 * <p>
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class Descriptor extends QuikDataObject {
    /**
     * According to Fengshui, the identifier should be generated on the server when caching a subscription to a server resource.
     * However, hashing in lua is difficult ... Therefore, as the identifier of the subscription in the cache,
     * the hash code of the descriptor will be used. So it will be easy to check the duplication of subscriptions.
     * If such hash is already there, then there is a subscription.
     **/
    protected Long id;
    protected String classCode;
    protected String securityCode;

    public Descriptor() {

    }

    public Descriptor(Long id, String classCode, String securityCode) {
        this.id = id;
        this.classCode = classCode;
        this.securityCode = securityCode;
    }

    @Override
    public String toString() {
        return "Descriptor{" +
                "id=" + id +
                ", classCode='" + classCode + '\'' +
                ", securityCode='" + securityCode + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
