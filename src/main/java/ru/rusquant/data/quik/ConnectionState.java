package ru.rusquant.data.quik;

/**
 *    Connection state contains information about sate of connection between QUIK terminal and QUIK-server.
 *    1 - means that terminal is connected to QUIK-server (server of broker)
 *    1 - means that terminal isn't connected to QUIK-server (server of broker)
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class ConnectionState extends QuikDataObject
{
	private Integer isConnected;

	public ConnectionState()
	{

	}

	public ConnectionState(Integer isConnected)
	{
		this.isConnected = isConnected;
	}

	public Integer isConnected()
	{
		return isConnected;
	}

	public void setIsConnected(Integer isConnected)
	{
		this.isConnected = isConnected;
	}
}
