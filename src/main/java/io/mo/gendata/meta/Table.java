package io.mo.gendata.meta;

import com.google.common.util.concurrent.AtomicDouble;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import io.mo.gendata.CoreAPI;
import io.mo.gendata.constant.CONFIG;
import io.mo.gendata.constant.DATA;
import io.mo.gendata.cos.COSUtils;
import io.mo.gendata.util.ConfUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Table implements Runnable{

    private String name;
    private List<Field> fields = new ArrayList<Field>();

    private int count = 0;
    private AtomicInteger pos = new AtomicInteger(1);
    private int offset = 0;
    private StringBuilder records = new StringBuilder();

    private CoreAPI api = new CoreAPI();

    private BlockingQueue[] queues;// = new ArrayBlockingQueue(CONFIG.MAX_QUEUE_SIZE);
    private ExecutorService service = Executors.newFixedThreadPool(CONFIG.THREAD_COUNT*2);

    private ReentrantLock lock = new ReentrantLock();
    
    private static Logger LOG = Logger.getLogger(Table.class.getName());
    
    private HashMap<String,Index> indexMap = new HashMap();
    
    private boolean completed = false;

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
    
    public void makeIndex(){
//        for(int i = 0; i < fields.size();i++){
//            System.out.println("field = "+fields.get(i).getName()+",index = " + fields.get(i).getIndex());
//        }
        for (Field field:fields) {
            if(field.isIndex()){
                Index index = new Index(field.getIndex(),field.getName());
                for(int i = 0 ; i < fields.size(); i++){
                    Field refField = fields.get(i);
                    if(refField.isRefIndex(index.getName()))
                        index.addRefField(refField);
                }
                indexMap.put(field.getIndex(),index);
               // System.out.println("field = "+field.getName()+",index = " + index.getName());
            }
        }
        
        for(int i = 0; i < indexMap.keySet().size(); i++){
            String indexD = indexMap.keySet().iterator().next().toString();
            Index index = (Index) indexMap.values().iterator().next();
            //System.out.println("index = " + indexD + " refs = " + index.getRefFields().get(0).getName());
        }
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
            queues = new ArrayBlockingQueue[1];
            queues[0] = new ArrayBlockingQueue(CONFIG.MAX_QUEUE_SIZE);
            Producer producer = new Producer(0);
            service.execute(producer);
        }else {
            //if count > CONFIG.THRESHOLD_MUTIL_THREAD,start multiple  producer
            queues = new ArrayBlockingQueue[CONFIG.THREAD_COUNT];
            for(int i = 0; i < CONFIG.THREAD_COUNT;i++){
                queues[i] = new ArrayBlockingQueue(CONFIG.MAX_QUEUE_SIZE);
//                int batch = count/CONFIG.THREAD_COUNT;
//                if( i == CONFIG.THREAD_COUNT -1)
//                    batch = count - i*batch;
                Producer producer = new Producer(i);
                service.execute(producer);
            }
        }
    }
    
    public void startWriter(){
        File output = new File(CONFIG.OUTPUT);
        if(!output.exists())
            output.mkdirs();
        
        for(int i = 0; i < CONFIG.FILE_COUNT; i++) {
            int batch = count/CONFIG.FILE_COUNT;
            if(i == CONFIG.FILE_COUNT - 1)
                batch = batch + count%CONFIG.FILE_COUNT;
            Writer writer = new Writer(i,batch);
            service.execute(writer);
        }
        
        
    }

    @Override
    public void run() {

        startProducer();
        startWriter();
        
        LOG.info("Now start to write the data records for the table["+name+"],please waiting...................");
        long start = System.currentTimeMillis();
        while (pos.intValue() < count){
            int current = pos.intValue();
            if(current < CONFIG.BATCH_COUNT){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                continue;
            }
            LOG.info(current + " records for table[" + name + "] has been generated ," + (int) (((double) current / count) * 100) + "% completed.");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        
        completed = true;
        service.shutdown();
        long end = System.currentTimeMillis();
        LOG.info(count + " records for table[" + name + "] has been generated ," + (int) (((double) count / count) * 100) + "% completed.");
        LOG.info("All the records for table["+name+"] has been generated completely,and costs "+(float)(end - start)/1000+" seconds.");
        //check whether the path exists,if not make the file
//        File output = new File(CONFIG.OUTPUT);
//        if(!output.exists())
//            output.mkdirs();
//        StringBuilder buffer = new StringBuilder();
//        LOG.info("Now start to write the data records for the table["+name+"],please waiting...................");
//        try {
//            FileWriter writer = new FileWriter(CONFIG.OUTPUT+"/"+name+".tbl");
//            int w_count = 1;
//            String record = null;
//            long start = System.currentTimeMillis();
//            while(true) {
//                //writer.write((String) queue.take());
//                for(int i = 0; i < queues.length; i++){
//                    record =  (String) queues[i].peek();
//                    if(record == null)
//                        continue;
//                    
//                    buffer.append(record);
//                    w_count++;
//                    if(w_count >= CONFIG.BATCH_COUNT && w_count%CONFIG.BATCH_COUNT == 0) {
//                        writer.write(buffer.toString());
//                        buffer.delete(0,buffer.length());
//                        //LOG.info(w_count + " records for table[" + name + "] has been generated ," + (int) (((double) w_count / count) * 100) + "% completed.");
//                        //writer.flush();
//                    }
//
//                    if(w_count > count)
//                        break;
//                }
//
//                if(w_count > count)
//                    break;
//                
//            }
//            writer.write(buffer.toString());
//            buffer.delete(0,buffer.length());
//            writer.flush();
//            writer.close();
//            service.shutdown();
//
//            long end = System.currentTimeMillis();
//            completed = true;
//            LOG.info("All the records for table["+name+"] has been generated completely,and costs "+(float)(end - start)/1000+" seconds.");
//
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
    }

    /*
     *define a private class implementing Runnable inf,to produce the table record to the BlockingQueue
     */
    private class Producer implements Runnable{
        private List<Field> t_fields = new ArrayList<Field>();
        private int batch = 0;
        private int id = 0;
        private StringBuilder record = new StringBuilder();
        private HashMap<String,Object> currentIndexValue = new HashMap<>();
        public Producer(int id){
            this.id = id;
            this.batch = batch;
            for(int i = 0; i < fields.size();i++){
                t_fields.add(fields.get(i).clone());
            }
        }
        
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            int i = 0;
            while(!completed){
                try {
                    queues[id].put(nextRecord());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            long end = System.currentTimeMillis();
        }

        public String nextRecord(){
//            String record = ""+t_fields.get(0).nextValue();
//            for(int i = 1 ; i < t_fields.size() - 1; i++){
//                record = record + t_fields.get(i).nextValue() + CONFIG.FIELD_SEPARATOR;
//            }
//            record = record + t_fields.get(t_fields.size() - 1).nextValue() + CONFIG.LINE_SEPARATOR;
//            return record;
            record.delete(0,record.length());
            //record.append(""+t_fields.get(0).nextValue());
            for(int i = 0 ; i < t_fields.size(); i++){
                Field t_field = t_fields.get(i);
                Object nextValue = t_field.nextValue();
                if(t_field.isIndex()){
                    Index index = indexMap.get(t_field.getIndex());
                    if(!index.indexValueExisted(nextValue)){
                        lock.lock();
                        
                        index.addIndexValue(nextValue);
                        for(Field refField:index.getRefFields()){
                            Object refNextValue = refField.nextValue();
                            index.addIndexRefValue(nextValue,refField.getName(),refNextValue);
                            
                         //   System.out.println(t_field.getIndex() + " = " + nextValue+", "+refField.getName()+" = "+refNextValue);
                        }
                        lock.unlock();
                    }
                    currentIndexValue.put(t_field.getIndex(),nextValue);
                }
                
                if(t_field.isRefIndex()){
                    nextValue = indexMap.get(t_field.getRef()).
                            getIndexRefValues(currentIndexValue.get(t_field.getRef()),t_field.getName());
                }
                record.append(nextValue);
                
                if(i < fields.size() -1)
                    record.append(CONFIG.FIELD_SEPARATOR);
                else 
                    record.append(CONFIG.LINE_SEPARATOR);
                //record.append(t_fields.get(i).nextValue() + CONFIG.FIELD_SEPARATOR);
            }
            //record.append(t_fields.get(t_fields.size() - 1).nextValue() + CONFIG.LINE_SEPARATOR);
            return record.toString();
        }
    }

    private class Writer implements Runnable {

        private int batch;

        private int id = 0;

        public Writer(int id, int batch) {
            this.id = id;
            this.batch = batch;
        }

        @Override
        public void run() {
            File output = new File(CONFIG.OUTPUT);
            if (!output.exists())
                output.mkdirs();
            StringBuilder buffer = new StringBuilder();
            int partNumber = 1;
            
            //LOG.info("Now start to write the data records for the table[" + name + "],please waiting...................");
            try {
                if(ConfUtil.getStorage().equalsIgnoreCase("local")) {
                    FileWriter writer = new FileWriter(CONFIG.OUTPUT + "/" + name + "_" + id + ".tbl");
                    int w_count = 1;
                    String record = null;
                    long start = System.currentTimeMillis();
                    while (true) {
                        //writer.write((String) queue.take());
                        for (int i = 0; i < queues.length; i++) {
                            record = (String) queues[i].poll();
                            if (record == null)
                                continue;

                            buffer.append(record);
                            w_count++;
                            if (w_count >= CONFIG.BATCH_COUNT && w_count % CONFIG.BATCH_COUNT == 0) {
                                writer.write(buffer.toString());
                                pos.addAndGet(CONFIG.BATCH_COUNT);
                                buffer.delete(0, buffer.length());
                                //LOG.info(w_count + " records for table[" + name + "] has been generated ," + (int) (((double) w_count / count) * 100) + "% completed.");
                                //writer.flush();
                            }

                            if (w_count > batch)
                                break;
                        }

                        if (w_count > batch)
                            break;

                    }
                    writer.write(buffer.toString());
                    pos.addAndGet(batch % CONFIG.BATCH_COUNT);
                    buffer.delete(0, buffer.length());
                    writer.flush();
                    writer.close();
                }

                if(ConfUtil.getStorage().equalsIgnoreCase("cos")) {
                    int w_count = 1;
                    String record = null;
                    long start = System.currentTimeMillis();
                    COSClient cosClient = COSUtils.getCOSClient();
                    String bucketName = ConfUtil.getBucket();
                    String key =CONFIG.OUTPUT + "/" + name + "_" + id + ".tbl";
                    InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, key);
                    InitiateMultipartUploadResult initiateMultipartUploadResult = cosClient.initiateMultipartUpload(initiateMultipartUploadRequest);
                    String uploadId = initiateMultipartUploadResult.getUploadId();
                    List<PartETag> partETags = new ArrayList<>();
                    while (true) {
                        //writer.write((String) queue.take());
                        for (int i = 0; i < queues.length; i++) {
                            record = (String) queues[i].poll();
                            if (record == null)
                                continue;
                            
                            buffer.append(record);
                            w_count++;


                            if (w_count < batch && batch - w_count < CONFIG.BATCH_COUNT)
                                continue;

                            if (w_count == batch) {
                                byte[] bytes = buffer.toString().getBytes(StandardCharsets.UTF_8);
                                if (bytes.length > CONFIG.MIN_UPLOAD_SIZE) {
                                    PartETag partETag = uploadPart(cosClient, bucketName, key, uploadId, partNumber, bytes);
                                    partETags.add(partETag);
                                    buffer.delete(0, records.length());
                                    pos.addAndGet(CONFIG.BATCH_COUNT);
                                } else {
                                    LOG.error(String.format("The upoading size is less than min uploading size[%d]", CONFIG.MIN_UPLOAD_SIZE));
                                    cosClient.shutdown();
                                    System.exit(1);
                                }
                            }
                            
                            if (w_count >= CONFIG.BATCH_COUNT && w_count % CONFIG.BATCH_COUNT == 0) {
                                byte[] bytes = buffer.toString().getBytes(StandardCharsets.UTF_8);
                                if (bytes.length > CONFIG.MIN_UPLOAD_SIZE) {
                                    LOG.info(String.format("Loading file: name[%s],partNumber[%d]",key,partNumber));
                                    PartETag partETag = uploadPart(cosClient, bucketName, key, uploadId, partNumber, bytes);
                                    partETags.add(partETag);
                                    partNumber++;
                                    pos.addAndGet(CONFIG.BATCH_COUNT);
                                    buffer.delete(0, buffer.length());
                                } else {
                                    LOG.error(String.format("The upoading size is less than min uploading size[%d]", CONFIG.MIN_UPLOAD_SIZE));
                                    cosClient.shutdown();
                                    System.exit(1);
                                }
                                //LOG.info(w_count + " records for table[" + name + "] has been generated ," + (int) (((double) w_count / count) * 100) + "% completed.");
                                //writer.flush();
                            }

                            if (w_count > batch)
                                break;
                        }

                        if (w_count > batch)
                            break;

                    }
                    
                    Collections.sort(partETags, new Comparator<PartETag>() {
                        @Override
                        public int compare(PartETag o1, PartETag o2) {
                            return o1.getPartNumber() - o2.getPartNumber();
                        }
                    });


                    CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
                    cosClient.completeMultipartUpload(completeMultipartUploadRequest);
                    buffer.delete(0,buffer.length());
                }
                
                
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public PartETag uploadPart(COSClient client,String bucketName,String key,String uploadId,int partNumber,byte[] bytes){

            InputStream inputStream = new ByteArrayInputStream(bytes);

            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(key);
            uploadPartRequest.setUploadId(uploadId);
            uploadPartRequest.setPartNumber(partNumber);
            uploadPartRequest.setPartSize(bytes.length);
            uploadPartRequest.setInputStream(inputStream);
            UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
            return uploadPartResult.getPartETag();
        }
    }
    

    public static void main(String args[]){
        int a = 10;
        int b = 100;
        System.out.println((int)((a/(float)b)*100));
    }
}
