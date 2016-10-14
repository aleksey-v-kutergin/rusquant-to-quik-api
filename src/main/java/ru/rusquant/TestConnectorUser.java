package ru.rusquant;


import ru.rusquant.connector.JavaToQuikConnector;
import ru.rusquant.connector.JavaToQuikPipeConnector;
import ru.rusquant.data.quik.Echo;
import ru.rusquant.data.quik.ErrorObject;
import ru.rusquant.data.quik.QuikDataObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Just for testing
 * Created by kutergin on 30.09.2016.
 */
public class TestConnectorUser
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		JavaToQuikConnector connector = new JavaToQuikPipeConnector();
		connector.connect();
		if(connector.isConnected())
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			runManualTest(connector, reader);
			/*
			boolean isExit = false;
			while(!isExit)
			{
				System.out.println();
				System.out.println("Select the test type: manual or auto or exit");
				String test = reader.readLine();
				if("manual".equals(test))
				{
					runManualTest(connector, reader);
				}
				else if("auto".equals(test))
				{
					runAutoTest();
				}
				else if("exit".equals(test))
				{
					isExit = true;
				}
				else
				{
					System.out.println("Invalid test type!");
				}
			}
			*/


			reader.close();
		}
		connector.disconnect();
	}


	private static void runManualTest(JavaToQuikConnector connector, BufferedReader reader) throws IOException
	{
		System.out.println("Running manual test...");
		System.out.println();

		boolean isExit = false;
		while(!isExit)
		{
			System.out.println();
			System.out.println("Enter not empty message message or type exit:");
			String message = reader.readLine();
			if(message != null && !message.isEmpty())
			{
				if("exit".equals(message))
				{
					isExit = true;
				}
				else
				{
					QuikDataObject result = connector.getEcho(message);
					if(result instanceof ErrorObject)
					{
						System.out.println( ((ErrorObject) result).getErrorMessage() );
						isExit = true;
					}
					else
					{
						System.out.println( ((Echo) result).getEchoAnswer() );
					}
				}
			}
			else
			{
				System.out.println("Invalid test type!");
			}
		}

	}


	private static void runAutoTest()
	{
		System.out.println("Running auto test...");
	}

}


		/*
		System.out.println();
		fillMessages("RUSQUANT TEST MESSAGE", 10000);
		long testDuration = runBandwidthTest();

		double sum = 0;
		for(int i = 0; i < responses.size(); i++)
		{
			sum += (double) requestResponseLatencyMap.get( responses.get(i).getRequestId() );
		}
		System.out.println("\nServer process " + requests.size() + " requests in " + testDuration + " milliseconds with average time per request: " + (sum / (double) requests.size()) + " milliseconds");
		System.out.println();
		*/


		/*
		for(int i = 0; i < responses.size(); i++)
		{
			Response response = responses.get(i);
			String str = "ECHO REQUEST ID: " + response.getRequestId() + " || ECHO ANSWER: " +  ( (EchoResponseBody) response.getBody() ).getEchoAnswer();
			if(i == 0) { str = "\n" + str; }
			System.out.println(str);
		}
		*/


/*

	public long runBandwidthTest()
	{
		System.out.println("Running bandwidth test");
		System.out.println("Start client to server data exchange!");

		int counter = 0;
		int countOfRequests = requests.size();

		Request request = null;
		Response response;
		String rawJSONRequest;
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
		this.responses.clear();
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


*/
