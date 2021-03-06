package io.mo.gendata.meta;

import io.mo.gendata.CoreAPI;
import io.mo.gendata.constant.DATA;
import io.mo.gendata.constant.CONFIG;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Table implements Runnable{

    private String name;
    private List<Field> fields = new ArrayList<Field>();

    private int count = 0;
    private StringBuffer records = new StringBuffer();

    private CoreAPI api = new CoreAPI();

    private BlockingQueue queue = new ArrayBlockingQueue(CONFIG.MAX_QUEUE_SIZE);
    private ExecutorService service = Executors.newFixedThreadPool(CONFIG.THREAD_COUNT);

    private static Logger LOG = Logger.getLogger(Table.class.getName());

    public Table(){

    }

    public Table(String name){
        this.name = name;
    }

    public Table(String name,int count){
        this.name = name;
        this.count = count;
    }

    public void addField(Field field){
        fields.add(field);
        //field.setApi(api);
    }

    public List<Field> getFields(){
        return fields;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String nextRecord(){

        String record = ""+fields.get(0).nextValue(api);
        for(int i = 1 ; i < fields.size() - 1; i++){
            record = record + fields.get(i).nextValue(api) + CONFIG.FIELD_SEPARATOR;
        }
        record = record + fields.get(fields.size() - 1).nextValue(api) + CONFIG.LINE_SEPARATOR;
        return record;
    }

    public String nextRecord(int size){
        long start = System.currentTimeMillis();
        for(int i = 0; i < size; i++){
            records.append(""+fields.get(0).nextValue(api)+ CONFIG.FIELD_SEPARATOR);
            for(int j = 1 ; j < fields.size() - 1; j++){
                records.append(fields.get(j).nextValue(api) + CONFIG.FIELD_SEPARATOR);
            }
            records.append(fields.get(fields.size() - 1).nextValue(api) + CONFIG.LINE_SEPARATOR);
        }
        long end = System.currentTimeMillis();
        //LOG.info("collect costs "+(float)(end - start)/1000+" seconds.");

        return records.toString();
    }

    public void test(){
        long start = System.currentTimeMillis();
        for(int i = 0; i < count; i++){
            for(int j = 0 ; j < fields.size() ; j++){
                fields.get(j).nextValue();
            }
            //records.append(fields.get(fields.size() - 1).nextValue(api) + SEPARATOR.LINE_SEPARATOR);
        }
        long end = System.currentTimeMillis();
        LOG.info("costs "+(float)(end - start)/1000+" seconds.");
    }


    public void startProducer(){
        //if count <= CONFIG.THRESHOLD_MUTIL_THREAD,only one producer
        if( count <= CONFIG.THRESHOLD_MUTIL_THREAD){
            Producer producer = new Producer(count);
            service.execute(producer);
        }else {
            //if count > CONFIG.THRESHOLD_MUTIL_THREAD,start multiple  producer
            for(int i = 0; i < CONFIG.THREAD_COUNT;i++){
                int batch = count/CONFIG.THREAD_COUNT;
                if( i == CONFIG.THREAD_COUNT -1)
                    batch = count - i*batch;
                Producer producer = new Producer(batch);
                service.execute(producer);
            }
        }
    }

    @Override
    public void run() {

        startProducer();
        //check whether the path exists,if not make the file
        File output = new File(CONFIG.OUTPUT);
        if(!output.exists())
            output.mkdirs();

        LOG.info("Now start to write the data records for the table["+name+"],please waiting...................");
        try {
            FileWriter writer = new FileWriter(CONFIG.OUTPUT+"/"+name+".tbl");
            int w_count = 1;
            long start = System.currentTimeMillis();
            while(w_count <= count) {
                writer.write((String) queue.take());
                w_count++;
                if(w_count >= CONFIG.BATCH_COUNT && w_count%CONFIG.BATCH_COUNT == 0)
                    LOG.info(w_count + " records for table["+name+"] has been generated completely.");
                writer.flush();
            }
            writer.close();
            long end = System.currentTimeMillis();
            LOG.info("All the records for table["+name+"] has been generated completely,and costs "+(float)(end - start)/1000+" seconds.");
            service.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /*
     *define a private class implementing Runnable inf,to produce the table record to the BlockingQueue
     */
    private class Producer implements Runnable{
        private List<Field> t_fields = new ArrayList<Field>();
        private int batch = 0;
        public Producer(int batch){
            this.batch = batch;
            for(int i = 0; i < fields.size();i++){
                t_fields.add(fields.get(i).clone());
            }
        }



        @Override
        public void run() {
            long start = System.currentTimeMillis();
            int i = 0;
            for( ; i < batch; i++){
                try {
                    queue.put(nextRecord());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            long end = System.currentTimeMillis();
        }

        public String nextRecord(){
            String record = ""+t_fields.get(0).nextValue();
            for(int i = 1 ; i < t_fields.size() - 1; i++){
                record = record + t_fields.get(i).nextValue() + CONFIG.FIELD_SEPARATOR;
            }
            record = record + t_fields.get(t_fields.size() - 1).nextValue() + CONFIG.LINE_SEPARATOR;
            return record;
        }
    }

    public static void main(String args[]){
    }
}
