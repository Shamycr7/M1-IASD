import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

//import GroupBy.Map;
//import GroupBy.Reduce;

public class Join {

	
	private static final String INPUT_PATH = "input-join/";
	private static final String OUTPUT_PATH = "output/join-";
	private static final Logger LOG = Logger.getLogger(Join.class.getName());

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n%6$s");

		try {
			FileHandler fh = new FileHandler("out.log");
			fh.setFormatter(new SimpleFormatter());
			LOG.addHandler(fh);
		} catch (SecurityException | IOException e) {
			System.exit(1);
		}
	}

	public static class Map extends Mapper<LongWritable, Text, Text, Text> {
		
		
		
			public final static String emptywords[] = {" "};
			public void map(LongWritable key, Text value, Context context) throws IOException ,InterruptedException {
					String line = value.toString().trim();
					String[] columns = line.split("\\|");
					int index_clé = -1;
					int index_valeur = -1;
					String id_table ;
					
					
					if (key.equals(new LongWritable(0)) || Arrays.equals(columns, emptywords)){
						return;
					}
					
					
					 if(columns.length == 9) {
							index_clé = 1;
							index_valeur = 8;
							id_table = "";
							}
					
					 else if(columns.length == 8) {
						index_clé = 0;
						index_valeur = 1;
						id_table = "c,&";
						}
					else {
						System.out.println("Column length is "+ columns.length);
						return;
					}
					
					
					
					
					
					context.write(new Text(columns[index_clé]), new Text(id_table + columns[index_valeur] ));
				
			}
	}
		
		
		
		
		
		//public final static String emptywords[] = {" "};
		//sales date category 
		//@Override
		//public void map(LongWritable key, Text value, Context context) throws IOException ,InterruptedException {
			//String line = value.toString().trim();
			//String[] columns = line.split(",");
			
			//if (Arrays.equals(columns, emptywords)){
			//	return;}
			//double sales;
			//try {
		//	sales = Double.parseDouble(columns[17]);
			//}catch(Exception e ){return;}
			//String s = columns[2] +  " | " + columns[14] + " | ";
			//context.write(new Text(s), new DoubleWritable(sales));
	//	}
		
		
		
		
		
		
		
		
		
		
	
		
		//public final static String emptywords[] = {" "};
		//sales date state 
		//@Override
		//public void map(LongWritable key, Text value, Context context) throws IOException ,InterruptedException {
		//	String line = value.toString().trim();
		//	String[] columns = line.split(",");
			
		//	if (Arrays.equals(columns, emptywords)){
			//	return;}
		//	double sales;
		//	try {
		//	sales = Double.parseDouble(columns[17]);
		//	}catch(Exception e ){return;}
		//	String s = columns[2] +  " | " + columns[10] + " | ";
		//	context.write(new Text(s), new DoubleWritable(sales));
		//}
		
		
		
		
		//profit 
		//public final static String emptywords[] = {" "};
		/*public void map(LongWritable key, Text value, Context context) throws IOException ,InterruptedException {
			String line = value.toString().trim();
			String[] columns = line.split(",");
			
			if (Arrays.equals(columns, emptywords)){
				return;}
			double profit;
			try {
			profit = Double.parseDouble(columns[20]);
			}catch(Exception e ){return;}
			
			context.write(new Text(columns[5]), new DoubleWritable(profit));
		}*/
	

	public static class Reduce extends Reducer<Text, Text, Text, Text> {

		@Override
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			
			ArrayList<Text> customers = new ArrayList<Text>(); 
			ArrayList<Text> orders = new ArrayList<Text>(); 
			
			for (Text val : values) {
				//System.out.println(val + "shamy");
				String[] txt = val.toString().split(",&");
				//System.out.println(txt + "shamy");
				if(txt.length == 2) {
					customers.add(new Text(txt[1]));
				}
				else {orders.add(val);}
				}
			
			
			
			for(Text customer : customers) {
				for(Text order : orders) {
					
					context.write(customer, order);
				}
				
			}
				
		}
	}

	//reduce de id order count sum 
	//@Override
	//public void reduce(Text key, Iterable<IntWritable> values, Context context)
	//		throws IOException, InterruptedException {
	//	int sum = 0;
	//	int count =0;
	//	for (IntWritable val : values) {
	//		sum += val.get();
	//		count++;
	//}
	//	context.write(key, new Text(count+ "\t"+sum));
	//}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		//conf.set("mapred.textoutputformat.separator",  "    |    ");

		Job job = new Job(conf, "GroupBy");

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);

		job.setOutputValueClass(Text.class); 

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH + Instant.now().getEpochSecond()));

		job.waitForCompletion(true);
	}
}

