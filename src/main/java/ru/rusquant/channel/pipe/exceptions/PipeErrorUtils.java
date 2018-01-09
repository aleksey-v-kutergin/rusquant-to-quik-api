package ru.rusquant.channel.pipe.exceptions;

import com.sun.jna.platform.win32.Kernel32;

import java.io.IOException;

/**
 * Class contains logic, which return error message or exception object for pipe's error code
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class PipeErrorUtils {

    public static String getErrorMessageByErrorCode(PipeErrorSource source, String pipeName, int errorCode) {
        switch (source) {
            case CONNECT: {
                return getConnectErrorMessage(pipeName, errorCode);
            }
            case READ: {
                return getReadIOErrorMessage(pipeName, errorCode);
            }
            case WRITE: {
                return getWriteIOErrorMessage(pipeName, errorCode);
            }
            case DISCONNECT: {
                return getDisconnectErrorMessage(pipeName, errorCode);
            }
            default: {
                return getDefaultErrorMessage(pipeName, errorCode);
            }
        }
    }


    public static IOException getErrorByErrorCode(PipeErrorSource source, String pipeName, int errorCode) {
        String message;
        switch (source) {
            case CONNECT: {
                message = getConnectErrorMessage(pipeName, errorCode);
                return getExceptionObjByErrorCode(errorCode, message);
            }
            case READ: {
                message = getReadIOErrorMessage(pipeName, errorCode);
                return getExceptionObjByErrorCode(errorCode, message);
            }
            case WRITE: {
                message = getWriteIOErrorMessage(pipeName, errorCode);
                return getExceptionObjByErrorCode(errorCode, message);
            }
            case DISCONNECT: {
                message = getDisconnectErrorMessage(pipeName, errorCode);
                return getExceptionObjByErrorCode(errorCode, message);
            }
            default: {
                message = getDefaultErrorMessage(pipeName, errorCode);
                return getExceptionObjByErrorCode(errorCode, message);
            }
        }
    }


    private static String getConnectErrorMessage(String pipeName, int errorCode) {
        String message = "\nError! Failed to connect to windows named pipe server: " + pipeName + ". Exceeded retry count! \nError for last try: ";
        message += "\n\tFailed to connect to windows named pipe: " + pipeName + " with " + getReasonByErrorCode(errorCode) + "!";
        return message;
    }


    private static String getWriteIOErrorMessage(String pipeName, int errorCode) {
        return "\nError! Write IO operation with windows named pipe: " + pipeName + " failed with " + getReasonByErrorCode(errorCode) + "!";
    }


    private static String getReadIOErrorMessage(String pipeName, int errorCode) {
        return "\nError! Read IO operation with windows named pipe: " + pipeName + " failed with " + getReasonByErrorCode(errorCode) + "!";
    }


    private static String getDisconnectErrorMessage(String pipeName, int errorCode) {
        String message = "\nError! Failed to close handle of the pipe: " + pipeName + "with " + getReasonByErrorCode(errorCode) + "!";
        message += "\n\tPleas stop server script and reload the QUIK terminal!";
        return message;
    }


    private static String getDefaultErrorMessage(String pipeName, int errorCode) {
        return "\nError! IO operation on windows named pipe: " + pipeName + " failed with " + getReasonByErrorCode(errorCode) + "!";
    }


    private static String getReasonByErrorCode(int errorCode) {
        String reason;
        switch (errorCode) {
            case Kernel32.ERROR_FILE_NOT_FOUND: {
                reason = "ERROR_FILE_NOT_FOUND. \n\tThis error means that server not yet opened channel. \n\tPleas, make sure that server script is running under QUIK terminal";
                return reason;
            }
            case Kernel32.ERROR_PIPE_BUSY: {
                reason = "ERROR_PIPE_BUSY. \n\tThis error means that pipe descriptor wasn't cleaned in proper way at server side after last client's session.";
                reason += "\n\tIn order to fix this error, pleas, restart the QUIK terminal and run launch server script again";
                return reason;
            }
            case Kernel32.ERROR_BROKEN_PIPE: {
                reason = " ERROR_BROKEN_PIPE. \n\tThis error occurs when client makes attempt to execute some IO operation on client-side end of the pipe while server already closed server-side end";
                reason += "\n\tPleas, make sure that server script is running under QUIK terminal!";
                return reason;
            }
            case Kernel32.ERROR_NO_DATA: {
                reason = " ERROR_NO_DATA. \n\tThis error occurs when client makes attempt to execute read o write IO operation on client-side end of the pipe while server already closed server-side end ";
                reason += "\n\tPleas, make sure that server script is running under QUIK terminal!";
                return reason;
            }
            default: {
                return "code: " + errorCode;
            }
        }
    }


    private static IOException getExceptionObjByErrorCode(int errorCode, String message) {
        switch (errorCode) {
            case Kernel32.ERROR_FILE_NOT_FOUND: {
                return new IOException(message);
            }
            case Kernel32.ERROR_PIPE_BUSY: {
                return new IOException(message);
            }
            case Kernel32.ERROR_BROKEN_PIPE: {
                return new ServerCloseConnectionException(message);
            }
            case Kernel32.ERROR_NO_DATA: {
                return new ServerCloseConnectionException(message);
            }
            default: {
                return new IOException(message);
            }
        }
    }

}
