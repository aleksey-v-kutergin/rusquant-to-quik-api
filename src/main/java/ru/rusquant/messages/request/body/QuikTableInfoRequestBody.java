package ru.rusquant.messages.request.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.types.QuikTableType;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class QuikTableInfoRequestBody extends RequestBody
{
    private QuikTableType tableType;

    public QuikTableInfoRequestBody()
    {

    }

    public QuikTableInfoRequestBody(QuikTableType tableType)
    {
        this.tableType = tableType;
    }

    public QuikTableType getTableType()
    {
        return tableType;
    }

    public void setTableType(QuikTableType tableType)
    {
        this.tableType = tableType;
    }
}
