package io.mo.gendata.benchmark.sysbench;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import io.mo.gendata.constant.CONFIG;
import io.mo.gendata.cos.COSUtils;
import io.mo.gendata.util.ConfUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Writer implements Runnable{
    private int id = 1;
    private double table_size = 0;
    
    private boolean auto_incr = false;
    
    private String table_name = null;
    
    private Random random = new Random();
    private StringBuilder shortBuffer = new StringBuilder();
    
    private CountDownLatch latch = null;
    private static Logger LOG = Logger.getLogger(Writer.class.getName());
    private DecimalFormat decimalFormat = new DecimalFormat("#");
    PrintWriter writer = null;
    
    
    public Writer(int id, double tbl_size, boolean auto_incr, CountDownLatch latch){
        this.id = id;
        this.table_size = tbl_size;
        this.auto_incr = auto_incr;
        table_name = "sbtest" + id;
        
        
        this.latch = latch;
    }

    @Override
    public void run() {
        StringBuilder records = new StringBuilder();
        long start = System.currentTimeMillis();
        int partNumber = 1;
        DecimalFormat df = new DecimalFormat("0");
        
        switch (ConfUtil.getStorage()) {
            case "local": {

                try {
                    writer = new PrintWriter(CONFIG.OUTPUT + table_name + ".tbl");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.exit(1);
                }

                for (double i = 0; i < table_size; i++) {
                    if (auto_incr) {
                        records.append(CONFIG.NULL_VALUE);
                        records.append(CONFIG.FIELD_SEPARATOR);
                        records.append(df.format(get_k_value()));
                        records.append(CONFIG.FIELD_SEPARATOR);
                        records.append(get_c_value());
                        records.append(CONFIG.FIELD_SEPARATOR);
                        records.append(get_pad_value());
                        if (i < table_size - 1)
                            records.append(CONFIG.LINE_SEPARATOR);
                    } else {
                        records.append(decimalFormat.format(i));
                        records.append(CONFIG.FIELD_SEPARATOR);
                        records.append(df.format(get_k_value()));
                        records.append(CONFIG.FIELD_SEPARATOR);
                        records.append(get_c_value());
                        records.append(CONFIG.FIELD_SEPARATOR);
                        records.append(get_pad_value());
                        if (i < table_size - 1)
                            records.append(CONFIG.LINE_SEPARATOR);
                    }

                    if (i > 0 && i % CONFIG.BATCH_COUNT == 0) {
                        writer.write(records.toString());
                        writer.flush();
                        records.delete(0, records.length());
                    }

                    if (i > 0 && ((i % (table_size / 100) == 0) || i % 10000000 == 0)) {
                        LOG.info(decimalFormat.format(i) + " records for table[" + table_name + "] has been generated ," + (int) (((double) i / table_size) * 100) + "% completed.");
                    }
                }

                //last batch
                if (records.length() > 0) {
                    writer.write(records.toString());
                    writer.flush();
                }

                writer.close();
                break;
            }

            case "cos": {
                COSClient cosClient = COSUtils.getCOSClient();
                String bucketName = ConfUtil.getBucket();
                String key = CONFIG.OUTPUT + table_name + ".tbl";
                InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, key);
                InitiateMultipartUploadResult initiateMultipartUploadResult = cosClient.initiateMultipartUpload(initiateMultipartUploadRequest);
                String uploadId = initiateMultipartUploadResult.getUploadId();
                List<PartETag> partETags = new ArrayList<>();

                for (double i = 0; i < table_size; i++) {
                    if (auto_incr) {
                        records.append(CONFIG.NULL_VALUE);
                        records.append(CONFIG.FIELD_SEPARATOR);
                        records.append(df.format(get_k_value()));
                        records.append(CONFIG.FIELD_SEPARATOR);
                        records.append(get_c_value());
                        records.append(CONFIG.FIELD_SEPARATOR);
                        records.append(get_pad_value());
                        if (i < table_size - 1)
                            records.append(CONFIG.LINE_SEPARATOR);
                    } else {
                        records.append(decimalFormat.format(i));
                        records.append(CONFIG.FIELD_SEPARATOR);
                        records.append(df.format(get_k_value()));
                        records.append(CONFIG.FIELD_SEPARATOR);
                        records.append(get_c_value());
                        records.append(CONFIG.FIELD_SEPARATOR);
                        records.append(get_pad_value());
                        if (i < table_size - 1)
                            records.append(CONFIG.LINE_SEPARATOR);
                    }

                    if (i > 0) {

                        //if remaining count is less than CONFIG.BATCH_COUNT,put all remaining records to a batch
                        if (i < table_size - 1 && table_size - i < CONFIG.BATCH_COUNT)
                            continue;

                        if (i == table_size - 1) {
                            byte[] bytes = records.toString().getBytes(StandardCharsets.UTF_8);
                            if (bytes.length > CONFIG.MIN_UPLOAD_SIZE) {
                                PartETag partETag = uploadPart(cosClient, bucketName, key, uploadId, partNumber, bytes);
                                partETags.add(partETag);
                                records.delete(0, records.length());
                            } else {
                                LOG.error(String.format("The upoading size is less than min uploading size[%d]", CONFIG.MIN_UPLOAD_SIZE));
                                cosClient.shutdown();
                                System.exit(1);
                            }
                            break;
                        }

                        if (i % CONFIG.BATCH_COUNT == 0) {
                            byte[] bytes = records.toString().getBytes(StandardCharsets.UTF_8);
                            if (bytes.length > CONFIG.MIN_UPLOAD_SIZE) {
                                LOG.info(String.format("Loading file: name[%s],partNumber[%d]",key,partNumber));
                                PartETag partETag = uploadPart(cosClient, bucketName, key, uploadId, partNumber, bytes);
                                partETags.add(partETag);
                                partNumber++;
                                records.delete(0, records.length());
                            } else {
                                LOG.error(String.format("The upoading size is less than min uploading size[%d]", CONFIG.MIN_UPLOAD_SIZE));
                                cosClient.shutdown();
                                System.exit(1);
                            }
                        }
                    }

                    if (i > 0 && ((i % (table_size / 100) == 0) || i % 10000000 == 0)) {
                        LOG.info(decimalFormat.format(i) + " records for table[" + table_name + "] has been generated ," + (int) (((double) i / table_size) * 100) + "% completed.");
                    }
                }

                Collections.sort(partETags, new Comparator<PartETag>() {
                    @Override
                    public int compare(PartETag o1, PartETag o2) {
                        return o1.getPartNumber() - o2.getPartNumber();
                    }
                });


                CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
                cosClient.completeMultipartUpload(completeMultipartUploadRequest);
                break;
            }
        }
        
        long end = System.currentTimeMillis();
        LOG.info(String.format("The data for table[%s] has been completedly, and costs %.2f minutes",table_name,((float)(end - start)/(1000*60))));
        latch.countDown();
    }

    public double get_k_value(){
        return RandomUtils.nextDouble(1,table_size);
    }

    public String get_c_value(){
        return getRandomChar(120);
    }

    public String get_pad_value(){
        return getRandomChar(59);
    }

    public String getRandomChar(int len){
        shortBuffer.delete(0,shortBuffer.length());
        String[] chars = new String[] { "0","1", "2", "3", "4", "5", "6", "7", "8", "9" };
        int count = len/11;
        for(int j = 0; j < count; j++) {
            for (int i = 0; i < 11; i++) {
                int index = random.nextInt(10);
                shortBuffer.append(chars[index]);
            }
            if( j != count -1)
                shortBuffer.append("-");
        }
        return shortBuffer.toString();
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
    
    public static void main(String[] args){
        DecimalFormat df = new DecimalFormat("0");
        System.out.println(df.format(RandomUtils.nextDouble(1,100000000)));
        switch (ConfUtil.getStorage()){
            case "local":
                System.out.println("local");
                break;
            case "cos":
                System.out.println("cos");
                break;
        }
    }
}
