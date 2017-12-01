package ru.rusquant.data.quik;

import ru.rusquant.data.quik.types.DSParameterType;
import ru.rusquant.data.quik.types.TimeScale;

/**
 *    Class-descriptor for quik datasource for OHLC prices.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class DatasourceDescriptor extends QuikDataObject
{
    private Long id;
    private String classCode;
    private String securityCode;
    private TimeScale interval;
    private DSParameterType parameter;

    public DatasourceDescriptor()
    {

    }

    public DatasourceDescriptor(Long id, String classCode, String securityCode, TimeScale interval, DSParameterType parameter)
    {
        this.id = id;
        this.classCode = classCode;
        this.securityCode = securityCode;
        this.interval = interval;
        this.parameter = parameter;
    }

    @Override
    public String toString()
    {
        return "DatasourceDescriptor{" +
                "classCode='" + classCode + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", interval=" + interval +
                ", parameter=" + parameter +
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

    public TimeScale getInterval()
    {
        return interval;
    }

    public void setInterval(TimeScale interval)
    {
        this.interval = interval;
    }

    public DSParameterType getParameter()
    {
        return parameter;
    }

    public void setParameter(DSParameterType parameter)
    {
        this.parameter = parameter;
    }
}
