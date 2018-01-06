package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rusquant.data.quik.types.ParameterDataType;

/**
 * Java implementation for the parameter of the exchange information from the table "Current trading".
 * Data types for fields according to qlua docs.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradingParameter extends QuikDataObject {
    /**
     * The data type of the parameter used in the "Current Bidding" table.
     * The possible values are:
     *        "1" - DOUBLE,
     *        "2" - LONG,
     *        "3" - CHAR,
     *        "4" is an enumerated type,
     *        "5" is the time,
     *        "6" is the date
     **/
    @JsonProperty("param_type")
    private ParameterDataType dataType;

    /**
     * The value of the parameter. For param_type = 3, the parameter value is "0", in other cases - the numeric representation.
     * For enumerated types, the value is equal to the order value of the enumeration
     **/
    @JsonProperty("param_value")
    private String value;

    /**
     * The string value of the parameter is similar to its representation in the table.
     * In the string representation, the separators of the digits, the separators of the integer and fractional part are taken into account.
     * For enumerated types, the corresponding string values are displayed
     **/
    @JsonProperty("param_image")
    private String image;

    /**
     * The result of the operation. The possible values are:
     * "0" is an error;
     * "1" - parameter found
     **/
    @JsonProperty("result")
    private String result;

    public TradingParameter() {

    }

    @Override
    public String toString() {
        return "TradingParameter: {" +
                "dataType=" + dataType +
                ", value='" + value + '\'' +
                ", image='" + image + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public ParameterDataType getDataType() {
        return dataType;
    }

    public void setDataType(ParameterDataType dataType) {
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
