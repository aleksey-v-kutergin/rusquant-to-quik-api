package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.Transaction;
import ru.rusquant.messages.response.body.ResponseBody;

/**
 *   Single transaction response
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class TransactionResponseBody extends ResponseBody
{
    private Transaction transactionReplay;

    public TransactionResponseBody()
    {

    }

    public TransactionResponseBody(Transaction transactionReplay)
    {
        this.transactionReplay = transactionReplay;
    }

    public Transaction getTransactionReplay()
    {
        return transactionReplay;
    }

    public void setTransactionReplay(Transaction transactionReplay)
    {
        this.transactionReplay = transactionReplay;
    }
}
