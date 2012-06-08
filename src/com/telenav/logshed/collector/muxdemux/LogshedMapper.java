package com.telenav.logshed.collector.muxdemux;

import java.io.IOException;

import org.apache.avro.generic.GenericData;
import org.apache.avro.mapred.AvroCollector;
import org.apache.avro.mapred.AvroMapper;
import org.apache.avro.mapred.Pair;
import org.apache.hadoop.mapred.Reporter;

import com.telenav.logshed.collector.utils.LogshedCollectorUtils;

public class LogshedMapper extends
		AvroMapper<GenericData.Record, Pair<LogKeyWritable, GenericData.Record>>
{

	@Override
	public void map(GenericData.Record datum,
			AvroCollector<Pair<LogKeyWritable, GenericData.Record>> collector, Reporter reporter)
			throws IOException
	{
		long tstamp = ((Long) datum.get("timestamp")).longValue();
		String keyPath = LogshedCollectorUtils.getLogshedKeyHour(tstamp,
				((String) datum.get("appid")));

		LogKeyWritable key = new LogKeyWritable(keyPath, tstamp);
		Pair<LogKeyWritable, GenericData.Record> pair = new Pair<LogKeyWritable, GenericData.Record>(
				key, datum);
		collector.collect(pair);
	}
}
