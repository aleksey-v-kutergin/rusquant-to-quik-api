package ru.rusquant.connector;

import ru.rusquant.client.WindowsNamedPipeClient;
import ru.rusquant.client.exceptions.MessageGrinderEmergencyAbortException;
import ru.rusquant.data.quik.ErrorObject;
import ru.rusquant.data.quik.QuikDataObject;
import ru.rusquant.messages.factory.RequestBodyFactory;
import ru.rusquant.messages.factory.RequestFactory;
import ru.rusquant.messages.request.Request;
import ru.rusquant.messages.request.RequestSubject;
import ru.rusquant.messages.request.RequestType;
import ru.rusquant.messages.request.body.RequestBody;
import ru.rusquant.messages.response.Response;
import ru.rusquant.messages.response.ResponseStatus;
import ru.rusquant.messages.response.body.ConnectionSateResponseBody;
import ru.rusquant.messages.response.body.EchoResponseBody;

import java.io.IOException;

/**
 *    Implementation of java to quik connector based on windows named pipe client.
 *    Connector acts as controller with respect to windows named pipe client.
 *
 *    Usage:
 *    1. Create instance of the connector
 *    2. Call connect() in order to make connection to windows named pipe server at terminal side.
 *
 *    	 There are only two outcomes for connect() call:
 *    	 2.1. connect() results with success (Successfully connects to pipe server)
 *    	 2.2. connect() fails and report to user about error
 *    	 2.3. there is no third result
 *
 *    3. Use public api methods for applied purposes
 *    4. Call disconnect() to break connection and free resources.
 *
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class JavaToQuikPipeConnector extends JavaToQuikConnector
{
	/**
	 *    Client instance to control
	 **/
	private WindowsNamedPipeClient client = new WindowsNamedPipeClient();


	/**
	 *    Fabrics for producing requests
	 **/
	private RequestBodyFactory requestBodyFactory = new RequestBodyFactory();
	private RequestFactory requestFactory = new RequestFactory();



	public JavaToQuikPipeConnector()
	{

	}



	@Override
	public void connect()
	{
		connectorError = null;
		client.connect();

		if(client.isConnected())
		{
			isConnectedToServer = Boolean.TRUE;
			client.run();
		}
		else
		{
			connectorError = new ErrorObject(client.getPipeError());
			isConnectedToServer = Boolean.FALSE;
		}
	}


	@Override
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
					isConnectedToServer = Boolean.FALSE;
					requestFactory.resetRequestIdSequence();
				}
			}
		}
		catch (InterruptedException e)
		{
			connectorError = new ErrorObject(e.getMessage());
		}
		catch (IOException e)
		{
			connectorError = new ErrorObject(e.getMessage());
		}
	}


	@Override
	public Float getAvgShippingDurationOfRequest(RequestSubject subject)
	{
		return client.getAvgShippingDurationOfRequest(subject);
	}

	@Override
	public Float getAvgDurationOfRequestProcessing(RequestSubject subject)
	{
		return client.getAvgDurationOfRequestProcessing(subject);
	}

	@Override
	public Float getAvgShippingDurationOfResponse(RequestSubject subject)
	{
		return client.getAvgShippingDurationOfResponse(subject);
	}

	@Override
	public Float getAvgRequestResponseLatency(RequestSubject subject)
	{
		return client.getAvgRequestResponseLatency(subject);
	}



	/** ========================================================= API implementation ========================================================= **/

	public QuikDataObject getEcho(String message)
	{
		QuikDataObject result = new ErrorObject();
		if(message == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for message parameter. Message cannot be null!"); }
		else if(message.isEmpty()) { ( (ErrorObject) result ).setErrorMessage("Receive null for message parameter. Message cannot be null!"); }

		String[] args = {message};
		RequestBody echoBody =  requestBodyFactory.createRequestBody(RequestSubject.ECHO, args);
		Request echoRequest = requestFactory.createRequest(RequestType.GET, RequestSubject.ECHO, echoBody);
		try
		{
			client.postRequest(echoRequest);
			Response response = client.getResponse();
			if(response != null)
			{
				if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
				{
					result = ( (EchoResponseBody) response.getBody() ).getEcho();
				}
				else
				{
					( (ErrorObject) result ).setErrorMessage("Call of getEcho() with message: " + message + " failed with error: " + response.getError());
				}
			}
		}
		catch (Exception e)
		{
			( (ErrorObject) result ).setErrorMessage(e.getMessage());
		}
		return result;
	}


	public QuikDataObject isConnected()
	{
		QuikDataObject result = new ErrorObject();
		String[] args = {};

		RequestBody body = requestBodyFactory.createRequestBody(RequestSubject.CONNECTION_SATE, args);
		Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.CONNECTION_SATE, body);
		try
		{
			client.postRequest(request);
			Response response = client.getResponse();
			if(response != null)
			{
				if(ResponseStatus.SUCCESS.toString().equals(response.getStatus()))
				{
					return ( (ConnectionSateResponseBody) response.getBody() ).getConnectionState();
				}
				else
				{
					( (ErrorObject) result ).setErrorMessage("Call of isConnected() failed with error: " + response.getError());
				}
			}
		}
		catch (Exception e)
		{
			( (ErrorObject) result ).setErrorMessage(e.getMessage());
		}
		return null;
	}
}
