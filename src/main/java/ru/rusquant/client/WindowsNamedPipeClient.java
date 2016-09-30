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

	private Map<String, Long> requestResponseLatencyMap = new HashMap<String, Long>();
	private List<String> responses = new ArrayList<String>();
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
		fillMessages("RUSQUANT TEST MESSAGE", 10);
		long testDuration = runBandwidthTest();

		/*
		double sum = 0;
		for(int i = 0; i < responses.size(); i++)
		{
			String res = responses.get(i);
			sum += (double) requestResponseLatencyMap.get(request);
		}
		System.out.println("\nServer process " + requests.size() + " requests in " + testDuration + " milliseconds with average time per request: " + ( sum / (double) requests.size() ) + " milliseconds");
		System.out.println();
		*/

		for(int i = 0; i < responses.size(); i++)
		{
			System.out.println(responses.get(i));
		}
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

		String rawJSONRequest = null;
		String response;

		long requestTime = 0;
		long responseTime;
		long latency;

		isStopped = Boolean.FALSE;
		long testStartTime = System.currentTimeMillis();
		while(!isStopped)
		{
			if(clientMode == 1)
			{
				Request request= requests.get(counter);
				request.fixSendingTime();

				rawJSONRequest = messagesManager.serializeRequest(requests.get(counter));
				if(rawJSONRequest != null && !rawJSONRequest.isEmpty())
				{
					writeMessage(rawJSONRequest);
					clientMode = 2;
					requestTime = System.currentTimeMillis();
				}
			}
			else if(clientMode == 2)
			{
				response = readMessage();
				if(response != null && !response.isEmpty())
				{
					if(validateResponse(rawJSONRequest, response))
					{
						responses.add(response);
						clientMode = 1;

						responseTime = System.currentTimeMillis();
						latency = responseTime - requestTime;
						requestResponseLatencyMap.put(rawJSONRequest, latency);
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
				String rawJson = messagesManager.serializeRequest(echoRequest);
				System.out.println(rawJson);
				requests.add(echoRequest);
			}
		}
	}


	private boolean validateResponse(String request, String response)
	{
		if(request == null || request.isEmpty()) return false;

		request = "@ECHO: " + request;
		if(request.equals(response)) return true;

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
				if(counter <= 1)
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
