package ru.rusquant.connector;

import ru.rusquant.client.WindowsNamedPipeClient;
import ru.rusquant.data.quik.*;
import ru.rusquant.data.quik.types.DSParameterType;
import ru.rusquant.data.quik.types.TimeScale;
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

    //TODO: Сделать следующую вещь:
    //TODO: Создаем класс исключение вроде InvalidArgumentException
    //TODO: Всю валидацию аргументов и сообщения об ощибках выносим в соответсвующие методы валицдации фабрикм
    //TODO: В реализации методов API просто формируем список параметров и ловим исключение  InvalidArgumentException
    //TODO: чтобы уведомить пользователя о невалидности параметров



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


	@Override
	public QuikDataObject getNumberOfRows(String tableName)
	{
		QuikDataObject result = new ErrorObject();
		if(tableName == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for table name parameter. Name of table cannot be null!"); }

		List<String> args = new ArrayList<>();
		args.add(tableName);

		RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.TABLE_INFO, args);
		if(body == null) { ( (ErrorObject) result ).setErrorMessage("Table with name: " + tableName + " not yet supported!"); }
		Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.TABLE_INFO, body);
		try
		{
			client.postRequest(request);
			Response response = client.getResponse();
			if(response != null)
			{
				if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
				{
					result = ( (QuikTableInfoResponseBody) response.getBody() ).getTableInfo();
				}
				else
				{
					( (ErrorObject) result ).setErrorMessage("Call of getNumberOfRows with table name: " + tableName + " failed with error: " + response.getError());
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
    public QuikDataObject getItem(String tableName, Integer index)
    {
        QuikDataObject result = new ErrorObject();
        if(tableName == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for table name parameter. Name of table cannot be null!"); }

        List<Object> args = new ArrayList<>();
        args.add(tableName);
        args.add(index);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.TABLE_ITEM, args);
        if(body == null) { ( (ErrorObject) result ).setErrorMessage("Table with name: " + tableName + " not yet supported!"); }
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.TABLE_ITEM, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (QuikTableItemResponseBody) response.getBody() ).getItem();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
    public QuikDataObject getItems(String tableName)
    {
        QuikDataObject result = new ErrorObject();
        if(tableName == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for table name parameter. Name of table cannot be null!"); }

        List<String> args = new ArrayList<>();
        args.add(tableName);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.TABLE_ITEMS, args);
        if(body == null) { ( (ErrorObject) result ).setErrorMessage("Table with name: " + tableName + " not yet supported!"); }
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.TABLE_ITEMS, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (QuikTableItemsResponseBody) response.getBody() ).getItems();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
    public QuikDataObject getParamEx(String classCode, String securityCode, String paramName)
    {
        return getParamExByVersion("EX1", classCode, securityCode, paramName);
    }

    @Override
    public QuikDataObject getParamEx2(String classCode, String securityCode, String paramName)
    {
        return getParamExByVersion("EX2", classCode, securityCode, paramName);
    }


    @Override
    public QuikDataObject subscribeParameter(String classCode, String securityCode, String paramName)
    {
        QuikDataObject result = new ErrorObject();
        if(classCode == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for table name parameter. Name of table cannot be null!"); }

        List<String> args = new ArrayList<>();
        args.add(classCode);
        args.add(securityCode);
        args.add(paramName);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.SUBSCRIBE_TRADING_PARAMETER, args);
        if(body == null) { ( (ErrorObject) result ).setErrorMessage("Table with name: " + classCode + " not yet supported!"); }
        Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.SUBSCRIBE_TRADING_PARAMETER, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (SubscribeParameterResponseBody) response.getBody() ).getDescriptor();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
    public QuikDataObject unsubscribeParameter(ParameterDescriptor descriptor)
    {
        QuikDataObject result = new ErrorObject();
        List<Object> args = new ArrayList<>();
        args.add(descriptor);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.UNSUBSCRIBE_TRADING_PARAMETER, args);
        Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.UNSUBSCRIBE_TRADING_PARAMETER, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (UnsubscribeParameterResponseBody) response.getBody() ).getResult();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
                }
            }
        }
        catch (Exception e)
        {
            ( (ErrorObject) result ).setErrorMessage(e.getMessage());
        }
        return result;
    }


    private QuikDataObject getParamExByVersion(String version, String classCode, String securityCode, String paramName)
    {
        QuikDataObject result = new ErrorObject();
        if(classCode == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for table name parameter. Name of table cannot be null!"); }

        List<String> args = new ArrayList<>();
        args.add(classCode);
        args.add(securityCode);
        args.add(paramName);
        args.add(version);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.TRADING_PARAMETER, args);
        if(body == null) { ( (ErrorObject) result ).setErrorMessage("Table with name: " + classCode + " not yet supported!"); }
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.TRADING_PARAMETER, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (TradingParameterResponseBody) response.getBody() ).getTradingParameter();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
    public QuikDataObject searchItems(String tableName, Long startIndex, Long endIndex, String params)
    {
        return new ErrorObject("Not supported operation! Function not yet implemented!");
    }

    @Override
    public QuikDataObject getClassesList()
    {
        QuikDataObject result = new ErrorObject();
        List<Object> args = new ArrayList<>();

        RequestBody body = requestBodyFactory.createRequestBody(RequestSubject.CLASSES_LIST, args);
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.CLASSES_LIST, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if(ResponseStatus.SUCCESS.toString().equals(response.getStatus()))
                {
                    result =  ( (ClassesListResponseBody) response.getBody() ).getCodes();
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
        return result;
    }

    @Override
    public QuikDataObject getClassInfo(String classCode)
    {
        QuikDataObject result = new ErrorObject();
        if(classCode == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for table name parameter. Name of table cannot be null!"); }

        List<String> args = new ArrayList<>();
        args.add(classCode);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.CLASS_INFO, args);
        if(body == null) { ( (ErrorObject) result ).setErrorMessage("Table with name: " + classCode + " not yet supported!"); }
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.CLASS_INFO, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (SecurityClassInfoResponseBody) response.getBody() ).getSecurityClass();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage("Call of getClassInfo with table name: " + classCode + " failed with error: " + response.getError());
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
    public QuikDataObject getClassSecurities(String classCode)
    {
        QuikDataObject result = new ErrorObject();
        if(classCode == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for table name parameter. Name of table cannot be null!"); }

        List<String> args = new ArrayList<>();
        args.add(classCode);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.CLASS_SECURITIES, args);
        if(body == null) { ( (ErrorObject) result ).setErrorMessage("Table with name: " + classCode + " not yet supported!"); }
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.CLASS_SECURITIES, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (ClassSecuritiesResponseBody) response.getBody() ).getCodes();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage("Call of getClassInfo with table name: " + classCode + " failed with error: " + response.getError());
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
    public QuikDataObject getMoney(String clientCode, String firmId, String tag, String currencyCode)
    {
        return new ErrorObject("Not supported operation! Function not yet implemented!");
    }

    @Override
    public QuikDataObject getMoneyEx(String clientCode, String firmId, String tag, String currencyCode, Integer limitKind)
    {
        return new ErrorObject("Not supported operation! Function not yet implemented!");
    }

    @Override
    public QuikDataObject getDepo(String clientCode, String firmId, String tag, String account)
    {
        return new ErrorObject("Not supported operation! Function not yet implemented!");
    }

    @Override
    public QuikDataObject getDepoEx(String clientCode, String firmId, String tag, String account, Integer limitKind)
    {
        return new ErrorObject("Not supported operation! Function not yet implemented!");
    }

    @Override
    public QuikDataObject getFuturesLimit(String firmId, String accountId, Integer limitType, String currencyCode)
    {
        return new ErrorObject("Not supported operation! Function not yet implemented!");
    }

    @Override
    public QuikDataObject getFuturesHolding(String firmId, String accountId, String securityCode, Integer posType)
    {
        return new ErrorObject("Not supported operation! Function not yet implemented!");
    }

    @Override
    public QuikDataObject getSecurityInfo(String classCode, String securityCode)
    {
        QuikDataObject result = new ErrorObject();
        if(classCode == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for table name parameter. Name of table cannot be null!"); }

        List<String> args = new ArrayList<>();
        args.add(classCode);
        args.add(securityCode);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.SECURITY_INFO, args);
        if(body == null) { ( (ErrorObject) result ).setErrorMessage("Table with name: " + classCode + " not yet supported!"); }
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.SECURITY_INFO, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (SecurityInfoResponseBody) response.getBody() ).getSecurity();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage("Call of getNumberOfRows with table name: " + classCode + " failed with error: " + response.getError());
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
    public QuikDataObject getTradeDate()
    {
        QuikDataObject result = new ErrorObject();
        List<Object> args = new ArrayList<>();

        RequestBody body = requestBodyFactory.createRequestBody(RequestSubject.TRADE_DATE, args);
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.TRADE_DATE, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if(ResponseStatus.SUCCESS.toString().equals(response.getStatus()))
                {
                    result =  ( (TradeDateResponseBody) response.getBody() ).getTradeDate();
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
        return result;
    }

    @Override
    public QuikDataObject getQuoteLevel2(String classCode, String securityCode)
    {
        QuikDataObject result = new ErrorObject();
        if(classCode == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for table name parameter. Name of table cannot be null!"); }

        List<String> args = new ArrayList<>();
        args.add(classCode);
        args.add(securityCode);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.QUOTES, args);
        if(body == null) { ( (ErrorObject) result ).setErrorMessage("Table with name: " + classCode + " not yet supported!"); }
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.QUOTES, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (QuotesResponseBody) response.getBody() ).getOrderBook();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
    public QuikDataObject subscribeQuotes(String classCode, String securityCode)
    {
        QuikDataObject result = new ErrorObject();
        if(classCode == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for table name parameter. Name of table cannot be null!"); }

        List<String> args = new ArrayList<>();
        args.add(classCode);
        args.add(securityCode);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.SUBSCRIBE_QUOTES, args);
        if(body == null) { ( (ErrorObject) result ).setErrorMessage("Table with name: " + classCode + " not yet supported!"); }
        Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.SUBSCRIBE_QUOTES, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (SubscribeQuotesResponseBody) response.getBody() ).getDescriptor();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
    public QuikDataObject unsubscribeQuotes(QuotesDescriptor descriptor)
    {
        QuikDataObject result = new ErrorObject();
        List<Object> args = new ArrayList<>();
        args.add(descriptor);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.UNSUBSCRIBE_QUOTES, args);
        Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.UNSUBSCRIBE_QUOTES, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (UnsubscribeQuotesResponseBody) response.getBody() ).getResult();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
    public QuikDataObject isSubscribedToQuotes(QuotesDescriptor descriptor)
    {
        QuikDataObject result = new ErrorObject();
        List<Object> args = new ArrayList<>();
        args.add(descriptor);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.IS_SUBSCRIBED_QUOTES, args);
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.IS_SUBSCRIBED_QUOTES, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (IsSubscribeQuotesResponseBody) response.getBody() ).getResult();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
    public QuikDataObject getMaxCountOfLotsInOrder(String classCode, String securityCode, String clientCode, String account, Double price, Boolean isBuy, Boolean isMarket)
    {
        QuikDataObject result = new ErrorObject();
        if(classCode == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for table name parameter. Name of table cannot be null!"); }

        List<Object> args = new ArrayList<>();
        args.add(classCode);
        args.add(securityCode);
        args.add(clientCode);
        args.add(account);
        args.add(price);
        args.add(isBuy);
        args.add(isMarket);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.MAX_LOT_COUNT, args);
        if(body == null) { ( (ErrorObject) result ).setErrorMessage("Table with name: " + classCode + " not yet supported!"); }
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.MAX_LOT_COUNT, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (MaxCountOfLotsResponseBody) response.getBody() ).getCountOfLots();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage("Call of getNumberOfRows with table name: " + classCode + " failed with error: " + response.getError());
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
    public QuikDataObject getPortfolioInfo(String firmId, String clientCode)
    {
        return new ErrorObject("Not supported operation! Function not yet implemented!");
    }

    @Override
    public QuikDataObject getPortfolioInfoEx(String firmId, String clientCode, Integer limitKind)
    {
        return new ErrorObject("Not supported operation! Function not yet implemented!");
    }

    @Override
    public QuikDataObject getBuySellInfo(String firmId, String clientCode, String classCode, String securityCode, Double price)
    {
        return new ErrorObject("Not supported operation! Function not yet implemented!");
    }

    @Override
    public QuikDataObject getBuySellInfoEx(String firmId, String clientCode, String classCode, String securityCode, Double price)
    {
        return new ErrorObject("Not supported operation! Function not yet implemented!");
    }

    @Override
    public QuikDataObject createDataSource(String classCode, String securityCode, String interval)
    {
        return createDataSource(classCode, securityCode, interval, null);
    }

    @Override
    public QuikDataObject createDataSource(String classCode, String securityCode, String interval, String parameter)
    {
        QuikDataObject result = new ErrorObject();
        if(classCode == null) { ( (ErrorObject) result ).setErrorMessage("Receive null for table name parameter. Name of table cannot be null!"); }

        List<String> args = new ArrayList<>();
        args.add(classCode);
        args.add(securityCode);
        args.add(interval);
        if(parameter != null) {
            args.add(parameter);
        }

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.CREATE_DATASOURCE, args);
        if(body == null) { ( (ErrorObject) result ).setErrorMessage("Table with name: " + classCode + " not yet supported!"); }
        Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.CREATE_DATASOURCE, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (CreateDatasourceResponseBody) response.getBody() ).getDescriptor();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
    public QuikDataObject closeDatasource(DatasourceDescriptor datasource)
    {
        QuikDataObject result = new ErrorObject();
        List<Object> args = new ArrayList<>();
        args.add(datasource);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.CLOSE_DATASOURCE, args);
        Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.CLOSE_DATASOURCE, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (CloseDatasourceResponseBody) response.getBody() ).getResult();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
    public QuikDataObject getDatasourceSize(DatasourceDescriptor datasource)
    {
        QuikDataObject result = new ErrorObject();
        List<Object> args = new ArrayList<>();
        args.add(datasource);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.DATASOURCE_SIZE, args);
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.DATASOURCE_SIZE, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (DatasourceSizeResponseBody) response.getBody() ).getResult();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
    public QuikDataObject getOHLCPrice(DatasourceDescriptor datasource, Long index)
    {
        QuikDataObject result = new ErrorObject();
        List<Object> args = new ArrayList<>();
        args.add(datasource);
        args.add(index);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.SINGLE_CANDLE, args);
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.SINGLE_CANDLE, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (SingleCandleResponseBody) response.getBody() ).getCandle();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
    public QuikDataObject getOHLCPrices(DatasourceDescriptor datasource)
    {
        QuikDataObject result = new ErrorObject();
        List<Object> args = new ArrayList<>();
        args.add(datasource);

        RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.ALL_CANDLES, args);
        Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.ALL_CANDLES, body);
        try
        {
            client.postRequest(request);
            Response response = client.getResponse();
            if(response != null)
            {
                if( ResponseStatus.SUCCESS.toString().equals(response.getStatus()) )
                {
                    result = ( (AllCandlesResponseBody) response.getBody() ).getOhlcDataFrame();
                }
                else
                {
                    ( (ErrorObject) result ).setErrorMessage(response.getError());
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
