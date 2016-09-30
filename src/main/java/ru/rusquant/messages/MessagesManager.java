package ru.rusquant.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.rusquant.messages.request.Request;

import java.io.IOException;
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
}
