package ru.rusquant.messages.response.body;

import ru.rusquant.data.quik.Echo;

public class EchoResponseBody extends ResponseBody
{
	private Echo echo;

	public EchoResponseBody()
	{

	}

	public EchoResponseBody(Echo echo)
	{
		this.echo = echo;
	}

	public Echo getEcho()
	{
		return echo;
	}

	public void setEcho(Echo echo)
	{
		this.echo = echo;
	}
}
