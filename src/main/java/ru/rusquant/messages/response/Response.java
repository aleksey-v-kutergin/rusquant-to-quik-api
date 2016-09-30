package ru.rusquant.messages.response;


import ru.rusquant.messages.response.body.ResponseBody;

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
	private Long sendingTimeOfReuestAtClient;


	/**
	 *     The time when server receipts the request in UNIX format.
	 *     This is service parameter.
	 **/
	private Long timeOfReceiptOfReuestAtServer;


	/**
	 *     The time when the response was sent from server in UNIX format.
	 *     This is service parameter.
	 **/
	private Long sendingTimeOfResponseAtClient;


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
	private String type;


	/**
	 *    Subject of request: subject of get or post operation.
	 *    For example: GET ECHO, GET TIME, POST ORDER .. etc.
	 **/
	private String subject;


	/**
	 *    The result of the attempt to execute request on server: SUCCESS / FAIL
	 **/
	private String status;


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
}
