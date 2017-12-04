package ru.rusquant.messages.factory;

import ru.rusquant.data.quik.DatasourceDescriptor;
import ru.rusquant.data.quik.ParameterDescriptor;
import ru.rusquant.data.quik.QuotesDescriptor;
import ru.rusquant.data.quik.Transaction;
import ru.rusquant.data.quik.types.*;
import ru.rusquant.messages.request.body.RequestSubject;
import ru.rusquant.messages.request.body.RequestBody;
import ru.rusquant.messages.request.body.quik.*;

import java.util.List;

/**
 *   Factory for request's body
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class RequestBodyFactory
{
	public RequestBody createRequestBody(RequestSubject subject, List<?> args)
	{
		switch (subject)
		{
			case ECHO:
			{
				if(isValidEchoArgs(args))
				{
					return new EchoRequestBody(args.get(0).toString());
				}
				else { return null; }
			}
			case CONNECTION_SATE:
			{
				return new ConnectionStateRequestBody();
			}
			case INFO_PARAMETER:
			{
				if(isValidInfoParamArgs(args))
				{
					return new InfoParameterRequestBody(args.get(0).toString());
				}
				else { return null; }
			}
			case TRANSACTION:
			{
				if(isValidTransactionArgs(args))
				{
					return new TransactionRequestBody( (Transaction) args.get(0) );
				}
			}
			case ORDER:
			{
				if(isValidOrderArgs(args))
				{
					return new OrderRequestBody((Long) args.get(0));
				}
			}
			case TRADE:
			{
				if(isValidTradeArgs(args))
				{
					return new TradesRequestBody((Long) args.get(0));
				}
			}
			case TABLE_INFO:
			{
				if(isValidTableInfoArgs(args))
				{
					String tableName = (String) args.get(0);
					return new QuikTableInfoRequestBody( QuikTableType.forValue(tableName) );
				}
			}
            case TABLE_ITEM:
            {
                if(isValidTableItemArgs(args))
                {
                    String tableName = (String) args.get(0);
                    Integer index = (Integer) args.get(1);
                    return new QuikTableItemRequestBody( QuikTableType.forValue(tableName), index );
                }
            }
            case TABLE_ITEMS:
            {
                if(isValidTableInfoArgs(args))
                {
                    String tableName = (String) args.get(0);
                    return new QuikTableItemsRequestBody( QuikTableType.forValue(tableName) );
                }
            }
            case TRADING_PARAMETER:
            {
                if(isValidTradingParameterArgs(args))
                {
                    String classCode = (String) args.get(0);
                    String securityCode = (String) args.get(1);
                    ParameterType parameter = ParameterType.valueOf(ParameterType.class, (String) args.get(2));
                    String version = (String) args.get(3);
                    return new TradingParameterRequestBody(classCode, securityCode, parameter, version);
                }
            }
            case SUBSCRIBE_TRADING_PARAMETER:
            {
                if(isValidTradingParameterArgs(args))
                {
                    String classCode = (String) args.get(0);
                    String securityCode = (String) args.get(1);
                    String parameterName = (String) args.get(2);
                    return new SubscribeParameterRequestBody(classCode, securityCode, parameterName);
                }
            }
            case UNSUBSCRIBE_TRADING_PARAMETER:
            {
                if(isValidTradingParameterArgs(args))
                {
                    return new UnsubscribeParameterRequestBody( (ParameterDescriptor) args.get(0) );
                }
            }
            case TRADE_DATE:
            {
                return new TradeDateRequestBody();
            }
            case SECURITY_INFO:
            {
                if(isValidSecurityInfoArgs(args))
                {
                    String classCode = (String) args.get(0);
                    String securityCode = (String) args.get(1);
                    return new SecurityInfoRequestBody(classCode, securityCode);
                }
            }
            case MAX_LOT_COUNT:
            {
                if(isValidMaxCountOfLotsArgs(args))
                {
                    String classCode =      (String)    args.get(0);
                    String securityCode =   (String)    args.get(1);
                    String clientCode =     (String)    args.get(2);
                    String account =        (String)    args.get(3);
                    Double price =          (Double)    args.get(4);
                    Boolean isBuy =         (Boolean)   args.get(5);
                    Boolean isMarket =      (Boolean)   args.get(6);
                    return new MaxCountOfLotsRequestBody(classCode, securityCode, clientCode, account, price, isBuy, isMarket);
                }
            }
            case CLASS_INFO:
            {
                if(isValidClassInfoArgs(args))
                {
                    String classCode = (String) args.get(0);
                    return new SecurityClassInfoRequestBody(classCode);
                }
            }
            case CLASSES_LIST:
            {
                return new ClassesListRequestBody();
            }
            case CLASS_SECURITIES:
            {
                if(isValidClassSecuritiesArgs(args))
                {
                    String classCode = (String) args.get(0);
                    return new ClassSecuritiesRequestBody(classCode);
                }
            }
            case SUBSCRIBE_QUOTES:
            {
                if(isValidQuotesArgs(args))
                {
                    String classCode = (String) args.get(0);
                    String securityCode = (String) args.get(1);
                    return new SubscribeQuotesRequestBody(classCode, securityCode);
                }
            }
            case UNSUBSCRIBE_QUOTES:
            {
                if(isValidQuotesArgs(args))
                {
                    return new UnsubscribeQuotesRequestBody( (QuotesDescriptor) args.get(0) );
                }
            }
            case IS_SUBSCRIBED_QUOTES:
            {
                if(isValidQuotesArgs(args))
                {
                    return new IsSubscribeQuotesRequestBody( (QuotesDescriptor) args.get(0) );
                }
            }
            case QUOTES:
            {
                if(isValidQuotesArgs(args))
                {
                    String classCode = (String) args.get(0);
                    String securityCode = (String) args.get(1);
                    return new QuotesRequestBody(classCode, securityCode);
                }
            }
            case CREATE_DATASOURCE:
            {
                if(isValidDatasourceArgs(args))
                {
                    String classCode = (String) args.get(0);
                    String securityCode = (String) args.get(1);
                    TimeScale interval = TimeScale.valueOf(TimeScale.class, (String) args.get(2));
                    if(args.size() == 3) {
                        return new CreateDatasourceRequestBody(classCode, securityCode, interval);
                    } else if (args.size() == 4) {
                        DSParameterType parameter = DSParameterType.valueOf(DSParameterType.class, (String) args.get(3));
                        return new CreateDatasourceRequestBody(classCode, securityCode, interval, parameter);
                    }
                }
            }
            case CLOSE_DATASOURCE:
            {
                if(isValidDatasourceArgs(args))
                {
                    return new CloseDatasourceRequestBody( (DatasourceDescriptor) args.get(0) );
                }
            }
            case DATASOURCE_SIZE:
            {
                if(isValidDatasourceArgs(args))
                {
                    return new DatasourceSizeRequestBody( (DatasourceDescriptor) args.get(0) );
                }
            }
            case SINGLE_CANDLE:
            {
                if(isValidDatasourceArgs(args))
                {
                    return new SingleCandleRequestBody( (DatasourceDescriptor) args.get(0), (Long) args.get(1) );
                }
            }
            case ALL_CANDLES:
            {
                if(isValidDatasourceArgs(args))
                {
                    return new AllCandlesRequestBody( (DatasourceDescriptor) args.get(0) );
                }
            }
			default:
			{
				return null;
			}
		}
	}


	private boolean isValidEchoArgs(List<?> args)
	{
		boolean isValid = true;
		isValid = isValid && isValidArg(args);
		isValid = isValid && args.get(0) instanceof String;
		return isValid;
	}


	private boolean isValidInfoParamArgs(List<?> args)
	{
		boolean isValid = true;
		isValid = isValid && isValidArg(args);
		isValid = isValid && args.get(0) instanceof String;

		if(isValid)
		{
			boolean belongsToAvailableTypes = false;
			for(InfoParamType type : InfoParamType.values())
			{
				if( type.toString().equalsIgnoreCase(args.get(0).toString()) )
				{
					belongsToAvailableTypes = true;
					break;
				}
			}
			isValid = isValid && belongsToAvailableTypes;
		}
		return isValid;
	}


	private boolean isValidTransactionArgs(List<?> args)
	{
		boolean isValid = true;
		Object arg;
		for(int i = 0; i < args.size(); i++)
		{
			arg = args.get(i);
			if(arg instanceof Transaction)
			{
				isValid = isValidTransaction( (Transaction) arg );
			}
			else
			{
				isValid = false;
			}
		}
		return isValid;
	}

	private boolean isValidTransaction(Transaction transaction)
	{
		boolean isValid = true;
		isValid = isValid && transaction.getTransId() 	!= null;
		isValid = isValid && transaction.getAction() 	!= null;
		isValid = isValid && transaction.getClassCode() != null;
		isValid = isValid && transaction.getSecCode() 	!= null;
		isValid = isValid && transaction.getOperation() != null;
		isValid = isValid && transaction.getType() 		!= null;
		isValid = isValid && transaction.getQuantity() 	!= null;
		isValid = isValid && transaction.getAccount() 	!= null;
		isValid = isValid && transaction.getPrice() 	!= null;
		isValid = isValid && transaction.getComment() 	!= null;
		isValid = isValid && transaction.getMode() 		!= null;
		return isValid;
	}


	private boolean isValidOrderArgs(List<?> args)
	{
		boolean isValid = true;
		isValid = isValid && isValidArg(args);
		isValid = isValid && args.get(0) instanceof Long;
		return isValid;
	}


	private boolean isValidTradeArgs(List<?> args)
	{
		boolean isValid = true;
		isValid = isValid && isValidArg(args);
		isValid = isValid && args.get(0) instanceof Long;
		return isValid;
	}

	private boolean isValidArg(List<?> args) {
		boolean isValid = true;
		isValid = isValid && args != null;
		isValid = isValid && args.size() == 1;
		isValid = isValid && args.get(0) != null;
		return isValid;
	}


	private boolean isValidTableInfoArgs(List<?> args)
	{
		boolean isValid = true;
		isValid = isValid && isValidArg(args);
		isValid = isValid && args.get(0) instanceof String;
		isValid = isValid && QuikTableType.forValue((String) args.get(0)) != null;
		return isValid;
	}


    private boolean isValidTableItemArgs(List<?> args)
    {
        boolean isValid = true;
        isValid = isValid && args != null;
        isValid = isValid && args.size() == 2;
        isValid = isValid && args.get(0) != null;
        isValid = isValid && args.get(0) instanceof String;
        isValid = isValid && args.get(1) != null;
        isValid = isValid && args.get(1) instanceof Integer;
        isValid = isValid && (Integer) args.get(1) >= 0;
        return isValid;
    }


    private boolean isValidTradingParameterArgs(List<?> args)
    {
        return true;
    }

    private boolean isValidQuotesArgs(List<?> args)
    {
        return true;
    }

    private boolean isValidSecurityInfoArgs(List<?> args)
    {
        return true;
    }


    private boolean isValidClassInfoArgs(List<?> args)
    {
        return true;
    }

    private boolean isValidClassSecuritiesArgs(List<?> args)
    {
        return true;
    }

    private boolean isValidMaxCountOfLotsArgs(List<?> args)
    {
        return true;
    }

    private boolean isValidDatasourceArgs(List<?> args)
    {
        return true;
    }
}
