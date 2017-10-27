package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.QuikTableInfo;

public class QuikTableInfoResponseBody extends ResponseBody
{
    private QuikTableInfo tableInfo;

    public QuikTableInfoResponseBody()
    {

    }

    public QuikTableInfoResponseBody(QuikTableInfo tableInfo)
    {
        this.tableInfo = tableInfo;
    }

    public QuikTableInfo getTableInfo()
    {
        return tableInfo;
    }

    public void setTableInfo(QuikTableInfo tableInfo)
    {
        this.tableInfo = tableInfo;
    }
}
