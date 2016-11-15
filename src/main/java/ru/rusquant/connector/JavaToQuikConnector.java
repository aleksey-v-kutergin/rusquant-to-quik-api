package ru.rusquant.connector;

import ru.rusquant.data.quik.ErrorObject;
import ru.rusquant.messages.request.RequestSubject;

/**
 *    Base class for connector.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public abstract class JavaToQuikConnector implements JavaToQuikAPI
{
	protected Boolean isConnected = Boolean.FALSE;

	protected ErrorObject connectorError;

	public Boolean isConnected()
	{
		return isConnected;
	}

	public ErrorObject getConnectorError()
	{
		return connectorError;
	}

	public abstract void connect();

	public abstract void disconnect();

	public abstract Float getAvgShippingDurationOfRequest(RequestSubject subject);

	public abstract Float getAvgDurationOfRequestProcessing(RequestSubject subject);

	public abstract Float getAvgShippingDurationOfResponse(RequestSubject subject);

	public abstract Float getAvgRequestResponseLatency(RequestSubject subject);
}
