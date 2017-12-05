package ru.rusquant.messages.response;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.messages.request.RequestType;
import ru.rusquant.messages.request.body.RequestSubject;
import ru.rusquant.messages.response.body.ResponseBody;

@JsonAutoDetect
public class Response
{
	/**
	 *     Id of underling request.
	 *     This is service parameter.
	 **/
	private Long requestId;

	/**
	 *     The time when the request was sent from client in UNIX format.
	 *     This is service parameter.
	 **/
	private Long sendingTimeOfRequestAtClient;


	/**
	 *     The time when server receipts the request in UNIX format.
	 *     This is service parameter.
	 **/
	private Long timeOfReceiptOfRequestAtServer;


	/**
	 *     The time when the response was sent from server in UNIX format.
	 *     This is service parameter.
	 **/
	private Long sendingTimeOfResponseAtServer;


	/**
	 *     The time when client receipts the response for corresponding request in UNIX format.
	 *     This is service parameter.
	 **/
	private Long timeOfReceiptOfResponseAtClient;



	/**
	 *    The type of request:
	 *    1. GET - get some data from server (get quik server time, get order book snapshot and so on)
	 *    2. POST - execute some operation at server side (Place order and so on)
	 **/
	private RequestType type;


	/**
	 *    Subject of request: subject of get or post operation.
	 *    For example: GET ECHO, GET TIME, POST ORDER .. etc.
	 **/
	private RequestSubject subject;


	/**
	 *    The result of the attempt to execute request on server: SUCCESS / FAIL
	 **/
	private ResponseStatus status;


	/**
	 *    If attempt to execute request fails - error description else null
	 **/
	private String error;

	/**
	 *    Requested data or meta data (for placed order for example).
	 *    Particular object depends on type and subject of the request.
	 **/
	private ResponseBody body;


	// Serialization constructor
	public Response()
	{

	}


	public ResponseBody getBody()
	{
		return body;
	}

	public void setBody(ResponseBody body)
	{
		this.body = body;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public Long getRequestId()
	{
		return requestId;
	}

	public void setRequestId(Long requestId)
	{
		this.requestId = requestId;
	}

	public Long getSendingTimeOfResponseAtServer()
	{
		return sendingTimeOfResponseAtServer;
	}

	public void setSendingTimeOfResponseAtServer(Long sendingTimeOfResponseAtServer)
	{
		this.sendingTimeOfResponseAtServer = sendingTimeOfResponseAtServer;
	}

	public Long getSendingTimeOfRequestAtClient()
	{
		return sendingTimeOfRequestAtClient;
	}

	public void setSendingTimeOfRequestAtClient(Long sendingTimeOfRequestAtClient)
	{
		this.sendingTimeOfRequestAtClient = sendingTimeOfRequestAtClient;
	}

	public ResponseStatus getStatus()
	{
		return status;
	}

	public void setStatus(ResponseStatus status)
	{
		this.status = status;
	}

	public Long getTimeOfReceiptOfResponseAtClient()
	{
		return timeOfReceiptOfResponseAtClient;
	}

	public void setTimeOfReceiptOfResponseAtClient(Long timeOfReceiptOfResponseAtClient)
	{
		this.timeOfReceiptOfResponseAtClient = timeOfReceiptOfResponseAtClient;
	}

	public Long getTimeOfReceiptOfRequestAtServer()
	{
		return timeOfReceiptOfRequestAtServer;
	}

	public void setTimeOfReceiptOfRequestAtServer(Long timeOfReceiptOfRequestAtServer)
	{
		this.timeOfReceiptOfRequestAtServer = timeOfReceiptOfRequestAtServer;
	}

	public RequestType getType()
	{
		return type;
	}

	public void setType(RequestType type)
	{
		this.type = type;
	}

	public RequestSubject getSubject()
	{
		return subject;
	}

	public void setSubject(RequestSubject subject)
	{
		this.subject = subject;
	}
}
