package ru.rusquant.data.quik;

/**
 *    Echo data object.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class QuikEcho extends QuikDataObject
{
	private String echoAnswer;

	public QuikEcho()
	{

	}

	public QuikEcho(String echoAnswer)
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
