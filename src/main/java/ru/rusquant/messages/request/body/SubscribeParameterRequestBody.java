package ru.rusquant.messages.request.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class SubscribeParameterRequestBody extends RequestBody
{
    private Long id;
    private String classCode;
    private String securityCode;
    private String parameterName;

    public SubscribeParameterRequestBody()
    {

    }

    public SubscribeParameterRequestBody(String classCode, String securityCode, String parameterName)
    {
        this.classCode = classCode;
        this.securityCode = securityCode;
        this.parameterName = parameterName;

        String paramsStr = this.classCode + this.securityCode + this.parameterName;
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

    public String getParameterName()
    {
        return parameterName;
    }
}
