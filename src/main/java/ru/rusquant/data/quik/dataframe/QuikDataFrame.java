package ru.rusquant.data.quik.dataframe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.rusquant.data.quik.QuikDataObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for collection of trades.
 * Why one needs wrapp a collection of objects:
 * 1. Standardization of the API functions. API member returns data object or error object.
 * 2. If one considers signature like List<Trade> getTrades(...) for API member, then one faces with question:
 * How to return error from such function??? Return list of errors with size 1.
 * Not a good idea, i guess.
 * <p>
 * In the case of wrapper we steel return a QuikDataObject from such API function.
 * <p>
 * 3. We are targeting to integration with R. In the case of the collections of such custom data objects
 * R hardly relies on DataFrames and it would be convenient to have a class, that can represent a list of objects
 * as DataFrame
 * <p>
 * At the R side TradesDataFrame could be converted to corresponding data frame.
 * <p>
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuikDataFrame extends QuikDataObject {

    @JsonDeserialize(as = ArrayList.class)
    protected List<? extends QuikDataObject> records = new ArrayList<>();

    public List<? extends QuikDataObject> getRecords() {
        return records;
    }

    public void setRecords(List<? extends QuikDataObject> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        String str = "Records: ";
        for (int i = 0; i < records.size(); i++) {
            str += "\n\t" + records.get(i);
        }
        return str;
    }

    /**
     * Data frame functionality
     **/
    public List<String> getColumnNames() {
        return new ArrayList<>();
    }

    public List<String> getRowNames() {
        return new ArrayList<>();
    }
}
