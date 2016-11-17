package ru.rusquant.messages.factory;

import ru.rusquant.messages.request.RequestSubject;
import ru.rusquant.messages.request.body.ConnectionStateRequestBody;
import ru.rusquant.messages.request.body.EchoRequestBody;
import ru.rusquant.messages.request.body.RequestBody;

/**
 *   Factory for request's body
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class RequestBodyFactory
{
	public RequestBody createRequestBody(RequestSubject subject, String[] args)
	{
		switch (subject)
		{
			case ECHO:
			{
				if(isValidEchoArgs(args))
				{
					return new EchoRequestBody(args[0]);
				}
				else { return null; }
			}
			case CONNECTION_SATE:
			{
				return new ConnectionStateRequestBody();
			}
			default:
			{
				return null;
			}
		}
	}


	private boolean isValidEchoArgs(String[] args)
	{
		boolean isValid = true;
		isValid = isValid && args != null;
		isValid = isValid && args.length == 1;
		isValid = isValid && args[0] != null;
		return isValid;
	}
}
