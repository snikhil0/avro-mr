package com.telenav.logshed.collector.muxdemux;

import java.io.IOException;

import org.apache.avro.generic.GenericData;
import org.apache.avro.mapred.AvroCollector;
import org.apache.avro.mapred.AvroReducer;
import org.apache.hadoop.mapred.Reporter;

/**
 * @author snikhil
 */
public class LogshedReducer extends
		AvroReducer<LogKeyWritable, GenericData.Record, GenericData.Record>
{

	@Override
	/**
	 * 
	 */
	public void reduce(LogKeyWritable key, Iterable<GenericData.Record> values,
			AvroCollector<GenericData.Record> collector, Reporter reporter) throws IOException
	{

		for (GenericData.Record r : values)
		{
			collector.collect(r);
		}

	}
}
