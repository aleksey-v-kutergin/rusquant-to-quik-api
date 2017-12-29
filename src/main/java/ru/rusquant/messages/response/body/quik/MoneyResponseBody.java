package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.table.Money;
import ru.rusquant.messages.response.body.ResponseBody;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class MoneyResponseBody extends ResponseBody
{
    private Money money;

    public MoneyResponseBody()
    {

    }

    public MoneyResponseBody(Money money)
    {
        this.money = money;
    }

    public Money getMoney()
    {
        return money;
    }

    public void setMoney(Money money)
    {
        this.money = money;
    }
}
