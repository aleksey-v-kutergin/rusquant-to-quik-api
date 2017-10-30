package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Java implementation for class table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class SecurityClass
{
    /** Firm identifier **/
    @JsonProperty("firmid")
    private String firmId;

    /** Class name **/
    @JsonProperty("name")
    private String name;

    /** Class code **/
    @JsonProperty("code")
    private String code;

    /** Number of parameters in class **/
    @JsonProperty("npars")
    private Integer numberOfParameters;

    /** Number of securities in class **/
    @JsonProperty("nsecs")
    private Integer numberOfSecurities;

    public SecurityClass()
    {

    }

    @Override
    public String toString()
    {
        return "SecurityClass: {" +
                "firmId='" + firmId + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", numberOfParameters=" + numberOfParameters +
                ", numberOfSecurities=" + numberOfSecurities +
                '}';
    }

    public String getFirmId()
    {
        return firmId;
    }

    public void setFirmId(String firmId)
    {
        this.firmId = firmId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Integer getNumberOfParameters()
    {
        return numberOfParameters;
    }

    public void setNumberOfParameters(Integer numberOfParameters)
    {
        this.numberOfParameters = numberOfParameters;
    }

    public Integer getNumberOfSecurities()
    {
        return numberOfSecurities;
    }

    public void setNumberOfSecurities(Integer numberOfSecurities)
    {
        this.numberOfSecurities = numberOfSecurities;
    }
}
