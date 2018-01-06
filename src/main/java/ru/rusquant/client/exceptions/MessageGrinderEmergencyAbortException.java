package ru.rusquant.client.exceptions;


import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Throws if data exchange between client and sever fails.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class MessageGrinderEmergencyAbortException extends RuntimeException {
    public MessageGrinderEmergencyAbortException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return (ExceptionUtils.getRootCause(this)).getMessage();
    }
}
