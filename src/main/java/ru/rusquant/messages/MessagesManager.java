package ru.rusquant.messages;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.rusquant.messages.request.Request;
import ru.rusquant.messages.response.Response;

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


	public String serializeRequest(Request request) throws IOException
	{
		if(request == null) return null;

		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, request);
		return writer.toString();
	}



	public Response deserializeResponse(String rawJsonResponse) throws IOException
	{
		if(rawJsonResponse == null || rawJsonResponse.isEmpty()) return null;

		StringReader reader = new StringReader(rawJsonResponse);
		Response response = mapper.readValue(reader, Response.class);
		return response;
	}
}
