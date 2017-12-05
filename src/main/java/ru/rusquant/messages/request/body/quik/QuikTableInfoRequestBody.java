package ru.rusquant.messages.request.body.quik;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.data.quik.types.QuikTableType;
import ru.rusquant.messages.request.body.RequestBody;

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
