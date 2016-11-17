package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.ConnectionState;

public class ConnectionSateResponseBody extends ResponseBody
{
	private ConnectionState connectionState;

	public ConnectionSateResponseBody()
	{

	}

	public ConnectionSateResponseBody(ConnectionState connectionState)
	{
		this.connectionState = connectionState;
	}

	public ConnectionState getConnectionState()
	{
		return connectionState;
	}
}
