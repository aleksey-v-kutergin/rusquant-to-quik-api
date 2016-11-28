package ru.rusquant.connection.pipe;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.ptr.IntByReference;

import java.nio.ByteBuffer;

/**
 *    Java implementation for
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public interface WindowsNamedPipeAPI extends Kernel32
{
	boolean FlushFileBuffers(HANDLE hNamedPipe);

	boolean PeekNamedPipe(HANDLE hNamedPipe, ByteBuffer lpBuffer, int nBufferSize, IntByReference lpBytesRead, DWORDByReference lpTotalBytesAvail, DWORDByReference lpBytesLeftThisMessage);
}
