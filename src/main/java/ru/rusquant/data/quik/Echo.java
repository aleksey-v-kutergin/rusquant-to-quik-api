package ru.rusquant.data.quik;

/**
 *    Echo data object.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class Echo extends QuikDataObject
{
	private String echoAnswer;

	public Echo()
	{

	}

	public Echo(String echoAnswer)
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
