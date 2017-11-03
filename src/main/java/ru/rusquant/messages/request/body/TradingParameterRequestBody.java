package ru.rusquant.messages.request.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.types.ParameterType;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class TradingParameterRequestBody extends RequestBody
{
    private String classCode;
    private String securityCode;
    private ParameterType parameter;
    private String version;

    public TradingParameterRequestBody()
    {

    }

    public TradingParameterRequestBody(String classCode, String securityCode, ParameterType parameter, String version)
    {
        this.classCode = classCode;
        this.securityCode = securityCode;
        this.parameter = parameter;
        this.version = version;
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

    public ParameterType getParameter()
    {
        return parameter;
    }

    public void setParameter(ParameterType parameter)
    {
        this.parameter = parameter;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }
}
