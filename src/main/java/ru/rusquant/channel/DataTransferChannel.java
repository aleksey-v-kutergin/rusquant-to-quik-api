package ru.rusquant.channel;

import java.io.IOException;

/**
 *    Base class for channels to transfer messages from client to server.
 *    This is more flexible when the client and the server do not depend on the details of the data transfer mechanism,
 *    but only work with the abstract channel.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public abstract class DataTransferChannel
{
    /** Number of attempts to open the channel. If exceeded, a connection error occurred. **/
    protected static final int OPEN_CHANNEL_RETRY_COUNT = 20;

    /** To report about specific data transfer error **/
    protected String errorMessage;

    public String getErrorMessage()
    {
        return errorMessage;
    }

    /** Try to opens channel and returns true if success. **/
    public abstract Boolean open();

    /** Method for close channel. **/
    public abstract void close() throws IOException;

    /** Writes data to chanel. **/
    public abstract void writeMessage(String message) throws IOException;

    /** Reads data from channel. **/
    public abstract String readMessage() throws IOException;
}
