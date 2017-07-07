package ru.rusquant.data.quik.types;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *    List of possible statuses of the transaction from QLUA specification.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public enum TransactionStatus
{
    /** "0" - the transaction was sent to the server **/
    SEND_TO_SERVER,

    /** "1" - the transaction was received on the QUIK server from the client **/
    RECEIVED,

    /** "2" is an error when transferring a transaction to the trading system. Since there is no connection to the gateway of the Moscow Stock Exchange, the transaction is not re-sent **/
    TRANSFER_ERROR,

    /** "3" - transaction completed **/
    COMPLETED,

    /** "4" - the transaction was not executed by the trading system. A more detailed description of the error is reflected in the "Message" field **/
    NOT_EXECUTED,

    /** "5" - the transaction failed to verify the QUIK server by any criteria. For example, checking for the user's rights to send a transaction of this type **/
    VERIFICATION_ERROR,

    /** "6" - the transaction did not pass the QUIK server limits check **/
    SERVER_LIMIT_CHECK_ERROR,

    /** "10" - the transaction is not supported by the trading system **/
    NOT_SUPPORTED,

    /** "11" - the transaction did not pass the validation of the electronic digital signature **/
    VALIDATION_DIGITAL_SIGNATURE_ERROR,

    /** "12" - it was not possible to wait for the response to the transaction, tk. The timeout of the wait has expired. It can occur when you submit transactions from QPILE **/
    TIMEOUT_EXPIRED,

    /** "13" - the transaction was rejected, as its execution could lead to a cross-transaction (ie a transaction with the same client account) **/
    REJECTED;


    /**
     *    QUICK do not understand strings. It understand 0, 1, 2... so on
     *    One need to serialize enum to int values and deserialize back to enum constants.
     *    This can be done with annotated method, defined below.
     *    During the serialization \ deserialization this jackson invokes this method for each enum constants.
     *    Deserialization works because the set of enum's constants is constant. So, int value can be mapped
     *    to corresponding enum constant.
     **/
    @JsonValue
    public int toValue()
    {
        return ordinal();
    }
}
