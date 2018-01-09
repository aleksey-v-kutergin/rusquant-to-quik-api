package ru.rusquant.messages.request.body.quik;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.types.QuikTableType;
import ru.rusquant.messages.request.body.RequestBody;

/**
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonAutoDetect
public class QuikTableItemsRequestBody extends RequestBody {

    private QuikTableType tableType;
    private Integer firstIndex;
    private Integer lastIndex;

    public QuikTableItemsRequestBody() {

    }

    public QuikTableItemsRequestBody(QuikTableType tableType, Integer firstIndex, Integer lastIndex) {
        this.tableType = tableType;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
    }

    public QuikTableType getTableType() {
        return tableType;
    }

    public void setTableType(QuikTableType tableType) {
        this.tableType = tableType;
    }

    public Integer getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(Integer firstIndex) {
        this.firstIndex = firstIndex;
    }

    public Integer getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(Integer lastIndex) {
        this.lastIndex = lastIndex;
    }
}
