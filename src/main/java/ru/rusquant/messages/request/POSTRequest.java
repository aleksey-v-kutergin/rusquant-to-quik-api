package ru.rusquant.messages.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.messages.request.body.RequestBody;

/**
 *   Class for POST request
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class POSTRequest extends Request
{
	public POSTRequest()
	{

	}

	public POSTRequest(Long id, String subject, RequestBody body)
	{
		super(id, "POST", subject, body);
	}
}
