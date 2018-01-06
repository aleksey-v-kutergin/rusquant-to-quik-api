package ru.rusquant.data.quik.dataframe;

/**
 * Data frame for OHLC prices
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class OhlcDataFrame extends QuikDataFrame {
    @Override
    public String toString() {
        String str = "OHLC: ";
        for (int i = 0; i < records.size(); i++) {
            str += "\n\t" + records.get(i);
        }
        return str;
    }
}
