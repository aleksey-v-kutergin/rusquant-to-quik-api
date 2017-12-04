package ru.rusquant.messages.factory;

import ru.rusquant.messages.request.*;
import ru.rusquant.messages.request.body.RequestBody;
import ru.rusquant.messages.request.body.RequestSubject;

/**
 * Factory for dealing with requests.
 * Created by kutergin on 28.09.2016.
 */
public class RequestFactory
{
	/**
	 *     Unique identificator for request within session.
	 **/
	private long requestId = 0;


	public void resetRequestIdSequence()
	{
		this.requestId = 0;
	}


	public Request createRequest(RequestType type, RequestSubject subject, RequestBody body)
	{
		switch (type)
		{
			case GET:
			{
				if(isValidSubjectForGET(subject))
				{
					requestId++;
					return new GETRequest(requestId, subject.toString(), body);
				}
				else { return null; }
			}
			case POST:
			{
				if(isValidSubjectForPOST(subject))
				{
					requestId++;
					return new POSTRequest(requestId, subject.toString(), body);
				}
				else { return null; }
			}
			default:
			{
				return null;
			}
		}
	}


	private boolean isValidSubjectForGET(RequestSubject subject)
	{
		if(subject == null) return false;
		return true;
	}


	private boolean isValidSubjectForPOST(RequestSubject subject)
	{
		if(subject == null) return false;
		return true;
	}
}
