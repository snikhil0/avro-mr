package com.telenav.logshed.collector.muxdemux;

import java.util.Calendar;

import org.apache.hadoop.util.ToolRunner;

import com.telenav.logshed.collector.utils.LogshedCollectorUtils;

/**
 * Make the MuxDemux job a runnable so it can be scheduled using a ScheduledExecutorService, where
 * by we can set how often this fires up and what is the delay
 */
public class MuxDemuxRunnable implements Runnable
{

	/**
	 * 
	 */
	private String appId;
	public final static String COLLECTOR_LOCATION = "resources/collectors/2012/06/06/17/";
	
	public MuxDemuxRunnable(String appId)
	{
		this.appId = appId;
	}

	@Override
	public void run()
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		String outPath = LogshedCollectorUtils.getLogshedKeyHour(cal.getTimeInMillis(), getAppId());
		String[] args = new String[2];
		args[0] = COLLECTOR_LOCATION;
		args[1] = outPath;

		try
		{
			ToolRunner.run(LogshedCollectorUtils.getLocalHadoopConfiguartion(), new MuxDemuxJob(),
					args);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}
}
