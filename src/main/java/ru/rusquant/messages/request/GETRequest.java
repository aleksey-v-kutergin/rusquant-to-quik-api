package ru.rusquant.messages.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.messages.request.body.RequestBody;
import ru.rusquant.messages.request.body.RequestSubject;

/**
 *   Class for GET request
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class GETRequest extends Request
{
	public GETRequest()
	{

	}

	public GETRequest(Long id, RequestSubject subject, RequestBody body)
	{
		super(id, RequestType.GET, subject, body);
	}
}
