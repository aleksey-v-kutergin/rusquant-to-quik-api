package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.QuikDataObject;

public class QuikTableItemResponseBody extends ResponseBody
{
    private QuikDataObject item;

    public QuikTableItemResponseBody()
    {

    }

    public QuikTableItemResponseBody(QuikDataObject item)
    {
        this.item = item;
    }

    public QuikDataObject getItem()
    {
        return item;
    }

    public void setItem(QuikDataObject item)
    {
        this.item = item;
    }
}
