package v4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;


public class MaxTemperatureMapperTest {

    @Test
    public void parsesValidRecord() throws IOException, InterruptedException {
        String year = "0043011990999991950051518004+68750+023550FM-12+0382";
        String temperature = "99999V0203201N00261220001CN9999999N9-00111+99999999999";
        Text value = new Text(year + temperature);
    
        new MapDriver<LongWritable, Text, Text, IntWritable>()
            .withMapper(new MaxTemperatureMapper())
            .withInput(new LongWritable(0), value)
            .withOutput(new Text("1950"), new IntWritable(-11))
            .runTest();
    }
    
    @Test
    public void parsesMissingTemperature() throws IOException, InterruptedException {
        String year = "0043011990999991950051518004+68750+023550FM-12+0382";
        String temperature = "99999V0203201N00261220001CN9999999N9+99991+99999999999";
        Text value = new Text(year + temperature);
  
        new MapDriver<LongWritable, Text, Text, IntWritable>()
            .withMapper(new MaxTemperatureMapper())
            .withInput(new LongWritable(0), value)
            .runTest();
    }
  
    @Test
    public void parsesMalformedTemperature() throws IOException, InterruptedException {
        String year = "0335999999433181957042302005+37950+139117SAO  +0004";
        String temperature = "RJSN V02011359003150070356999999433201957010100005+353";
        Text value = new Text(year + temperature);
  
        Counters counters = new Counters();
        new MapDriver<LongWritable, Text, Text, IntWritable>()
            .withMapper(new MaxTemperatureMapper())
            .withInput(new LongWritable(0), value)
            .withCounters(counters)
            .runTest();
        Counter c = counters.findCounter(MaxTemperatureMapper.Temperature.MALFORMED);
        assertThat(c.getValue(), is(1L));
    }
}  