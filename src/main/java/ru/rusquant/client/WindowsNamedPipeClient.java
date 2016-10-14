package ru.rusquant.client;

import ru.rusquant.connection.pipe.WindowsNamedPipe;
import ru.rusquant.messages.MessagesManager;
import ru.rusquant.messages.request.Request;
import ru.rusquant.messages.response.Response;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *    Class implements all client-side logic of Windows Named Pipe server-client process
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class WindowsNamedPipeClient extends Client
{
	private WindowsNamedPipe pipe= new WindowsNamedPipe("\\\\.\\pipe\\RusquantToQuikPipe");

	private MessagesManager messagesManager = MessagesManager.getInstance();

	private Boolean isStopped = Boolean.FALSE;


	/**
	 *    Queues for producer - consumer scheme.
	 *	  Client instance acts as producer. It populates queue of request and takes the requests come.
	 *	  ClientThread acts as consumer. It takes requests, sends them to server and populate queue with responses.
	 *	  So, ClientThread is some kind of meat grinder for requests. While Client is a control layer.
	 **/
	private LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<Request>();
	private LinkedBlockingQueue<Response> responses = new LinkedBlockingQueue<Response>();


	/**
	 *    Inner message processor.
	 **/
	private MessageGrinder grinder = new MessageGrinder();


	/**
	 *     Control layer.
	 **/

	public WindowsNamedPipeClient()
	{
		grinder.setName("MessageGrinder");
	}


	@Override
	public void connect()
	{
		synchronized (pipe)
		{
			this.isConnected = pipe.connect();
		}
	}


	@Override
	public void disconnect()
	{
		synchronized (pipe)
		{
			try
			{
				pipe.disconnect();
				this.isConnected = Boolean.FALSE;
				System.out.println("Disconnected from pipe!");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}


	@Override
	public void run()
	{
		isStopped = Boolean.FALSE;
		grinder.start();
	}


	/**
	 *   Causes stop of client to server message exchange process.
	 **/
	@Override
	public void stop() throws InterruptedException
	{
		grinder.interrupt();
		grinder.join();
		isStopped = Boolean.TRUE;
	}




	/**
	 *    Producer's method to populate queue of requests
	 **/
	public void postRequest(Request request) throws InterruptedException
	{
		requests.put(request);
	}


	/**
	 *    Producer's method to get the result of the request.
	 *    Actually, in this case Client class acts as consumer of responses.
	 **/
	public Response getResponse(Request request) throws InterruptedException
	{
		Response response = responses.take();
		return response;
	}


	public Boolean isStopped()
	{
		return isStopped;
	}

	/**
	 *     MessageGrinder class takes care about request - response processing between client and server.
	 *
	 *     It executes client's main loop:
	 *     1. Take request from queue
	 *     2. Serialize request into json and send it to server
	 *     3. Receive response from server and deserialize from json it into java-object
	 *     4. Put response into queue and go to next request
	 *
	 *     Execution of MessageGrinder makes sense only when client is connected to server.
	 *
	 *     So, scenario is as follows:
	 *     1. If client successfully connects to server - start MessageGrinder thread
	 *     2. MessageGrinder processes incoming requests
	 *     3. If client looses connection or instructed to stop or end session:
	 *     		3.1. Interrupt thread
	 *     		3.2. Main thread MUST wait until MessageGrinder thread finishes all operations
	 *
	 *
	 *     	Exception's politics:
	 *     	The hard link between request and response is considered.
	 *		The situations when client cannot write\read\serialize\deserialize request or response,
	 *		cannot receive valid (in sense of id) response for given request are considered as serious errors in message flow process.
	 *		In such situations client aborts all operations, shut down the connection and clean pipe descriptor, notify at connector's level about error.
	 **/
	private class MessageGrinder extends Thread
	{
		/**
		 *    Client switches between two modes:
		 *    1 - writing message to sever
		 *    2 - reading answer from server
		 **/
		private int clientMode = 1;


		private boolean isInterrupted = false;

		/**
		 *    Flag for emergency stop of the grinder.
		 **/
		private Boolean isEmergencyAborted = Boolean.FALSE;


		/**
		 *	   In order to know what's happens.
		 **/
		private IOException error;


		/**
		 *   For each response the corresponding request has to obtained.
		 *   If client has been instructed to shut down and response for last sent request has not been received,
		 *   then MessageGrinder has to work until this response will be received.
		 **/
		private Boolean isLastResponseReceived = Boolean.TRUE;



		public IOException getError()
		{
			return error;
		}


		public String readMessage() throws IOException
		{
			synchronized (pipe)
			{
				return pipe.read();
			}
		}


		public void writeMessage(String request) throws IOException
		{
			synchronized (pipe)
			{
				pipe.write(request);
			}
		}


		/**
		 *    The hard link between request and response is considered.
		 **/
		private boolean validateResponse(Request request, Response response)
		{
			if(request == null) return false;
			if(request.getId().equals(response.getRequestId())) return true;
			return false;
		}


		private Boolean needContinueWork()
		{
			if(isEmergencyAborted) return false;
			if(isInterrupted && isLastResponseReceived) return false;
			return true;
		}


		private Request executeWriteStep() throws InterruptedException, IOException
		{
			Request request = null;
			request = requests.take();
			if(request == null) return null;
			request.fixSendingTime();

			String rawJSONRequest = messagesManager.serializeRequest(request);
			if(rawJSONRequest != null && !rawJSONRequest.isEmpty())
			{
				writeMessage(rawJSONRequest);
				isLastResponseReceived = Boolean.FALSE;
				clientMode = 2;
			}

			return request;
		}


		private void executeReadStep(Request request) throws InterruptedException, IOException
		{
			String rawJSONResponse = readMessage();
			Response response = messagesManager.deserializeResponse(rawJSONResponse);
			if(response != null)
			{
				if(validateResponse(request, response))
				{
					response.setTimeOfReceiptOfResponseAtClient(System.currentTimeMillis());
					responses.put(response);

					isLastResponseReceived = Boolean.TRUE;
					clientMode = 1;
				}
			}
		}



		@Override
		public void run()
		{
			MessageGrinder.this.isInterrupted = false;
			Request request = null;
			try
			{
				while( needContinueWork() )
				{
					try
					{
						if(clientMode == 1)
						{
							request = executeWriteStep();
						}
						else if(clientMode == 2)
						{
							executeReadStep(request);
						}
					}
					catch (InterruptedException e)
					{
						MessageGrinder.this.isInterrupted = true;
						System.out.println("Message grinder thread has been interrupted!");
					}
				}

				writeMessage("CLIENT_OFF");

			}
			catch (IOException e)
			{
				MessageGrinder.this.isEmergencyAborted = Boolean.TRUE;
				error = e;
			}
		}
	}
}
