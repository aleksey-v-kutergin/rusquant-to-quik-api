package ru.rusquant.client;

import ru.rusquant.connection.pipe.WindowsNamedPipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *    Class implements all client-side logic of Windows Named Pipe server-client process
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class WindowsNamedPipeClient extends Client
{
	private WindowsNamedPipe pipe= new WindowsNamedPipe("\\\\.\\pipe\\RusquantToQuikPipe");

	private int clientMode = 1;


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
		//runManualTest();
		runEchoTest();
	}



	private void runManualTest()
	{
		System.out.println("Running manual test");
		System.out.println("Start client to server data exchange!");

		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			Boolean isStoped = Boolean.FALSE;
			while(!isStoped)
			{
				if(clientMode == 1)
				{
					System.out.println("Enter the message to server:");
					String message = reader.readLine();
					if(message != null && !message.isEmpty())
					{
						if(message.equals("stop"))
						{
							writeMessage("CLIENT_OFF");
							break;
						}

						writeMessage(message);
						clientMode = 2;
					}
				}
				else if(clientMode == 2)
				{
					String response = readMessage();
					if(response != null && !response.isEmpty())
					{
						System.out.println("SERVER RESPONSE: " + response);
						clientMode = 1;
					}
				}
			}

			reader.close();
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}

		System.out.println("Stop client to server data exchange!");

	}


	private void runEchoTest()
	{
		Queue<String> messages = new LinkedBlockingQueue<String>(30);
		for (int i = 1; i <= 30; i++)
		{
			messages.add("RUSQUANT CLIENT TEST REQUEST NUMBER: " + i);
		}

		System.out.println("Running echo test");
		System.out.println("Start client to server data exchange!");

		Boolean isStoped = Boolean.FALSE;
		int counter = 0;
		int countOfMessages = messages.size();

		while(!isStoped)
		{
			if(clientMode == 1)
			{
				String message = messages.poll();
				if(message != null && !message.isEmpty())
				{
					writeMessage(message);
					clientMode = 2;
				}
			}
			else if(clientMode == 2)
			{
				String response = readMessage();
				if(response != null && !response.isEmpty())
				{
					System.out.println("SERVER RESPONSE: " + response);
					clientMode = 1;
					counter++;
				}
			}

			if(counter == countOfMessages)
			{
				writeMessage("CLIENT_OFF");
				isStoped = Boolean.TRUE;
			}
		}
		System.out.println("Stop client to server data exchange!");
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


	@Override
	public String readMessage()
	{
		try
		{
			return pipe.read();
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}


	@Override
	public void writeMessage(String request)
	{
		try
		{
			pipe.write(request);
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
