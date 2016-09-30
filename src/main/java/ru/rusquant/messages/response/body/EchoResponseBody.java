package ru.rusquant.messages.response.body;

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
}
