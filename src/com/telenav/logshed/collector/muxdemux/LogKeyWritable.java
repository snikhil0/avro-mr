package com.telenav.logshed.collector.muxdemux;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class LogKeyWritable implements WritableComparable<LogKeyWritable>
{
	private Text keyPath;

	public LongWritable getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(LongWritable timestamp)
	{
		this.timestamp = timestamp;
	}

	public void setKeyPath(Text keyPath)
	{
		this.keyPath = keyPath;
	}

	public Text getKeyPath()
	{
		return keyPath;
	}

	private LongWritable timestamp;

	public LogKeyWritable()
	{
		keyPath = new Text("");
		timestamp = new LongWritable(Calendar.getInstance().getTimeInMillis());
	}

	public LogKeyWritable(String key, long tstamp)
	{
		keyPath = new Text(key);
		timestamp = new LongWritable(tstamp);
	}

	@Override
	public void readFields(DataInput in) throws IOException
	{
		keyPath.readFields(in);
		timestamp.readFields(in);

	}

	@Override
	public void write(DataOutput out) throws IOException
	{
		keyPath.write(out);
		timestamp.write(out);
	}

	@Override
	public int compareTo(LogKeyWritable ap)
	{
		int cmp = keyPath.compareTo(ap.keyPath);
		if (cmp != 0)
		{
			return cmp;
		}

		return timestamp.compareTo(timestamp);
	}

	@Override
	public int hashCode()
	{
		return keyPath.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof LogKeyWritable)
		{
			LogKeyWritable ap = (LogKeyWritable) obj;
			return ap.keyPath.equals(keyPath);
		}

		return false;
	}
}
