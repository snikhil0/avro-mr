package com.telenav.logshed.collector.utils;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.avro.Schema;
import org.apache.hadoop.conf.Configuration;

/**
 * Logshed configuration and setup is handled by this class. Mostly by static funcitons.
 */
public class LogshedCollectorUtils
{

	private static final String COLLECTOR = "collector";
	public static final String COLLECTORS = "collectors";
	private static final int TIME_WINDOW_IN_MINS = 5;

	/**
	 * @return The hadoop configuration for dev deployment of logshed
	 */
	public static Configuration getLocalHadoopConfiguartion()
	{
		Configuration conf = new Configuration();
		conf.addResource("config/local/core-site.xml");
		conf.addResource("config/local/hdfs-site.xml");
		conf.addResource("config/local/mapred-site.xml");
		conf.addResource("config/local/fair-scheduler.xml");
		return conf;
	}

	public static String getTimeBasedDir(long timestamp)
	{
		DateFormat dirFormat = new SimpleDateFormat("yyyy/MM/dd/HH/");
		String dir = "";
		return dirFormat.format(new Date(timestamp));

	}

	public static String getAppIdTimeBasedDir(long timestamp, String appId)
	{
		return "/".concat(appId).concat("/")
				.concat(LogshedCollectorUtils.getTimeBasedDir(timestamp));
	}

	public static String getDfsLocation(long timestamp) throws UnknownHostException
	{
		Random rand = new Random(timestamp);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

		InetAddress thisIp = InetAddress.getLocalHost();
		String filename = COLLECTOR.concat("-").concat(thisIp.getHostAddress()).concat("-")
				.concat(format.format(new Date(timestamp))).concat("-")
				.concat(String.valueOf(timestamp + rand.nextInt()));
		String dir = getAppIdTimeBasedDir(timestamp, COLLECTORS);
		return dir.concat(filename);
	}

	public static String getLogshedKeyHour(long tstamp, String appId)
	{
		return getAppIdTimeBasedDir(tstamp, String.format("logshed/%s", appId));
	}

	public static String getLogshedKeyMinute(long tstamp, String appId)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(tstamp);

		int mins = cal.get(Calendar.MINUTE);
		int min5min = TIME_WINDOW_IN_MINS * (mins / TIME_WINDOW_IN_MINS);

		return getLogshedKeyHour(tstamp, appId).concat(String.format("%02d", min5min));

	}

	@SuppressWarnings("deprecation")
	public static Schema getResourceSchema()
	{
		try
		{
			return Schema.parse(new File("resources/collectorStore.avsc"));
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
