package io.mo.gendata.customer.etao.jinghua;

import io.mo.gendata.constant.CONFIG;
import io.mo.gendata.util.ConfUtil;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Generator {
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private static Logger LOG = Logger.getLogger(Generator.class.getName());
    
    public Generator(){
        
    }
    
    public static void main(String[] args){
        
        double count = DATA.DEFAUT_COUNT;
        int writeWorkerNum = 10;
        
        
        Options options = new Options();
        options.addOption("","table_size",true,"For ETAO JINGHUA customer, set row count of the table");
        options.addOption("","starttime",true,"For ETAO JINGHUA customer, set starttime of table data");
        options.addOption("o","output",true,"the output dir that table data is generated");
        
        CommandLineParser parser = new DefaultParser();
        try {

            CommandLine cmd = parser.parse(options,args);

            if(cmd.hasOption("table_size")) {
                count = Double.parseDouble(cmd.getOptionValue("table_size"));
                if(count == 0){
                    count = DATA.DEFAUT_COUNT;
                }
                LOG.info("The row count is" + count);
            }

            if(cmd.hasOption("starttime")) {
                DATA.STARTTIME.setTime(format.parse(cmd.getOptionValue("starttime")));
                LOG.info("The starttime is" + cmd.getOptionValue("starttime"));
            }

            if(cmd.hasOption("output")) {
                CONFIG.OUTPUT = cmd.getOptionValue("output");
                LOG.info("The data output dir is" + cmd.getOptionValue("output"));
            }
            
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        if(count < DATA.BATCH_SIZE * writeWorkerNum){
            writeWorkerNum = 1;
        }

        ExecutorService executor = Executors.newFixedThreadPool(writeWorkerNum);
        CountDownLatch latch = new CountDownLatch(writeWorkerNum);
        
        try {
            LOG.info(String.format("Start to generate data for customer[etao-jinghua], count=%.0f",count));
            
            long start = System.currentTimeMillis();

            double perCount = (int)(count/writeWorkerNum);
            double lastCount = 0;
            for(int i = 1; i < writeWorkerNum + 1 ; i++) {
                Writer writer = new Writer( i, perCount,latch);
                lastCount += writer.getCount();
                executor.execute(writer);
            }
            latch.await();
            executor.shutdown();
            
            long end = System.currentTimeMillis();
            
            LOG.info(String.format("Finished to generate data for customer[etao-jinghua], count=%.0f, and costs %.2f minutes",lastCount,((float)(end - start)/(1000*60))));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
