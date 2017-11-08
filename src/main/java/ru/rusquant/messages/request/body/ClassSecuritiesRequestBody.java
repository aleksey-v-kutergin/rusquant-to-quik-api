package ru.rusquant.messages.request.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class ClassSecuritiesRequestBody extends RequestBody
{
    private String classCode;

    public ClassSecuritiesRequestBody()
    {

    }

    public ClassSecuritiesRequestBody(String classCode)
    {
        this.classCode = classCode;
    }

    public String getClassCode()
    {
        return classCode;
    }

    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }
}
