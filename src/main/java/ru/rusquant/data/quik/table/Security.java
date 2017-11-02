package ru.rusquant.data.quik.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.QuikDataObject;

/**
 * Java implementation for securities table item.
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Security extends QuikDataObject
{
    /** Code of asset **/
    @JsonProperty("code")
    private String code;

    /** Name of asset **/
    @JsonProperty("name")
    private String name;

    /** Short name of asset **/
    @JsonProperty("short_name")
    private String shortName;

    /** Asset class code **/
    @JsonProperty("class_code")
    private String classCode;

    /** Asset class name **/
    @JsonProperty("class_name")
    private String className;

    /** Nominal value of the security **/
    @JsonProperty("face_value")
    private Double faceValue;

    /** Currency of the nominal **/
    @JsonProperty("face_unit")
    private String faceUnit;

    /** Accuracy (the number of significant digits after the decimal point) **/
    @JsonProperty("scale")
    private Integer scale;

    /** Maturity date **/
    @JsonProperty("mat_date")
    private Long maturityDate;

    /** Size of lot for asset **/
    @JsonProperty("lot_size")
    private Double lotSize;

    /** ISIN **/
    @JsonProperty("isin_code")
    private String isinCode;

    /** Minimal price step for asset **/
    @JsonProperty("min_price_step")
    private Double minPriceStep;

    public Security()
    {

    }

    @Override
    public String toString()
    {
        return "Security: {" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", classCode='" + classCode + '\'' +
                ", className='" + className + '\'' +
                ", faceValue=" + faceValue +
                ", faceUnit='" + faceUnit + '\'' +
                ", scale=" + scale +
                ", maturityDate=" + maturityDate +
                ", lotSize=" + lotSize +
                ", isinCode='" + isinCode + '\'' +
                ", minPriceStep=" + minPriceStep +
                '}';
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public String getClassCode()
    {
        return classCode;
    }

    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public Double getFaceValue()
    {
        return faceValue;
    }

    public void setFaceValue(Double faceValue)
    {
        this.faceValue = faceValue;
    }

    public String getFaceUnit()
    {
        return faceUnit;
    }

    public void setFaceUnit(String faceUnit)
    {
        this.faceUnit = faceUnit;
    }

    public Integer getScale()
    {
        return scale;
    }

    public void setScale(Integer scale)
    {
        this.scale = scale;
    }

    public Long getMaturityDate()
    {
        return maturityDate;
    }

    public void setMaturityDate(Long maturityDate)
    {
        this.maturityDate = maturityDate;
    }

    public Double getLotSize()
    {
        return lotSize;
    }

    public void setLotSize(Double lotSize)
    {
        this.lotSize = lotSize;
    }

    public String getIsinCode()
    {
        return isinCode;
    }

    public void setIsinCode(String isinCode)
    {
        this.isinCode = isinCode;
    }

    public Double getMinPriceStep()
    {
        return minPriceStep;
    }

    public void setMinPriceStep(Double minPriceStep)
    {
        this.minPriceStep = minPriceStep;
    }
}
