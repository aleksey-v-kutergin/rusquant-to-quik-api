package ru.rusquant.messages.request.body.quik;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.rusquant.messages.request.body.RequestBody;

/**
 *   Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *   Company: Rusquant
 */
@JsonAutoDetect
public class InfoParameterRequestBody extends RequestBody
{
	private String infoParameterName;

	public InfoParameterRequestBody()
	{

	}

	public InfoParameterRequestBody(String infoParameterName)
	{
		this.infoParameterName = infoParameterName;
	}


	public String getInfoParameterName()
	{
		return infoParameterName;
	}

	public void setInfoParameterName(String infoParameterName)
	{
		this.infoParameterName = infoParameterName;
	}
}
