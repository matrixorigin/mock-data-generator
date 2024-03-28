package io.mo.gendata.benchmark.sysbench;

import io.mo.gendata.constant.CONFIG;
import io.mo.gendata.util.ConfUtil;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Generator {
    
    public  Random random = new Random();

    private static Logger LOG = Logger.getLogger(Generator.class.getName());
    
    public Generator(){
        
    }
    
    public static void main(String[] args){
        
        int tbl_count = 0;
        double tbl_size = 0;
        boolean auto_incr = false;
        int writeWorkerNum = 10;

        Options options = new Options();
        options.addOption("n","tables",true,"For sysbench data prepare, set table count");
        options.addOption("s","table_size",true,"for sysbench data prepare, set table size");
        options.addOption("a","auto_inc",true,"for sysbench data prepare, set table size");
        options.addOption("o","output",true,"for sysbench data prepare, set table size");
        options.addOption("o","file_count",true,"for sysbench data prepare, set table size");
        
        CommandLineParser parser = new DefaultParser();
        try {

            CommandLine cmd = parser.parse(options,args);

            if(cmd.hasOption("tables")) {
                tbl_count = Integer.parseInt(cmd.getOptionValue("tables"));
                LOG.info("The table count is" + cmd.getOptionValue("tables"));
            }else {
                LOG.error("Error: the table count is required by --tables");
                System.exit(1);
            }

            if(cmd.hasOption("table_size")) {
                tbl_size = Double.parseDouble(cmd.getOptionValue("table_size"));
                LOG.info("The table size is " + cmd.getOptionValue("table_size"));
            }else {
                LOG.error("Error: the table size is required by --table_size");
                System.exit(1);
            }

            if(cmd.hasOption("auto_inc")) {
                auto_incr = cmd.getOptionValue("tables").equalsIgnoreCase("true")?true:false;
                LOG.info(" Tables is with auto increment primary key" + cmd.getOptionValue("auto_inc"));
            }

            if(cmd.hasOption("output")) {
                CONFIG.OUTPUT = cmd.getOptionValue("output");
                LOG.info("The output path is " + cmd.getOptionValue("output"));
                
            }
            
            if(ConfUtil.getBatchSize() != 0)
                CONFIG.BATCH_COUNT = ConfUtil.getBatchSize();

            if(cmd.hasOption("file_count")) {
                CONFIG.FILE_COUNT = Integer.parseInt(cmd.getOptionValue("file_count"));
                LOG.info("The count of table data files is  " + cmd.getOptionValue("file_count"));
            }
                
            

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        
        if(writeWorkerNum > tbl_count)
            writeWorkerNum = tbl_count;
        
        ExecutorService executor = Executors.newFixedThreadPool(writeWorkerNum);
        CountDownLatch latch = new CountDownLatch(tbl_count);
        
        try {
            LOG.info(String.format("Start to generate sysbench data, tableCount=%d, tableSize=%.0f",tbl_count,tbl_size));
            
            long start = System.currentTimeMillis();
            
            for(int i = 1; i < tbl_count + 1 ; i++) {
                Writer writer = new Writer( i, tbl_size, auto_incr,latch);
                executor.execute(writer);
            }
            latch.await();
            executor.shutdown();
            
            long end = System.currentTimeMillis();
            
            LOG.info(String.format("Finished to generate sysbench data, tableCount=%d, tableSize=%.0f, and costs %.2f minutes",tbl_count,tbl_size,((float)(end - start)/(1000*60))));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
