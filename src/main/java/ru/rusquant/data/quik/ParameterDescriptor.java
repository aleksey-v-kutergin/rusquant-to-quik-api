package ru.rusquant.data.quik;

/**
 *    Class-descriptor to order the parameters of the current trading table.
 *    Since, one needs:
 *      1. Subscribe for parameter
 *      2. Unsubscribe when work is done
 *
 *    To store a link to a server-cached subscription to a parameter on the client's side
 *    and manipulate it (close), it's convenient to use the descriptor object.
 *
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class ParameterDescriptor extends QuikDataObject
{
    /**
     *   According to Fengshui, the identifier should be generated on the server when caching a subscription to a parameter.
     *   However, with hashing in lloy all is difficult ... Therefore, as the identifier of the subscription in the cache,
     *   the hash code of the descriptor will be used. So it will be easy to check the duplication of subscriptions.
     *   If such hash is already there, then there is a subscription.
     **/
    private Long id;
    private String classCode;
    private String securityCode;
    private String parameterName;

    public ParameterDescriptor()
    {

    }

    public ParameterDescriptor(Long id, String classCode, String securityCode, String parameterName)
    {
        this.id = id;
        this.classCode = classCode;
        this.securityCode = securityCode;
        this.parameterName = parameterName;
    }

    @Override
    public String toString()
    {
        return "ParameterDescriptor: {" +
                "id=" + id +
                ", classCode='" + classCode + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", parameterName='" + parameterName + '\'' +
                '}';
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getClassCode()
    {
        return classCode;
    }

    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }

    public String getSecurityCode()
    {
        return securityCode;
    }

    public void setSecurityCode(String securityCode)
    {
        this.securityCode = securityCode;
    }

    public String getParameterName()
    {
        return parameterName;
    }

    public void setParameterName(String parameterName)
    {
        this.parameterName = parameterName;
    }
}
