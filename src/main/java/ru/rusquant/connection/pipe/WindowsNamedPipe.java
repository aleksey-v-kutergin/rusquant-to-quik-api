package ru.rusquant.connection.pipe;

import com.sun.jna.platform.win32.WinNT;

import java.io.Closeable;
import java.io.IOException;

/**
 *    Java representation of the Windows Named Pipe
 *    Class takes care for all native logic to work with client-side of the windows named pipe
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class WindowsNamedPipe implements Closeable
{
	private String pipeName;

	private WinNT.HANDLE handle = WinNT.INVALID_HANDLE_VALUE;

	public WindowsNamedPipe(String pipeName)
	{
		this.pipeName = pipeName;
	}


	private void init()
	{

	}


	public void write()
	{

	}


	public String read()
	{
		return null;
	}


	public void close() throws IOException
	{

	}
}
