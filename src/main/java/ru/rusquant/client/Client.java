package ru.rusquant.client;

/**
 *    Base class for all rusquant to QUIK clients
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public abstract class Client
{
	protected Boolean isConnected = Boolean.FALSE;

	public Boolean isConnected()
	{
		return isConnected;
	}

	public abstract void connect();

	public abstract void disconnect();

	public abstract void run();

	public abstract void stop();
}
