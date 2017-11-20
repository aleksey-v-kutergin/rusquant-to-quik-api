package ru.rusquant.data.quik;


/**
 *    Class-descriptor to order order book data for security at quik server.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class QuotesDescriptor extends QuikDataObject
{
    private Long id;
    private String classCode;
    private String securityCode;

    public QuotesDescriptor()
    {

    }

    public QuotesDescriptor(Long id, String classCode, String securityCode)
    {
        this.id = id;
        this.classCode = classCode;
        this.securityCode = securityCode;
    }

    @Override
    public String toString()
    {
        return "QuotesDescriptor: {" +
                "id=" + id +
                ", classCode='" + classCode + '\'' +
                ", securityCode='" + securityCode + '\'' +
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
}
