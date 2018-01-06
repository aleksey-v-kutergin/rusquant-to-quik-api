package ru.rusquant.messages.response.body.quik;

import ru.rusquant.data.quik.dataframe.OhlcDataFrame;
import ru.rusquant.messages.response.body.ResponseBody;

/**
 * Response contains a collection of all candles in datasource.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class AllCandlesResponseBody extends ResponseBody {

    private OhlcDataFrame ohlcDataFrame;

    public AllCandlesResponseBody() {

    }

    public AllCandlesResponseBody(OhlcDataFrame ohlcDataFrame) {
        this.ohlcDataFrame = ohlcDataFrame;
    }

    public OhlcDataFrame getOhlcDataFrame() {
        return ohlcDataFrame;
    }

    public void setOhlcDataFrame(OhlcDataFrame ohlcDataFrame) {
        this.ohlcDataFrame = ohlcDataFrame;
    }
}
