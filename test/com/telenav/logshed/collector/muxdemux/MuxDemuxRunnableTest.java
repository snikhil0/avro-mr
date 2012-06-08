package com.telenav.logshed.collector.muxdemux;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Test;

public class MuxDemuxRunnableTest
{

	private final static String APPID = "test";
	
	
	@Test
	public void test()
	{

		File dir = new File(MuxDemuxRunnable.COLLECTOR_LOCATION);

		String files[] = dir.list();
		for (String f : files)
		{
			try
			{
				MuxDemuxValidate
						.validateCollectorsFile(dir.getAbsolutePath().concat("/").concat(f));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.execute(new MuxDemuxRunnable(APPID));

		try
		{
			Thread.sleep((int) (0.20 * 60 * 1000)); // 20secs
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			service.shutdown();
		}

		try
		{
			MuxDemuxValidate.validateMuxDemuxFile(APPID, cal.getTimeInMillis());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
