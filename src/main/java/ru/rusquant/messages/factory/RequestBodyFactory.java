package ru.rusquant.messages.factory;

import ru.rusquant.data.quik.Transaction;
import ru.rusquant.data.quik.types.InfoParamType;
import ru.rusquant.data.quik.types.ParameterType;
import ru.rusquant.data.quik.types.QuikTableType;
import ru.rusquant.messages.request.RequestSubject;
import ru.rusquant.messages.request.body.*;

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

    private boolean isValidSecurityInfoArgs(List<?> args)
    {
        return true;
    }

    private boolean isValidMaxCountOfLotsArgs(List<?> args)
    {
        return true;
    }
}
