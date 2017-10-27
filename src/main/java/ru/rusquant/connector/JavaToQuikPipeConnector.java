package ru.rusquant.connector;

import ru.rusquant.client.WindowsNamedPipeClient;
import ru.rusquant.data.quik.ErrorObject;
import ru.rusquant.data.quik.QuikDataObject;
import ru.rusquant.data.quik.Transaction;
import ru.rusquant.messages.factory.RequestBodyFactory;
import ru.rusquant.messages.factory.RequestFactory;
import ru.rusquant.messages.request.Request;
import ru.rusquant.messages.request.RequestSubject;
import ru.rusquant.messages.request.RequestType;
import ru.rusquant.messages.request.body.RequestBody;
import ru.rusquant.messages.response.Response;
import ru.rusquant.messages.response.ResponseStatus;
import ru.rusquant.messages.response.body.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	@Override
	public QuikDataObject isConnected()
	{
		QuikDataObject result = new ErrorObject();
		List<Object> args = new ArrayList<>();

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


	@Override
	public QuikDataObject getEcho(String message)
	{
		QuikDataObject result = new ErrorObject();
		if(message == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for message parameter. Message cannot be null!"); }
		else if(message.isEmpty()) { ( (ErrorObject) result ).setErrorMessage("Receive empty message parameter. Message cannot be empty!"); }

		List<String> args = new ArrayList<>();
		args.add(message);

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


	@Override
	public QuikDataObject getInfoParam(String paramName)
	{
		QuikDataObject result = new ErrorObject();
		if(paramName == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for paramName parameter. Name of info parameter cannot be null!"); }
		else if(paramName.isEmpty()) { ( (ErrorObject) result ).setErrorMessage("Receive empty paramName parameter. Name of info parameter cannot be empty string!"); }

		List<String> args = new ArrayList<>();
		args.add(paramName.toUpperCase());

		RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.INFO_PARAMETER, args);
		if(paramName.isEmpty()) { ( (ErrorObject) result ).setErrorMessage("Receive invalid paramName parameter!"); }
		Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.INFO_PARAMETER, body);
		try
		{
			client.postRequest(request);
			Response response = client.getResponse();
			if(response != null)
			{
				if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
				{
					result = ( (InfoParameterResponseBody) response.getBody() ).getInfoParameter();
				}
				else
				{
					( (ErrorObject) result ).setErrorMessage("Call of getInfoParam() with paramName: " + paramName.toUpperCase() + " failed with error: " + response.getError());
				}
			}
		}
		catch (Exception e)
		{
			( (ErrorObject) result ).setErrorMessage(e.getMessage());
		}
		return result;
	}


	@Override
	public QuikDataObject sendTransaction(Transaction transaction)
	{
		QuikDataObject result = new ErrorObject();
		if(transaction == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for transaction parameter. Transaction cannot be null!"); }

		List<Transaction> args = new ArrayList<>();
		args.add(transaction);

		RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.TRANSACTION, args);
		if(body == null) { ( (ErrorObject) result ).setErrorMessage("Receive invalid transaction!"); }
		Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.TRANSACTION, body);
		try
		{
			client.postRequest(request);
			Response response = client.getResponse();
			if(response != null)
			{
				if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
				{
					result = ( (TransactionResponseBody) response.getBody() ).getTransactionReplay();
				}
				else
				{
					( (ErrorObject) result ).setErrorMessage("Call of sendTransaction() with transaction: " + transaction.toString() + " failed with error: " + response.getError());
				}
			}
		}
		catch (Exception e)
		{
			( (ErrorObject) result ).setErrorMessage(e.getMessage());
		}
		return result;
	}



	@Override
	public QuikDataObject getOrder(Long orderNumber)
	{
		QuikDataObject result = new ErrorObject();
		if(orderNumber == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for order number parameter. Order number cannot be null!"); }

		List<Long> args = new ArrayList<>();
		args.add(orderNumber);

		RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.ORDER, args);
		if(body == null) { ( (ErrorObject) result ).setErrorMessage("Receive invalid order number!"); }
		Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.ORDER, body);
		try
		{
			client.postRequest(request);
			Response response = client.getResponse();
			if(response != null)
			{
				if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
				{
					result = ( (OrderResponseBody) response.getBody() ).getOrder();
				}
				else
				{
					( (ErrorObject) result ).setErrorMessage("Call of getOrder() with order number: " + orderNumber + " failed with error: " + response.getError());
				}
			}
		}
		catch (Exception e)
		{
			( (ErrorObject) result ).setErrorMessage(e.getMessage());
		}
		return result;
	}


	@Override
	public QuikDataObject getTrades(Long orderNumber)
	{
		QuikDataObject result = new ErrorObject();
		if(orderNumber == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for order number parameter. Order number cannot be null!"); }

		List<Long> args = new ArrayList<>();
		args.add(orderNumber);

		RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.TRADE, args);
		if(body == null) { ( (ErrorObject) result ).setErrorMessage("Receive invalid order number!"); }
		Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.TRADE, body);
		try
		{
			client.postRequest(request);
			Response response = client.getResponse();
			if(response != null)
			{
				if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
				{
					result = ( (TradesResponseBody) response.getBody() ).getTradesDataFrame();
				}
				else
				{
					( (ErrorObject) result ).setErrorMessage("Call of getTrades() with order number: " + orderNumber + " failed with error: " + response.getError());
				}
			}
		}
		catch (Exception e)
		{
			( (ErrorObject) result ).setErrorMessage(e.getMessage());
		}
		return result;
	}
}
