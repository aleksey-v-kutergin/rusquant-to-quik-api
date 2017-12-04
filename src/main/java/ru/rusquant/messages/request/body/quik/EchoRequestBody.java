package ru.rusquant.messages.request.body.quik;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.messages.request.body.RequestBody;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class EchoRequestBody extends RequestBody
{
	private String echoMessage;


	public EchoRequestBody()
	{

	}


	public EchoRequestBody(String echoMessages)
	{
		this.echoMessage = echoMessages;
	}


	public String getEchoMessage()
	{
		return echoMessage;
	}
}
