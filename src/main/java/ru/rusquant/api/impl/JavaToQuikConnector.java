package ru.rusquant.api.impl;

import ru.rusquant.api.Connector;
import ru.rusquant.api.JavaToQuikAPI;
import ru.rusquant.data.quik.*;
import ru.rusquant.data.quik.descriptor.DatasourceDescriptor;
import ru.rusquant.data.quik.descriptor.ParameterDescriptor;
import ru.rusquant.data.quik.descriptor.QuotesDescriptor;
import ru.rusquant.messages.request.factory.RequestBodyFactory;
import ru.rusquant.messages.request.Request;
import ru.rusquant.messages.request.body.RequestSubject;
import ru.rusquant.messages.request.RequestType;
import ru.rusquant.messages.request.body.RequestBody;
import ru.rusquant.messages.response.Response;
import ru.rusquant.messages.response.ResponseStatus;
import ru.rusquant.messages.response.body.quik.*;

import java.util.ArrayList;
import java.util.List;

/**
 *    Implementation of java to quik api.
 *    Usage:
 *    1. Create instance of the connector
 *    2. Call connect() in order to make connection to lua server, running under Quik workstation.
 *
 *    	 There are only two outcomes for connect() call:
 *    	 2.1. connect() results with success (Successfully connects to lua server)
 *    	 2.2. connect() fails and report to user about error
 *    	 2.3. there is no third result
 *
 *    3. Use public api methods for applied purposes
 *    4. Call disconnect() to break connection and free resources.
 *
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class JavaToQuikConnector extends Connector implements JavaToQuikAPI
{
	/** Fabric to produce bodies for request **/
	private final RequestBodyFactory requestBodyFactory = new RequestBodyFactory();

	/** Return connect error as Quik data object **/
	public ErrorObject getConnectError()
	{
		return new ErrorObject(this.connectErrorMessage);
	}


	/** ========================================================= API implementation ========================================================= **/

	@Override
	public QuikDataObject isConnected()
	{
		ErrorObject error = new ErrorObject();
		try
		{
            List<Object> args = new ArrayList<>();
            RequestBody body = requestBodyFactory.createRequestBody(RequestSubject.CONNECTION_SATE, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.CONNECTION_SATE, body);

			client.postRequest(request);
			Response response = client.getResponse();
            if(ResponseStatus.SUCCESS.equals(response.getStatus()))
            {
                return ( (ConnectionSateResponseBody) response.getBody() ).getConnectionState();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
		}
		catch (Exception e)
		{
            error.setErrorMessage(e.getMessage());
		}
		return error;
	}


	@Override
	public QuikDataObject getEcho(String message)
	{
		ErrorObject error = new ErrorObject();
		try
		{
            List<String> args = new ArrayList<>();
            args.add(message);

            RequestBody echoBody =  requestBodyFactory.createRequestBody(RequestSubject.ECHO, args);
            Request echoRequest = requestFactory.createRequest(RequestType.GET, RequestSubject.ECHO, echoBody);

            client.postRequest(echoRequest);
			Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (EchoResponseBody) response.getBody() ).getEcho();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
		}
		catch (Exception e)
		{
            error.setErrorMessage(e.getMessage());
		}
		return error;
	}


	@Override
	public QuikDataObject getInfoParam(String paramName)
	{
		ErrorObject error = new ErrorObject();
		try
		{
            List<String> args = new ArrayList<>();
            args.add(paramName);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.INFO_PARAMETER, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.INFO_PARAMETER, body);

			client.postRequest(request);
			Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (InfoParameterResponseBody) response.getBody() ).getInfoParameter();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
		}
		catch (Exception e)
		{
            error.setErrorMessage(e.getMessage());
		}
		return error;
	}


	@Override
	public QuikDataObject sendTransaction(Transaction transaction)
	{
		ErrorObject error = new ErrorObject();
		try
		{
            List<Transaction> args = new ArrayList<>();
            args.add(transaction);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.TRANSACTION, args);
            Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.TRANSACTION, body);

			client.postRequest(request);
			Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (TransactionResponseBody) response.getBody() ).getTransactionReplay();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
		}
		catch (Exception e)
		{
            error.setErrorMessage(e.getMessage());
		}
		return error;
	}



	@Override
	public QuikDataObject getOrder(Long orderNumber)
	{
		ErrorObject error = new ErrorObject();
		try
		{
            List<Long> args = new ArrayList<>();
            args.add(orderNumber);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.ORDER, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.ORDER, body);

			client.postRequest(request);
			Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (OrderResponseBody) response.getBody() ).getOrder();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
		}
		catch (Exception e)
		{
            error.setErrorMessage(e.getMessage());
		}
		return error;
	}


	@Override
	public QuikDataObject getTrades(Long orderNumber)
	{
		ErrorObject error = new ErrorObject();
		try
		{
            List<Long> args = new ArrayList<>();
            args.add(orderNumber);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.TRADE, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.TRADE, body);

			client.postRequest(request);
			Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (TradesResponseBody) response.getBody() ).getTradesDataFrame();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
		}
		catch (Exception e)
		{
            error.setErrorMessage(e.getMessage());
		}
		return error;
	}


	@Override
	public QuikDataObject getNumberOfRows(String tableName)
	{
		ErrorObject error = new ErrorObject();
		try
		{
            List<String> args = new ArrayList<>();
            args.add(tableName);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.TABLE_INFO, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.TABLE_INFO, body);

			client.postRequest(request);
			Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (QuikTableInfoResponseBody) response.getBody() ).getTableInfo();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
		}
		catch (Exception e)
		{
            error.setErrorMessage(e.getMessage());
		}
		return error;
	}


    @Override
    public QuikDataObject getItem(String tableName, Integer index)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<Object> args = new ArrayList<>();
            args.add(tableName);
            args.add(index);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.TABLE_ITEM, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.TABLE_ITEM, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (QuikTableItemResponseBody) response.getBody() ).getItem();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }


    @Override
    public QuikDataObject getItems(String tableName)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<String> args = new ArrayList<>();
            args.add(tableName);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.TABLE_ITEMS, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.TABLE_ITEMS, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return  ( (QuikTableItemsResponseBody) response.getBody() ).getItems();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
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
        ErrorObject error = new ErrorObject();
        try
        {
            List<String> args = new ArrayList<>();
            args.add(classCode);
            args.add(securityCode);
            args.add(paramName);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.SUBSCRIBE_TRADING_PARAMETER, args);
            Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.SUBSCRIBE_TRADING_PARAMETER, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (SubscribeParameterResponseBody) response.getBody() ).getDescriptor();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }


    @Override
    public QuikDataObject unsubscribeParameter(ParameterDescriptor descriptor)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<Object> args = new ArrayList<>();
            args.add(descriptor);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.UNSUBSCRIBE_TRADING_PARAMETER, args);
            Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.UNSUBSCRIBE_TRADING_PARAMETER, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (UnsubscribeParameterResponseBody) response.getBody() ).getResult();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }


    private QuikDataObject getParamExByVersion(String version, String classCode, String securityCode, String paramName)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<String> args = new ArrayList<>();
            args.add(classCode);
            args.add(securityCode);
            args.add(paramName);
            args.add(version);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.TRADING_PARAMETER, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.TRADING_PARAMETER, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (TradingParameterResponseBody) response.getBody() ).getTradingParameter();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject searchItems(String tableName, Long startIndex, Long endIndex, String params)
    {
        return new ErrorObject("Not supported operation! Function not yet implemented!");
    }

    @Override
    public QuikDataObject getClassesList()
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<Object> args = new ArrayList<>();
            RequestBody body = requestBodyFactory.createRequestBody(RequestSubject.CLASSES_LIST, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.CLASSES_LIST, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if(ResponseStatus.SUCCESS.equals(response.getStatus()))
            {
                return ( (ClassesListResponseBody) response.getBody() ).getCodes();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject getClassInfo(String classCode)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<String> args = new ArrayList<>();
            args.add(classCode);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.CLASS_INFO, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.CLASS_INFO, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (SecurityClassInfoResponseBody) response.getBody() ).getSecurityClass();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject getClassSecurities(String classCode)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<String> args = new ArrayList<>();
            args.add(classCode);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.CLASS_SECURITIES, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.CLASS_SECURITIES, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (ClassSecuritiesResponseBody) response.getBody() ).getCodes();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
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
        ErrorObject error = new ErrorObject();
        try
        {
            List<String> args = new ArrayList<>();
            args.add(classCode);
            args.add(securityCode);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.SECURITY_INFO, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.SECURITY_INFO, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (SecurityInfoResponseBody) response.getBody() ).getSecurity();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject getTradeDate()
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<Object> args = new ArrayList<>();
            RequestBody body = requestBodyFactory.createRequestBody(RequestSubject.TRADE_DATE, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.TRADE_DATE, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if(ResponseStatus.SUCCESS.equals(response.getStatus()))
            {
                return ( (TradeDateResponseBody) response.getBody() ).getTradeDate();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject getQuoteLevel2(String classCode, String securityCode)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<String> args = new ArrayList<>();
            args.add(classCode);
            args.add(securityCode);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.QUOTES, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.QUOTES, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (QuotesResponseBody) response.getBody() ).getOrderBook();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject subscribeQuotes(String classCode, String securityCode)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<String> args = new ArrayList<>();
            args.add(classCode);
            args.add(securityCode);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.SUBSCRIBE_QUOTES, args);
            Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.SUBSCRIBE_QUOTES, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (SubscribeQuotesResponseBody) response.getBody() ).getDescriptor();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject unsubscribeQuotes(QuotesDescriptor descriptor)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<Object> args = new ArrayList<>();
            args.add(descriptor);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.UNSUBSCRIBE_QUOTES, args);
            Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.UNSUBSCRIBE_QUOTES, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (UnsubscribeQuotesResponseBody) response.getBody() ).getResult();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject isSubscribedToQuotes(QuotesDescriptor descriptor)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<Object> args = new ArrayList<>();
            args.add(descriptor);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.IS_SUBSCRIBED_QUOTES, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.IS_SUBSCRIBED_QUOTES, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (IsSubscribeQuotesResponseBody) response.getBody() ).getResult();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject getMaxCountOfLotsInOrder(String classCode, String securityCode, String clientCode, String account, Double price, Boolean isBuy, Boolean isMarket)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<Object> args = new ArrayList<>();
            args.add(classCode);
            args.add(securityCode);
            args.add(clientCode);
            args.add(account);
            args.add(price);
            args.add(isBuy);
            args.add(isMarket);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.MAX_LOT_COUNT, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.MAX_LOT_COUNT, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (MaxCountOfLotsResponseBody) response.getBody() ).getCountOfLots();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
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
        ErrorObject error = new ErrorObject();
        try
        {
            List<String> args = new ArrayList<>();
            args.add(classCode);
            args.add(securityCode);
            args.add(interval);
            if(parameter != null) { args.add(parameter); }

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.CREATE_DATASOURCE, args);
            Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.CREATE_DATASOURCE, body);

            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (CreateDatasourceResponseBody) response.getBody() ).getDescriptor();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject closeDatasource(DatasourceDescriptor datasource)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<Object> args = new ArrayList<>();
            args.add(datasource);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.CLOSE_DATASOURCE, args);
            Request request = requestFactory.createRequest(RequestType.POST, RequestSubject.CLOSE_DATASOURCE, body);
            
            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (CloseDatasourceResponseBody) response.getBody() ).getResult();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject getDatasourceSize(DatasourceDescriptor datasource)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<Object> args = new ArrayList<>();
            args.add(datasource);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.DATASOURCE_SIZE, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.DATASOURCE_SIZE, body);
            
            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (DatasourceSizeResponseBody) response.getBody() ).getResult();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject getOHLCPrice(DatasourceDescriptor datasource, Long index)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<Object> args = new ArrayList<>();
            args.add(datasource);
            args.add(index);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.SINGLE_CANDLE, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.SINGLE_CANDLE, body);
            
            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (SingleCandleResponseBody) response.getBody() ).getCandle();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }

    @Override
    public QuikDataObject getOHLCPrices(DatasourceDescriptor datasource)
    {
        ErrorObject error = new ErrorObject();
        try
        {
            List<Object> args = new ArrayList<>();
            args.add(datasource);

            RequestBody body =  requestBodyFactory.createRequestBody(RequestSubject.ALL_CANDLES, args);
            Request request = requestFactory.createRequest(RequestType.GET, RequestSubject.ALL_CANDLES, body);
            
            client.postRequest(request);
            Response response = client.getResponse();
            if( ResponseStatus.SUCCESS.equals(response.getStatus()) )
            {
                return ( (AllCandlesResponseBody) response.getBody() ).getOhlcDataFrame();
            }
            else
            {
                error.setErrorMessage(response.getError());
            }
        }
        catch (Exception e)
        {
            error.setErrorMessage(e.getMessage());
        }
        return error;
    }
}
