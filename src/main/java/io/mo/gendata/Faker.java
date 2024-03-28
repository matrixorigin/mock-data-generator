package io.mo.gendata;

import io.mo.gendata.constant.CONFIG;
import io.mo.gendata.meta.Table;
import io.mo.gendata.util.ConfUtil;
import io.mo.gendata.util.DDLParser;
import io.mo.gendata.util.TableParser;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Faker {
    private static Logger LOG = Logger.getLogger(Faker.class.getName());
    public static void main(String[] args){
        
        boolean parseDDL = false;
        boolean isTest = false;
        int testThreadCount = 0;
                
        Options options = new Options();
        options.addOption(null,"config",true,"the config file that table is defined");
        options.addOption(null,"output",true,"the output dir that table data is generated");
        options.addOption(null,"parse",true,"to parse the speified ddl file");
        options.addOption(null,"test",true,"to test performance ");
        options.addOption(null,"file_count",true,"to test performance ");
        
        CommandLineParser parser = new DefaultParser();
        try {

            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("config")) {
                CONFIG.INPUT = String.valueOf(cmd.getOptionValue("config"));
                LOG.info("The config file or dir is " + cmd.getOptionValue("tables"));
            }

            if(cmd.hasOption("parse")) {
                CONFIG.INPUT = String.valueOf(cmd.getOptionValue("parse"));
                LOG.info("The ddl file or dir that nedd to be parsed is  " + cmd.getOptionValue("tables"));
                parseDDL = true;
            }

            if(cmd.hasOption("file_count")) {
                CONFIG.FILE_COUNT = Integer.parseInt(cmd.getOptionValue("file_count"));
                LOG.info("The count of table data files is  " + cmd.getOptionValue("file_count"));
            }

            if(cmd.hasOption("output")) {
                CONFIG.OUTPUT = String.valueOf(cmd.getOptionValue("output"));
                LOG.info("The output dir is  " + cmd.getOptionValue("output"));
            }else {
                LOG.info("The output dir is default dir ./data/");
            }

            if(cmd.hasOption("test")) {
                isTest = true;
                testThreadCount = Integer.parseInt(cmd.getOptionValue("test"));
                LOG.info("Just for testing performace........");
            }
            
            
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        //if parse ddl operation
        if(parseDDL){
            DDLParser.parse(CONFIG.INPUT);
            return;
        }
        
        

        TableParser.parse(CONFIG.INPUT);

        List<Table> tables = TableParser.getTables();
        init();
        
        //if only for test
        if(isTest) {
            for (int i = 0; i < tables.size(); i++) {
                final Table table = tables.get(i);
                table.test(testThreadCount);
            }
            
            return;
        }

        //define a thread pool,each table use a thread
        ExecutorService service = Executors.newFixedThreadPool(tables.size());

        for(int i  = 0; i < tables.size();i++){
             final Table table = tables.get(i);
            service.execute(table);
        }

        service.shutdown();
    }
    
    public static void init(){
        if(ConfUtil.getFieldSeparator() != null)
            CONFIG.FIELD_SEPARATOR = ConfUtil.getFieldSeparator();

        if(ConfUtil.getLineSeparator() != null)
            CONFIG.LINE_SEPARATOR = ConfUtil.getLineSeparator();

        if(ConfUtil.getEscapeSeparator() != null)
            CONFIG.ESCAPE_SEPARATOR = ConfUtil.getEscapeSeparator();

        if(ConfUtil.getEncloseSeparator() != null)
            CONFIG.ENCLOSE_SEPARATOR = ConfUtil.getEncloseSeparator();

        if(ConfUtil.getBatchSize() != 0)
            CONFIG.BATCH_COUNT = ConfUtil.getBatchSize();

        if(ConfUtil.getFileCount() != 0 && CONFIG.FILE_COUNT == 0) {
            CONFIG.FILE_COUNT = ConfUtil.getFileCount();
        }
        
        LOG.info("field_separator = " + CONFIG.FIELD_SEPARATOR);
        LOG.info("line_separator = " + CONFIG.LINE_SEPARATOR);
        LOG.info("escape_separator = " + CONFIG.ESCAPE_SEPARATOR);
        LOG.info("enclose_separator = " + CONFIG.ENCLOSE_SEPARATOR);
        LOG.info("batch_size = " + CONFIG.BATCH_COUNT);
        LOG.info("file_count = " + CONFIG.FILE_COUNT);
    }

}
