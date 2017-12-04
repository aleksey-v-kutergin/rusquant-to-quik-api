package ru.rusquant.channel.socket;

import ru.rusquant.channel.DataTransferChannel;

import java.io.IOException;

/**
 *    Implementation of the data transfer mechanism through sockets.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class SocketChannel extends DataTransferChannel
{
    @Override
    public Boolean open()
    {
        return null;
    }

    @Override
    public void close() throws IOException
    {

    }

    @Override
    public void writeMessage(String message) throws IOException
    {

    }

    @Override
    public String readMessage() throws IOException
    {
        return null;
    }
}
