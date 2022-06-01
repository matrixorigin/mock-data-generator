package io.mo.gendata;

import io.mo.gendata.constant.CONFIG;
import io.mo.gendata.constant.DATA;
import io.mo.gendata.meta.Table;
import io.mo.gendata.util.TableParser;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Faker {
    public static void main(String[] args){

        //args[0] refer to the input file
        if(args.length > 0 && args[0] != null)
            CONFIG.INPUT = args[0];

        //args[1] refer to the output path data stored
        if(args.length > 1 && args[1] != null)
            CONFIG.OUTPUT = args[1];

        TableParser.parse(CONFIG.INPUT);

        List<Table> tables = TableParser.getTables();

        //define a thread pool,each table use a thread
        ExecutorService service = Executors.newFixedThreadPool(tables.size());

        for(int i  = 0; i < tables.size();i++){
             final Table table = tables.get(i);
            service.execute(table);
        }

        service.shutdown();
    }

}
