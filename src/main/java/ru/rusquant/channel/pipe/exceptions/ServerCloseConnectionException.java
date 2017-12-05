package ru.rusquant.channel.pipe.exceptions;

import java.io.IOException;

/**
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class ServerCloseConnectionException extends IOException
{
	public ServerCloseConnectionException(String message)
	{
		super(message);
	}
}
