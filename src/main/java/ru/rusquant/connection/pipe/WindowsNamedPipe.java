package ru.rusquant.connection.pipe;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

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

	private static final  /*Kernel32*/ WindowsNamedPipeAPI KERNEL32_INSTANCE = (WindowsNamedPipeAPI) Native.loadLibrary("kernel32", WindowsNamedPipeAPI.class, W32APIOptions.UNICODE_OPTIONS);

	private static final int IO_BUFFER_SIZE = 4 * 1024;

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



	public void write(String message) throws IOException
	{
		if(message == null || message.length() == 0) return;

		WinBase.OVERLAPPED overlapped = new WinBase.OVERLAPPED();
		ByteBuffer buffer = ByteBuffer.allocate(message.length() + 1);
		IntByReference bytesWritten = new IntByReference();

		buffer.put(message.getBytes());
		buffer.put((byte) 0);

		boolean isIOOperationSuccess = KERNEL32_INSTANCE.WriteFile(handle, buffer.array(), buffer.capacity(), bytesWritten, overlapped);
		int lastError = KERNEL32_INSTANCE.GetLastError();

		if(isIOOperationSuccess)
		{
			KERNEL32_INSTANCE.FlushFileBuffers(handle);
		}
		else
		{
			if(lastError == WinNT.ERROR_IO_PENDING)
			{
				while(overlapped.Internal.intValue() == WinNT.ERROR_IO_PENDING) {}
				KERNEL32_INSTANCE.FlushFileBuffers(handle);
			}
			else
			{
				throw new IOException("ERROR! FAILED TO WRITE MESSAGE TO THE WINDOWS NAMED PIPE: " + this.pipeName + " WITH ERROR CODE: " + lastError);
			}
		}
	}


	public String read() throws IOException
	{
		WinBase.OVERLAPPED overlapped = new WinBase.OVERLAPPED();
		ByteBuffer buffer = ByteBuffer.allocate(IO_BUFFER_SIZE);
		IntByReference bytesRead = new IntByReference(buffer.capacity());

		if (KERNEL32_INSTANCE.PeekNamedPipe(handle, buffer, buffer.capacity(), bytesRead, null, null))
		{
			boolean isIOOperationSuccess = KERNEL32_INSTANCE.ReadFile(handle, buffer, buffer.capacity(), bytesRead, overlapped);
			int lastError = KERNEL32_INSTANCE.GetLastError();
			if(!isIOOperationSuccess)
			{
				if(lastError == WinNT.ERROR_IO_PENDING || lastError == WinNT.ERROR_MORE_DATA)
				{
					while(overlapped.Internal.intValue() == WinNT.ERROR_IO_PENDING || overlapped.Internal.intValue() == WinNT.ERROR_MORE_DATA) {}
				}
				else
				{
					throw new IOException("ERROR! FAILED TO READ MESSAGE FROM THE WINDOWS NAMED PIPE: " + this.pipeName + " WITH ERROR CODE: " + lastError);
				}
			}
			if(bytesRead.getValue() > 0) { return new String(buffer.array(), 0, bytesRead.getValue()); }
		}

		return null;
	}


	public void close() throws IOException
	{

	}
}
