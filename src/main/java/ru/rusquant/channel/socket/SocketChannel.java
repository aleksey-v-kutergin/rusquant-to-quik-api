package ru.rusquant.channel.socket;

import ru.rusquant.channel.DataTransferChannel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Implementation of the data transfer mechanism through sockets.
 * Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 * Company: Rusquant
 */
public class SocketChannel extends DataTransferChannel
{
    private String host;
    private Integer port;
    private Socket socket;

    // Streams to read and write
    private DataOutputStream out;
    private DataInputStream in;


    public SocketChannel(String host, Integer port)
    {
        this.host = host;
        this.port = port;
    }

    @Override
    public Boolean open()
    {
        try
        {
            this.socket = new Socket(this.host, this.port);
            this.out = new DataOutputStream(this.socket.getOutputStream());
            this.in = new DataInputStream(this.socket.getInputStream());
            return Boolean.TRUE;
        }
        catch (IOException e)
        {
            this.errorMessage = e.getMessage();
            return Boolean.FALSE;
        }
    }

    @Override
    public void close() throws IOException
    {
        this.out.close();
        this.in.close();
        this.socket.close();
    }

    @Override
    public void writeMessage(String message) throws IOException
    {
        String line = message + "\n";
        out.write(line.getBytes("UTF-8"));
    }

    @Override
    public String readMessage() throws IOException, InterruptedException
    {
        // Read IO from socket is not blocking
        // Need wait for data
        int attempts = 0;
        while (in.available() == 0 && attempts <= 1000000)
        {
            attempts++;
            Thread.sleep(5);
        }
        int count = in.available();
        if (count > 0)
        {
            byte[] bytes = new byte[count];
            in.read(bytes);
            return new String(bytes, "UTF-8");
        }
        return null;
    }
}
