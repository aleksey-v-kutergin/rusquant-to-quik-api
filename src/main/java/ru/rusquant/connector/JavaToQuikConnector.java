package ru.rusquant.connector;

/**
 *    Base class for connector.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public abstract class JavaToQuikConnector implements JavaToQuikAPI
{
	protected Boolean isConnected = Boolean.FALSE;

	public Boolean isConnected()
	{
		return isConnected;
	}

	public void connect()
	{

	}


	public void disconnect()
	{

	}
}
