package ru.rusquant.connection.pipe;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *    Java representation of the Windows Named Pipe
 *    Class takes care for all native logic to work with client-side of the windows named pipe
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 *    \\\\.\\pipe\\RusquantToQuikPipe
 */
public class WindowsNamedPipe implements Closeable
{
	static
	{
		System.setProperty("jna.encoding", "Cp1251");
	}

	private static Kernel32 KERNEL32_INSTANCE = Kernel32.INSTANCE;

	private String pipeName;

	private WinNT.HANDLE handle = WinNT.INVALID_HANDLE_VALUE;


	public WindowsNamedPipe(String pipeName)
	{
		this.pipeName = pipeName;
	}



	public Boolean connect()
	{
		if(this.pipeName == null || this.pipeName.isEmpty()) return Boolean.FALSE;
		if(handle != WinNT.INVALID_HANDLE_VALUE) return Boolean.TRUE;

		for(int retryCount = 10; retryCount > 0; retryCount--)
		{
			this.handle = KERNEL32_INSTANCE.CreateFile(this.pipeName, Kernel32.GENERIC_READ | Kernel32.GENERIC_WRITE, 0, null, Kernel32.OPEN_EXISTING, 0, null);

			if(handle == WinNT.INVALID_HANDLE_VALUE)
			{
				int errorCode = KERNEL32_INSTANCE.GetLastError();
				if(errorCode == Kernel32.ERROR_FILE_NOT_FOUND)
				{
					System.out.println("ERROR! FAILED TO CONNECT TO WINDOWS NAMED PIPE: " + this.pipeName + " SERVER HAS NOT YET OPENED CONNECTION!");
				}
				else if(errorCode == Kernel32.ERROR_PIPE_BUSY)
				{
					System.out.println("ERROR! FAILED TO CONNECT TO WINDOWS NAMED PIPE: " + this.pipeName + " THE PIPE IS BUSY!!");
				}
				else
				{
					System.out.println("ERROR! FAILED TO CONNECT TO WINDOWS NAMED PIPE: " + this.pipeName + " WITH ERROR CODE: " + KERNEL32_INSTANCE.GetLastError());
				}
			}
			else
			{
				return Boolean.TRUE;
			}
		}

		System.out.println("ERROR! FAILED TO CONNECT TO WINDOWS NAMED PIPE: " + this.pipeName + " EXCEEDED RETRY COUNT!");
		return Boolean.FALSE;
	}



	public void disconnect() throws IOException
	{
		if(handle != WinNT.INVALID_HANDLE_VALUE)
		{
			boolean result = KERNEL32_INSTANCE.CloseHandle(handle);
			if(!result)
			{
				throw new IOException("ERROR! FAILED TO CLOSE WINDOWS NAMED PIPE: " + this.pipeName + " HANDLE WITH ERROR CODE: " + KERNEL32_INSTANCE.GetLastError());
			}
		}
	}


	public void write()
	{

	}


	public String read()
	{
		WinBase.OVERLAPPED overlapped = new WinBase.OVERLAPPED();
		ByteBuffer buffer = ByteBuffer.allocate(4 * 1024);
		IntByReference bytesRead = new IntByReference(buffer.capacity());
		int lastError = 0;

		while( !( KERNEL32_INSTANCE.ReadFile(handle, buffer, buffer.capacity(), bytesRead, overlapped) ) || ( lastError = KERNEL32_INSTANCE.GetLastError() ) != Kernel32.ERROR_MORE_DATA )
		{
			if(lastError == Kernel32.ERROR_PIPE_NOT_CONNECTED || overlapped.Internal.intValue() != WinNT.ERROR_IO_PENDING) { break; }
		}

		if(bytesRead.getValue() > 0)
		{
			return new String(buffer.array(), 0, bytesRead.getValue());
		}
		else
		{
			return null;
		}
	}


	public void close() throws IOException
	{

	}
}
