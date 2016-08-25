package ru.rusquant.client;

import ru.rusquant.connection.pipe.WindowsNamedPipe;

import java.io.IOException;

/**
 *    Class implements all client-side logic of Windows Named Pipe server-client process
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class WindowsNamedPipeClient extends Client
{
	private WindowsNamedPipe pipe= new WindowsNamedPipe("\\\\.\\pipe\\RusquantToQuikPipe");


	public WindowsNamedPipeClient()
	{

	}


	@Override
	public void connect()
	{
		this.isConnected = pipe.connect();
	}


	@Override
	public void disconnect()
	{
		try
		{
			pipe.disconnect();
			this.isConnected = Boolean.FALSE;
			System.out.println("Disconnected from pipe!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void run()
	{
		System.out.println("Start reading data from server!");

		String response;
		while( ( response = pipe.read() ) != null )
		{
			System.out.println("SERVER RESPONSE: " + response);
		}

		System.out.println("Stop reading data from server!");
	}


	@Override
	public void stop()
	{
		disconnect();
	}

	public static void main(String[] args)
	{
		System.out.println("Java WNP Client to Lua WNP Server hello world program ");
		WindowsNamedPipeClient client = new WindowsNamedPipeClient();

		System.out.println("Making connection to server");
		client.connect();
		if(client.isConnected())
		{
			System.out.println("Connect success!!!");
			System.out.println("Starting main client loop");
			client.run();

			System.out.println("Stopping client!");
			client.stop();
		}
	}
}
