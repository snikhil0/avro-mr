package com.telenav.logshed.collector.muxdemux;

import org.apache.avro.Schema;
import org.apache.avro.mapred.AvroJob;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

import com.telenav.logshed.collector.utils.LogshedCollectorUtils;

@SuppressWarnings("deprecation")
public class MuxDemuxJob extends Configured implements Tool
{
	public static final Schema IN_SCHEMA = LogshedCollectorUtils.getResourceSchema();
	public static final Schema OUT_SCHEMA = LogshedCollectorUtils.getResourceSchema();

	@Override
	public int run(String[] args) throws Exception
	{
		JobConf jobConf = new JobConf(LogshedCollectorUtils.getLocalHadoopConfiguartion());
		Job job = new Job(jobConf, "muxdemux_job");
		FileInputFormat.setInputPaths(job, new Path(args[0]));

		Path outPath = new Path(args[1]);
		FileOutputFormat.setOutputPath(job, outPath);
		job.setJarByClass(MuxDemuxJob.class);

		AvroJob.setInputSchema(jobConf, IN_SCHEMA);
		AvroJob.setOutputSchema(jobConf, OUT_SCHEMA);

		AvroJob.setMapperClass(jobConf, LogshedMapper.class);
		AvroJob.setReducerClass(jobConf, LogshedReducer.class);

		return job.waitForCompletion(true) ? 0 : 1;

	}
}
