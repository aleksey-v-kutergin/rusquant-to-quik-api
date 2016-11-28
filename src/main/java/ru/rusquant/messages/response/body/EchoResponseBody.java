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
}
