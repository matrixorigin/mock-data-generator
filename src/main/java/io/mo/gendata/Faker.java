package io.mo.gendata;

import io.mo.gendata.constant.CONFIG;
import io.mo.gendata.meta.Table;
import io.mo.gendata.util.ConfUtil;
import io.mo.gendata.util.TableParser;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Faker {
    private static Logger LOG = Logger.getLogger(Table.class.getName());
    public static void main(String[] args){

        //args[0] refer to the input file
        if(args.length > 0 && args[0] != null)
            CONFIG.INPUT = args[0];

        //args[1] refer to the output path data stored
        if(args.length > 1 && args[1] != null)
            CONFIG.OUTPUT = args[1];

        TableParser.parse(CONFIG.INPUT);

        List<Table> tables = TableParser.getTables();
        init();
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

        if(ConfUtil.getFileCount() != 0)
            CONFIG.FILE_COUNT = ConfUtil.getFileCount();
        
        LOG.info("field_separator = " + CONFIG.FIELD_SEPARATOR);
        LOG.info("line_separator = " + CONFIG.LINE_SEPARATOR);
        LOG.info("escape_separator = " + CONFIG.ESCAPE_SEPARATOR);
        LOG.info("enclose_separator = " + CONFIG.ENCLOSE_SEPARATOR);
        LOG.info("batch_size = " + CONFIG.BATCH_COUNT);
        LOG.info("file_count = " + CONFIG.FILE_COUNT);
    }

}
