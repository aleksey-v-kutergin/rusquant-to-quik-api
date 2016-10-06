package ru.rusquant.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.rusquant.messages.request.Request;
import ru.rusquant.messages.response.Response;
import ru.rusquant.messages.response.body.EchoResponseBody;
import ru.rusquant.messages.response.body.ResponseBody;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 *   Class covers serialization \ deserialization of
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
public class MessagesManager
{
	private static MessagesManager instance = new MessagesManager();

	private ObjectMapper mapper = new ObjectMapper();

	public static MessagesManager getInstance()
	{
		return instance;
	}

	private MessagesManager()
	{

	}


	public String serializeRequest(Request request)
	{
		if(request == null) return null;
		try
		{
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, request);
			return writer.toString();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}



	public Response deserializeResponse(String rawJsonResponse)
	{
		try
		{
			//String testJson = "{\"body\":{\"type\":\"EchoResponseBody\",\"echoAnswer\":\"@ECHO: RUSQUANT TEST MESSAGE: 1\"},\"error\":\"NO_ERROR\",\"requestId\":1,\"sendingTimeOfResponseAtClient\":1475497547,\"sendingTimeOfReuestAtClient\":1475497547442,\"status\":\"SUCCESS\",\"subject\":\"ECHO\",\"timeOfReceiptOfResponseAtClient\":1475497547,\"timeOfReceiptOfReuestAtServer\":1475497547,\"type\":\"GET\"}";
			StringReader reader = new StringReader(rawJsonResponse);
			Response response = mapper.readValue(reader, Response.class);

			return response;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
