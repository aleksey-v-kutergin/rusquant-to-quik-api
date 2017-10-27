package ru.rusquant.messages.factory;

import ru.rusquant.data.quik.Transaction;
import ru.rusquant.data.quik.types.InfoParamType;
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
}
