package ru.rusquant.data.quik.dataframe;

/**
 * Data frame for trades
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class TradesDataFrame extends QuikDataFrame {

    @Override
    public String toString() {
        String str = "Trades: ";
        for (int i = 0; i < records.size(); i++) {
            str += "\n\t" + records.get(i);
        }
        return str;
    }
}
