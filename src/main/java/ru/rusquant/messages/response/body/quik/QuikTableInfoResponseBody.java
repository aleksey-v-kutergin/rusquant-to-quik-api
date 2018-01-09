package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.QuikTableInfo;
import ru.rusquant.messages.response.body.ResponseBody;

public class QuikTableInfoResponseBody extends ResponseBody {

    private QuikTableInfo tableInfo;

    public QuikTableInfoResponseBody() {

    }

    public QuikTableInfoResponseBody(QuikTableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public QuikTableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(QuikTableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
}
