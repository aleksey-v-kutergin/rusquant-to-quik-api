package ru.rusquant.client.timing;

import ru.rusquant.messages.request.body.RequestSubject;
import ru.rusquant.messages.response.Response;
import ru.rusquant.messages.response.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 *    Class implements logic for analysis of timing of the response-request process.
 *    Author: Aleksey Kutergin <aleksey.v.kutergin@gmail.ru>
 *    Company: Rusquant
 */
public class TimingManager
{
	private static TimingManager ourInstance = new TimingManager();

	public static TimingManager getInstance()
	{
		return ourInstance;
	}


	private static final int SAMPLE_SIZE = 100;
	private static final int MAX_CAPACITY = 1000;

	private ArrayBlockingQueue<TimingDataRow> storage = new ArrayBlockingQueue<TimingDataRow>(MAX_CAPACITY);
	private ConcurrentHashMap<String, TimingDataRow> statistics = new ConcurrentHashMap<String, TimingDataRow>();

	private Thread statisticsRefresher;



	/**
	 *     Class-container for time-info based on response data
	 **/
	private class TimingDataRow
	{
		public TimingDataRow(String type, String subject, String status)
		{
			this.type = type;
			this.subject = subject;
			this.status = status;
		}

		private String type;
		private String subject;
		private String status;

		private float shippingDurationOfRequest;
		private float durationOfRequestProcessing;
		private float shippingDurationOfResponse;
		private float requestResponseLatency;
	}



	private class RefreshStatisticsTask implements Runnable
	{
		List<TimingDataRow> sample;

		public RefreshStatisticsTask(List<TimingDataRow> sample)
		{
			this.sample = sample;
		}

		public void run()
		{
			for(RequestSubject subject : RequestSubject.values())
			{
				if(Thread.currentThread().isInterrupted()) { break; }
				if(sample == null || sample.isEmpty()) { break; }

				float sum1 = 0;
				float sum2 = 0;
				float sum3 = 0;
				float sum4 = 0;

				for(int i = 0; i < sample.size(); i++)
				{
					TimingDataRow row = sample.get(i);
					if(subject.toString().equals(row.subject) &&  ResponseStatus.SUCCESS.toString().equals(row.status))
					{
						sum1 += row.shippingDurationOfRequest;
						sum2 += row.durationOfRequestProcessing;
						sum3 += row.shippingDurationOfResponse;
						sum4 += row.requestResponseLatency;
					}
				}

				TimingDataRow avgRow = new TimingDataRow("", ResponseStatus.SUCCESS.toString(), subject.toString());
				float sampleSize = (float) sample.size();
				avgRow.shippingDurationOfRequest 	= (float) Math.round( ( sum1 / sampleSize ) * 100 ) / 100;
				avgRow.durationOfRequestProcessing 	= (float) Math.round( ( sum2 / sampleSize ) * 100 ) / 100;
				avgRow.shippingDurationOfResponse 	= (float) Math.round( ( sum3 / sampleSize ) * 100 ) / 100;
				avgRow.requestResponseLatency 		= (float) Math.round( ( sum4 / sampleSize ) * 100 ) / 100;
				statistics.put(subject.toString(), avgRow);
			}
		}
	}



	/**
	 *    Constructor initializes all statistics with zero values.
	 **/
	private TimingManager()
	{
		initStatistics();
	}


	private void initStatistics()
	{
		statistics.clear();
		for(RequestSubject subject : RequestSubject.values())
		{
			TimingDataRow initialAvgRow = new TimingDataRow("AVG", subject.toString(), ResponseStatus.SUCCESS.toString());
			initialAvgRow.shippingDurationOfRequest = 0;
			initialAvgRow.durationOfRequestProcessing = 0;
			initialAvgRow.shippingDurationOfResponse = 0;
			initialAvgRow.requestResponseLatency = 0;
			statistics.put(subject.toString(), initialAvgRow);
		}
	}


	public void addTimingDataRow(Response response)
	{
		try
		{
			if(storage.size() > SAMPLE_SIZE) { refreshStatistics(); }
			TimingDataRow dataRow = new TimingDataRow(response.getType().toString(), response.getSubject().toString(), response.getStatus().toString());
			dataRow.shippingDurationOfRequest 	= calculateTimeDifference(response.getTimeOfReceiptOfRequestAtServer(), response.getSendingTimeOfRequestAtClient());
			dataRow.durationOfRequestProcessing = calculateTimeDifference(response.getSendingTimeOfResponseAtServer(), response.getTimeOfReceiptOfRequestAtServer());
			dataRow.shippingDurationOfResponse 	= calculateTimeDifference(response.getTimeOfReceiptOfResponseAtClient(), response.getSendingTimeOfResponseAtServer());
			dataRow.requestResponseLatency 		= calculateTimeDifference(response.getTimeOfReceiptOfResponseAtClient(), response.getSendingTimeOfRequestAtClient());
			storage.put(dataRow);
		}
		catch (InterruptedException ignore) { stopStatisticsRefresher(); }
	}


	public void reset()
	{
		storage.clear();
		initStatistics();
	}


	private float calculateTimeDifference(Long minuendArg, Long subtrahendArg)
	{
		long minuend = minuendArg == null ? 0 : minuendArg;
		long subtrahend = subtrahendArg == null ? 0 : subtrahendArg;
		float timeDiff = (float) ( minuend - subtrahend );
		return timeDiff < 0 ? 0 : timeDiff;
	}


	private void refreshStatistics()
	{
		try
		{
			List<TimingDataRow> sample = new ArrayList<TimingDataRow>();
			for(int i = 0; i < SAMPLE_SIZE; i++)
			{
				sample.add(storage.take());
			}
			RefreshStatisticsTask task = new RefreshStatisticsTask(sample);
			statisticsRefresher = new Thread(task);
			statisticsRefresher.start();
		}
		catch (InterruptedException ignore) { stopStatisticsRefresher(); }
	}


	public void stopStatisticsRefresher()
	{
		if(statisticsRefresher != null && statisticsRefresher.isAlive())
		{
			statisticsRefresher.interrupt();
		}
	}



	public Float getAvgShippingDurationOfRequest(RequestSubject subject)
	{
		TimingDataRow row = statistics.get(subject.toString());
		return row == null ? 0 : row.shippingDurationOfRequest;
	}

	public Float getAvgDurationOfRequestProcessing(RequestSubject subject)
	{
		TimingDataRow row = statistics.get(subject.toString());
		return row == null ? 0 : row.durationOfRequestProcessing;
	}

	public Float getAvgShippingDurationOfResponse(RequestSubject subject)
	{
		TimingDataRow row = statistics.get(subject.toString());
		return row == null ? 0 : row.shippingDurationOfResponse;
	}

	public Float getAvgRequestResponseLatency(RequestSubject subject)
	{
		TimingDataRow row = statistics.get(subject.toString());
		return row == null ? 0 : row.requestResponseLatency;
	}
}
