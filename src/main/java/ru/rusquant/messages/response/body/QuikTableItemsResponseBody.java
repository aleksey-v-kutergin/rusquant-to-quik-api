package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.dataframe.QuikDataFrame;

/**
 *   Response contains a collection of rows for requested table.
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class QuikTableItemsResponseBody extends ResponseBody
{
    private QuikDataFrame items;

    public QuikTableItemsResponseBody()
    {

    }

    public QuikTableItemsResponseBody(QuikDataFrame items)
    {
        this.items = items;
    }

    public QuikDataFrame getItems()
    {
        return items;
    }

    public void setItems(QuikDataFrame items)
    {
        this.items = items;
    }
}
