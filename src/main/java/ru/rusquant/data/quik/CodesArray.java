package ru.rusquant.data.quik;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object for array of string codes of security classes, securities, etc.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class CodesArray extends QuikDataObject {
    @JsonProperty("separator")
    private String separator;

    @JsonProperty("codesString")
    private String codesString;

    public CodesArray() {

    }

    public CodesArray(String codesString) {
        this.codesString = codesString;
    }

    @Override
    public String toString() {
        return "CodesArray: {" +
                "separator='" + separator + '\'' +
                ", codesString='" + codesString + '\'' +
                '}';
    }

    public String[] getCodes() {
        return codesString.split(separator);
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getCodesString() {
        return codesString;
    }

    public void setCodesString(String codesString) {
        this.codesString = codesString;
    }

    /**
     * Created by kutergin on 27.11.2017.
     */
    public static class LongResult extends QuikDataObject {
    }
}
