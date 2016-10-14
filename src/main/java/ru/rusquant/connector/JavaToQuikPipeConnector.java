package ru.rusquant.connector;

import ru.rusquant.client.WindowsNamedPipeClient;
import ru.rusquant.data.quik.Echo;
import ru.rusquant.data.quik.ErrorObject;
import ru.rusquant.data.quik.QuikDataObject;
import ru.rusquant.messages.factory.RequestBodyFactory;
import ru.rusquant.messages.factory.RequestFactory;
import ru.rusquant.messages.request.Request;
import ru.rusquant.messages.request.RequestSubject;
import ru.rusquant.messages.request.RequestType;
import ru.rusquant.messages.request.body.RequestBody;
import ru.rusquant.messages.response.Response;
import ru.rusquant.messages.response.body.EchoResponseBody;

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
		System.out.println();
		System.out.println("Making connection to server");

		client.connect();
		if(client.isConnected())
		{
			System.out.println("Connect success!!!");

			System.out.println();
			System.out.println("Starting main client loop");
			isConnected = Boolean.TRUE;
			client.run();
		}
	}


	@Override
	public void disconnect()
	{
		try
		{
			if(client.isConnected())
			{
				System.out.println("Stopping client!");

				client.stop();
				if(client.isStopped())
				{
					client.disconnect();
					isConnected = Boolean.FALSE;
					requestFactory.resetRequestIdSequence();
				}
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}




	/** ========================================================= API implementation ========================================================= **/

	public QuikDataObject getEcho(String message)
	{
		QuikDataObject result = new ErrorObject();
		if(message == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for message parameter. Message cannot be null!"); }
		if(message.isEmpty()) { ( (ErrorObject) result ).setErrorMessage("Receive null for message parameter. Message cannot be null!"); }

		String[] args = {message};
		RequestBody echoBody =  requestBodyFactory.createRecuestBody(RequestSubject.ECHO, args);
		Request echoRequest = requestFactory.createRequest(RequestType.GET, RequestSubject.ECHO, echoBody);
		try
		{
			client.postRequest(echoRequest);
			Response response = client.getResponse(echoRequest);
			if(response != null)
			{
				if( "SUCCESS".equals(response.getStatus()) )
				{
					String echoAnswer = ( (EchoResponseBody) response.getBody() ).getEchoAnswer();
					result = new Echo(echoAnswer);
				}
				else
				{
					( (ErrorObject) result ).setErrorMessage("Call of getEcho() with message: " + message + " failed with error: " + response.getError());
				}
			}
		}
		catch (InterruptedException e)
		{
			( (ErrorObject) result ).setErrorMessage(e.getMessage());
		}
		return result;
	}
}
