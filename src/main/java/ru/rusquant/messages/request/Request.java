package ru.rusquant.messages.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.messages.request.body.RequestBody;

/**
 *   Class encapsulated data, which are common for all Client requests.
 *   Client sends requests in json format.
 *   Logic of request: GET\POST (KIND OF ACTION) ECHO\ORDER (WHAT?) parameters (RequestBody)
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class Request
{
	/**
	 *     Unit identifier within single client's session
	 *     This is service parameter.
	 **/
	private Long id;

	/**
	 *     The time when the request was sent in UNIX format.
	 *     This is service parameter.
	 **/
	private Long time;


	/**
	 *    The type of request:
	 *    1. GET - get some data from server (get quik server time, get order book snapshot and so on)
	 *    2. POST - execute some operation at server side (Place order and so on)
	 **/
	private String type;


	/**
	 *    Subject of request: subject of get or post operation.
	 *    For example: GET ECHO, GET TIME, POST ORDER .. etc.
	 **/
	private String subject;


	/**
	 *    Parameters of the request.
	 *    Particular object depends on type and subject of the request.
	 **/
	private RequestBody body;



	public Request()
	{

	}


	public Request(Long id, String type, String subject, RequestBody body)
	{
		this.id = id;
		this.type = type;
		this.subject = subject;
		this.body = body;
	}

	public void fixSendingTime()
	{
		this.time = System.currentTimeMillis();
	}

	public Long getId()
	{
		return id;
	}

	public String getType()
	{
		return type;
	}

	public Long getTime()
	{
		return time;
	}

	public String getSubject()
	{
		return subject;
	}

	public RequestBody getBody()
	{
		return body;
	}
}
