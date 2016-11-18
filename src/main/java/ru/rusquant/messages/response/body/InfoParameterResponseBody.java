package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.InfoParameter;

public class InfoParameterResponseBody extends ResponseBody
{
	private InfoParameter infoParameter;

	public InfoParameterResponseBody()
	{

	}

	public InfoParameterResponseBody(InfoParameter infoParameter)
	{
		this.infoParameter = infoParameter;
	}


	public InfoParameter getInfoParameter()
	{
		return infoParameter;
	}

	public void setInfoParameter(InfoParameter infoParameter)
	{
		this.infoParameter = infoParameter;
	}
}
