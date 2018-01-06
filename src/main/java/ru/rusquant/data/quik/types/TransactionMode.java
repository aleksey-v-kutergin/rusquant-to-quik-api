package ru.rusquant.data.quik.types;

/**
 * Executions modes for transactions
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public enum TransactionMode {
    /**
     * The call wrapper-function for sendTransaction at server-side will not wait for callbacks execution.
     **/
    IMMEDIATE,

    /**
     * The call wrapper-function for sendTransaction at server-side will wait for OnTransReplay() callback execution.
     **/
    ON_TRANS_REPLAY,

    /**
     * The call wrapper-function for sendTransaction at server-side will wait for OnTrade() callback execution.
     **/
    ON_TRADE,

    /**
     * The call wrapper-function for sendTransaction at server-side will wait for OnOrder() callback execution.
     **/
    ON_ORDER
}
