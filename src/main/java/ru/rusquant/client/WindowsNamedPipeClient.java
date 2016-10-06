package ru.rusquant.client;

import ru.rusquant.connection.pipe.WindowsNamedPipe;
import ru.rusquant.messages.MessagesManager;
import ru.rusquant.messages.factory.RequestBodyFactory;
import ru.rusquant.messages.factory.RequestFactory;
import ru.rusquant.messages.request.Request;
import ru.rusquant.messages.request.RequestSubject;
import ru.rusquant.messages.request.RequestType;
import ru.rusquant.messages.request.body.RequestBody;
import ru.rusquant.messages.response.Response;
import ru.rusquant.messages.response.body.EchoResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 *    Class implements all client-side logic of Windows Named Pipe server-client process
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class WindowsNamedPipeClient extends Client
{
	private WindowsNamedPipe pipe= new WindowsNamedPipe("\\\\.\\pipe\\RusquantToQuikPipe");

	private int clientMode = 1;
	private Boolean isStopped = Boolean.FALSE;

	private List<Request> requests = new ArrayList<Request>();

	private Map<Long, Long> requestResponseLatencyMap = new HashMap<Long, Long>();
	private List<Response> responses = new ArrayList<Response>();
	private List<Response> responsesList = new ArrayList<Response>();

	private RequestBodyFactory requestBodyFactory = new RequestBodyFactory();
	private RequestFactory requestFactory = new RequestFactory();

	private MessagesManager messagesManager = MessagesManager.getInstance();

	public WindowsNamedPipeClient()
	{

	}

	@Override
	public void connect()
	{
		this.isConnected = pipe.connect();
	}


	@Override
	public void disconnect()
	{
		try
		{
			pipe.disconnect();
			this.isConnected = Boolean.FALSE;
			requestFactory.resetRequestIdSequence();
			System.out.println("Disconnected from pipe!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void run()
	{
		System.out.println();
		fillMessages("RUSQUANT TEST MESSAGE", 100000);
		long testDuration = runBandwidthTest();

		double sum = 0;
		for(int i = 0; i < responses.size(); i++)
		{
			sum += (double) requestResponseLatencyMap.get( responses.get(i).getRequestId() );
		}
		System.out.println("\nServer process " + requests.size() + " requests in " + testDuration + " milliseconds with average time per request: " + ( sum / (double) requests.size() ) + " milliseconds");
		System.out.println();


		/*
		for(int i = 0; i < responses.size(); i++)
		{
			Response response = responses.get(i);
			String str = "ECHO REQUEST ID: " + response.getRequestId() + " || ECHO ANSWER: " +  ( (EchoResponseBody) response.getBody() ).getEchoAnswer();
			if(i == 0) { str = "\n" + str; }
			System.out.println(str);
		}
		*/
	}




	@Override
	public void stop()
	{
		disconnect();
	}



	@Override
	public String readMessage()
	{
		try
		{
			return pipe.read();
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}


	@Override
	public void writeMessage(String request)
	{
		try
		{
			pipe.write(request);
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}


	public long runBandwidthTest()
	{
		System.out.println("Running bandwidth test");
		System.out.println("Start client to server data exchange!");

		int counter = 0;
		int countOfRequests = requests.size();

		Request request = null;
		Response response;
		String rawJSONRequest = null;
		String rawJSONResponse;
		long latency;

		isStopped = Boolean.FALSE;
		long testStartTime = System.currentTimeMillis();
		while(!isStopped)
		{
			if(clientMode == 1)
			{
				request = requests.get(counter);
				request.fixSendingTime();

				rawJSONRequest = messagesManager.serializeRequest(requests.get(counter));
				if(rawJSONRequest != null && !rawJSONRequest.isEmpty())
				{
					writeMessage(rawJSONRequest);
					clientMode = 2;
				}
			}
			else if(clientMode == 2)
			{
				rawJSONResponse = readMessage();
				response = messagesManager.deserializeResponse(rawJSONResponse);
				if(response != null)
				{
					if(validateResponse(request, response))
					{
						responses.add(response);
						clientMode = 1;

						response.setTimeOfReceiptOfResponseAtClient( System.currentTimeMillis() );
						latency = response.getTimeOfReceiptOfResponseAtClient() - response.getSendingTimeOfResponseAtClient();
						requestResponseLatencyMap.put(response.getRequestId(), latency);
						if( counter % (0.10 * requests.size()) == 0) { System.out.print("*"); }

						counter++;
					}
				}
			}

			if(counter == countOfRequests)
			{
				writeMessage("CLIENT_OFF");
				isStopped = Boolean.TRUE;
			}
		}
		long testEndTime = System.currentTimeMillis();
		return testEndTime - testStartTime;
	}


	public void fillMessages(String message, int count)
	{
		this.requests.clear();
		this.responsesList.clear();
		this.requestResponseLatencyMap.clear();

		RequestBody echoBody;
		Request echoRequest;

		for(int i = 1; i <= count; i++)
		{
			String[] args = {message + ": " + i};
			echoBody =  requestBodyFactory.createRecuestBody(RequestSubject.ECHO, args);
			if(echoBody != null)
			{
				echoRequest = requestFactory.createRequest(RequestType.GET, RequestSubject.ECHO, echoBody);
				//String rawJson = messagesManager.serializeRequest(echoRequest);
				//System.out.println(rawJson);
				requests.add(echoRequest);
			}
		}
	}


	private boolean validateResponse(Request request, Response response)
	{
		if(request == null) return false;
		if(request.getId().equals(response.getRequestId())) return true;
		return false;
	}


	public static void main(String[] args)
	{
		try
		{
			WindowsNamedPipeClient client = new WindowsNamedPipeClient();
			//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			boolean isStopped = false;
			int counter = 1;
			while(!isStopped)
			{
				//System.out.println("Start new session?");
				//String command = reader.readLine();
				//if("yes".equals(command))
				if(counter <= 10)
				{
					System.out.println("==============================================================================================");
					System.out.println("Start session number: " + counter);

					System.out.println();
					System.out.println("Making connection to server");
					client.connect();

					if(client.isConnected())
					{
						System.out.println("Connect success!!!");

						System.out.println();
						System.out.println("Starting main client loop");
						client.run();

						System.out.println("Stopping client!");
						client.disconnect();
					}

					System.out.println();
					System.out.println("End session number: " + counter);
					System.out.println();
					System.out.println();

					counter++;
				}
				else
				{
					isStopped = true;
				}
			}
			//reader.close();
		}
		catch (Exception e)
		{

		}
	}


}
