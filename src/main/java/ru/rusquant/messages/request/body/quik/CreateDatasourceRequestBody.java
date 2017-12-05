package ru.rusquant.messages.request.body.quik;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.types.DSParameterType;
import ru.rusquant.data.quik.types.TimeScale;
import ru.rusquant.messages.request.body.RequestBody;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class CreateDatasourceRequestBody extends RequestBody
{
    private Long id;
    private String classCode;
    private String securityCode;
    private TimeScale interval;
    private DSParameterType parameter;

    public CreateDatasourceRequestBody()
    {

    }

    public CreateDatasourceRequestBody(String classCode, String securityCode, TimeScale interval)
    {
        this.classCode = classCode;
        this.securityCode = securityCode;
        this.interval = interval;

        String paramsStr =  this.classCode +
                            this.securityCode +
                            this.interval.toString();
        long hash = (long) paramsStr.hashCode();
        this.id = hash < 0 ? -1 * hash : hash;
    }


    public CreateDatasourceRequestBody(String classCode, String securityCode, TimeScale interval, DSParameterType parameter)
    {
        this.classCode = classCode;
        this.securityCode = securityCode;
        this.interval = interval;
        this.parameter = parameter;

        String paramsStr =  this.classCode +
                            this.securityCode +
                            this.interval.toString() +
                            this.parameter.toString();
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

    public TimeScale getInterval()
    {
        return interval;
    }

    public DSParameterType getParameter()
    {
        return parameter;
    }
}
