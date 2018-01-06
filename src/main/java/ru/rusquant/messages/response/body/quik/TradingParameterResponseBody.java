package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.TradingParameter;
import ru.rusquant.messages.response.body.ResponseBody;

/**
 * Response contains a requested trading tradingParameter data.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class TradingParameterResponseBody extends ResponseBody {
    private TradingParameter tradingParameter;

    public TradingParameterResponseBody() {

    }

    public TradingParameterResponseBody(TradingParameter tradingParameter) {
        this.tradingParameter = tradingParameter;
    }

    public TradingParameter getTradingParameter() {
        return tradingParameter;
    }

    public void setTradingParameter(TradingParameter tradingParameter) {
        this.tradingParameter = tradingParameter;
    }
}
