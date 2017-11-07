package ru.rusquant.messages.request.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class SecurityInfoRequestBody extends RequestBody
{
    private String classCode;
    private String securityCode;

    public SecurityInfoRequestBody()
    {

    }

    public SecurityInfoRequestBody(String classCode, String securityCode)
    {
        this.classCode = classCode;
        this.securityCode = securityCode;
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
