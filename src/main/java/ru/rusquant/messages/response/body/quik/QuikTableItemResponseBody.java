package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.QuikDataObject;
import ru.rusquant.messages.response.body.ResponseBody;

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
