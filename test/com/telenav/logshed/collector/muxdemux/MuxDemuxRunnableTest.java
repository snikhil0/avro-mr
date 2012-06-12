package com.telenav.logshed.collector.muxdemux;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

public class MuxDemuxRunnableTest extends TestCase
{

	private final static String APPID = "test";
	
	
	@Test
	public void test()
	{

		File dir = new File(MuxDemuxRunnable.COLLECTOR_LOCATION);
		int expected = -1;
		
		String files[] = dir.list();
		for (String f : files)
		{
			try
			{
				MuxDemuxValidate
						.validateCollectorsFile(dir.getAbsolutePath().concat("/").concat(f));
				expected = 0;
			}
			catch (IOException e)
			{
				Assert.assertEquals("Failed to validate collector files: " + e.getMessage(), 0, expected);
				e.printStackTrace();
			}
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.execute(new MuxDemuxRunnable(APPID));

		expected = -1;
		try
		{
			Thread.sleep((int) (1 * 60 * 1000)); // 1min
			expected = 0;
		}
		catch (Exception e)
		{
			Assert.assertEquals("Failed to validate collector files: " + e.getMessage(), 0, expected);
			e.printStackTrace();
		}
		finally
		{
			service.shutdown();
		}

		expected = -1;
		try
		{
			MuxDemuxValidate.validateMuxDemuxFile(APPID, cal.getTimeInMillis());
			expected = 0;
		}
		catch (Exception e)
		{
			Assert.assertEquals("Failed to validate mux demux file: " + e.getMessage(), 0, expected);
		}
	}
}
