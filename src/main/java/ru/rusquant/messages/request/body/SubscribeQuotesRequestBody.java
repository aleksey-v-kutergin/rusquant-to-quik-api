package ru.rusquant.messages.request.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class SubscribeQuotesRequestBody extends RequestBody
{
    private Long id;
    private String classCode;
    private String securityCode;

    public SubscribeQuotesRequestBody()
    {

    }

    public SubscribeQuotesRequestBody(String classCode, String securityCode)
    {
        this.classCode = classCode;
        this.securityCode = securityCode;

        String paramsStr = this.classCode + this.securityCode;
        long hash = (long) paramsStr.hashCode();
        this.id = hash < 0 ? -1 * hash : hash;
    }

    public Long getId()
    {
        return id;
    }

    public String getClassCode()
    {
        return classCode;
    }

    public String getSecurityCode()
    {
        return securityCode;
    }
}
