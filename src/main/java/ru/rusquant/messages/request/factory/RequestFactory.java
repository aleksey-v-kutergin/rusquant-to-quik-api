package ru.rusquant.messages.request.factory;

import ru.rusquant.messages.request.*;
import ru.rusquant.messages.request.body.RequestBody;
import ru.rusquant.messages.request.body.RequestSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for dealing with requests.
 * Created by kutergin on 28.09.2016.
 */
public class RequestFactory
{
	/** Unique id for request within session. **/
	private long requestId = 0;

	private List<RequestSubject> getRequests = new ArrayList<>();
	private List<RequestSubject> postRequests = new ArrayList<>();

	public RequestFactory()
	{
		// Subset of GET request's subjects
		getRequests.add(RequestSubject.ECHO);
		getRequests.add(RequestSubject.ORDER);
		getRequests.add(RequestSubject.CONNECTION_SATE);
		getRequests.add(RequestSubject.INFO_PARAMETER);
		getRequests.add(RequestSubject.TRADE);
		getRequests.add(RequestSubject.TABLE_INFO);
		getRequests.add(RequestSubject.TABLE_ITEM);
		getRequests.add(RequestSubject.TABLE_ITEMS);
		getRequests.add(RequestSubject.TRADING_PARAMETER);
		getRequests.add(RequestSubject.TRADE_DATE);
		getRequests.add(RequestSubject.SECURITY_INFO);
		getRequests.add(RequestSubject.MAX_LOT_COUNT);
		getRequests.add(RequestSubject.CLASS_INFO);
		getRequests.add(RequestSubject.CLASSES_LIST);
		getRequests.add(RequestSubject.CLASS_SECURITIES);
		getRequests.add(RequestSubject.IS_SUBSCRIBED_QUOTES);
		getRequests.add(RequestSubject.QUOTES);
		getRequests.add(RequestSubject.DATASOURCE_SIZE);
		getRequests.add(RequestSubject.ALL_CANDLES);
		getRequests.add(RequestSubject.SINGLE_CANDLE);

		// Subset of POST request's subjects
		postRequests.add(RequestSubject.TRANSACTION);
		postRequests.add(RequestSubject.SUBSCRIBE_TRADING_PARAMETER);
		postRequests.add(RequestSubject.UNSUBSCRIBE_TRADING_PARAMETER);
		postRequests.add(RequestSubject.SUBSCRIBE_QUOTES);
		postRequests.add(RequestSubject.UNSUBSCRIBE_QUOTES);
		postRequests.add(RequestSubject.CREATE_DATASOURCE);
		postRequests.add(RequestSubject.CLOSE_DATASOURCE);
	}

	public void resetRequestIdSequence()
	{
		this.requestId = 0;
	}

	public Request createRequest(RequestType type, RequestSubject subject, RequestBody body)
			throws IllegalArgumentException
	{
		switch (type)
		{
			case GET:
			{
				if(isValidSubjectForGET(subject))
				{
					requestId++;
					return new GETRequest(requestId, subject, body);
				}
				else
				{
					String msg = subject == null ? "The passed request subject is null!"
							: "Passed request subject cannot be subject of the GET request!";
					throw new IllegalArgumentException(msg);
				}
			}
			case POST:
			{
				if(isValidSubjectForPOST(subject))
				{
					requestId++;
					return new POSTRequest(requestId, subject, body);
				}
				else
				{
					String msg = subject == null ? "The passed request subject is null!"
							: "Passed request subject cannot be subject of the POST request!";
					throw new IllegalArgumentException(msg);
				}
			}
			default:
			{
				String msg = subject == null ? "The passed request type is null!"
						: "Not supported type of request!";
				throw new IllegalArgumentException(msg);
			}
		}
	}


	private boolean isValidSubjectForGET(RequestSubject subject)
	{
		return this.getRequests.contains(subject);
	}


	private boolean isValidSubjectForPOST(RequestSubject subject)
	{
		return this.postRequests.contains(subject);
	}
}
