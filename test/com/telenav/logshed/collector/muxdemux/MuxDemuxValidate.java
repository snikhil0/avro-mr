package com.telenav.logshed.collector.muxdemux;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.DatumReader;

import com.telenav.logshed.collector.utils.LogshedCollectorUtils;

public class MuxDemuxValidate
{

	public static void validateMuxDemuxFile(String appid, long timestamp) throws IOException
	{

		DatumReader<GenericData.Record> reader = new GenericDatumReader<Record>(
				LogshedCollectorUtils.getResourceSchema());
		String outpath = LogshedCollectorUtils.getLogshedKeyHour(timestamp, appid);

		String MUXDEMUX_FILE = outpath.concat("part-r-00000");
		InputStream in = new BufferedInputStream(new FileInputStream(MUXDEMUX_FILE));
		DataFileStream<GenericData.Record> muxdemux = new DataFileStream<GenericData.Record>(in,
				reader);
		for (GenericData.Record r : muxdemux)
		{
			System.out.println(r.toString());
		}
	}

	public static void validateCollectorsFile(String filePath) throws IOException
	{

		DatumReader<GenericData.Record> reader = new GenericDatumReader<Record>(
				LogshedCollectorUtils.getResourceSchema());

		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		DataFileStream<GenericData.Record> muxdemux = new DataFileStream<GenericData.Record>(in,
				reader);
		for (GenericData.Record r : muxdemux)
		{
			System.out.println(r.toString());
		}
	}

}
