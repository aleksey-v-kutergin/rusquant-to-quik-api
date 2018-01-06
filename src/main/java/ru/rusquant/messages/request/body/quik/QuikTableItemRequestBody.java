package ru.rusquant.messages.request.body.quik;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.types.QuikTableType;
import ru.rusquant.messages.request.body.RequestBody;

/**
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
@JsonAutoDetect
public class QuikTableItemRequestBody extends RequestBody {
    private QuikTableType tableType;
    private Integer itemIndex;

    public QuikTableItemRequestBody() {

    }

    public QuikTableItemRequestBody(QuikTableType tableType, Integer itemIndex) {
        this.tableType = tableType;
        this.itemIndex = itemIndex;
    }


    public QuikTableType getTableType() {
        return tableType;
    }

    public void setTableType(QuikTableType tableType) {
        this.tableType = tableType;
    }

    public Integer getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(Integer itemIndex) {
        this.itemIndex = itemIndex;
    }
}
