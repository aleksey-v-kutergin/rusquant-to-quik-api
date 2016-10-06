package ru.rusquant.messages.response.body;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EchoResponseBody extends ResponseBody
{
	private String echoAnswer;

	public EchoResponseBody()
	{

	}

	public EchoResponseBody(String echoAnswer)
	{
		this.echoAnswer = echoAnswer;
	}

	public String getEchoAnswer()
	{
		return echoAnswer;
	}

	public void setEchoAnswer(String echoAnswer)
	{
		this.echoAnswer = echoAnswer;
	}
}
