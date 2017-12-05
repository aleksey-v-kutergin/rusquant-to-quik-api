package ru.rusquant.api;

import ru.rusquant.channel.ChannelType;
import ru.rusquant.client.Client;
import ru.rusquant.messages.request.factory.RequestFactory;
import ru.rusquant.messages.request.body.RequestSubject;

import java.io.IOException;

/**
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public abstract class Connector
{
	/** Client instance **/
	protected final Client client = new Client(ChannelType.PIPE);

	/** Fabric to produce requests **/
	protected final RequestFactory requestFactory = new RequestFactory();
	protected String connectErrorMessage;

	public boolean connect()
	{
		connectErrorMessage = null;
		client.connect();
		if(client.isConnected())
		{
			client.run();
			return true;
		}
		else
		{
			connectErrorMessage = client.getError();
			return false;
		}
	}

	public void disconnect()
	{
		try
		{
			if(client.isConnected())
			{
				client.stop();
				if(client.isStopped())
				{
					client.disconnect();
					requestFactory.resetRequestIdSequence();
				}
			}
		}
		catch (InterruptedException | IOException e)
		{
			connectErrorMessage = e.getMessage();
		}
	}

	public String getConnectErrorMessage()
	{
		return connectErrorMessage;
	}

	public Float getAvgShippingDurationOfRequest(RequestSubject subject)
	{
		return client.getAvgShippingDurationOfRequest(subject);
	}

	public Float getAvgDurationOfRequestProcessing(RequestSubject subject)
	{
		return client.getAvgDurationOfRequestProcessing(subject);
	}

	public Float getAvgShippingDurationOfResponse(RequestSubject subject)
	{
		return client.getAvgShippingDurationOfResponse(subject);
	}

	public Float getAvgRequestResponseLatency(RequestSubject subject)
	{
		return client.getAvgRequestResponseLatency(subject);
	}
}
