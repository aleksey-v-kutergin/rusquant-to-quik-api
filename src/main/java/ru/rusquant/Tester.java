package ru.rusquant;

import ru.rusquant.client.WindowsNamedPipeClient;
import ru.rusquant.messages.MessagesManager;

/**
 * Just for testing
 * Created by kutergin on 30.09.2016.
 */
public class Tester
{
	public static void main(String[] args)
	{
		WindowsNamedPipeClient client = new WindowsNamedPipeClient();
		client.fillMessages("RUSQUANT TEST MESSAGE", 2);

	}

}
