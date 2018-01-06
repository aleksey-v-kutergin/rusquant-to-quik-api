package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.dataframe.QuikDataFrame;
import ru.rusquant.messages.response.body.ResponseBody;

/**
 * Response contains a collection of rows for requested table.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class QuikTableItemsResponseBody extends ResponseBody {
    private QuikDataFrame items;

    public QuikTableItemsResponseBody() {

    }

    public QuikTableItemsResponseBody(QuikDataFrame items) {
        this.items = items;
    }

    public QuikDataFrame getItems() {
        return items;
    }

    public void setItems(QuikDataFrame items) {
        this.items = items;
    }
}
