package io.mo.gendata.customer.etao.jinghua;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import io.mo.gendata.constant.CONFIG;
import io.mo.gendata.cos.COSUtils;
import io.mo.gendata.util.ConfUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Writer implements Runnable{
    private int id = 1;
    private double count = 0;
    
    private Random random = new Random();

    private StringBuilder records = new StringBuilder(50000000);

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
    private CountDownLatch latch = null;
    private static Logger LOG = Logger.getLogger(Writer.class.getName());
    private DecimalFormat decimalFormat = new DecimalFormat("#");
    PrintWriter writer = null;
    
    
    
    public Writer(int id, double count, CountDownLatch latch){
        this.id = id;
        this.count = count;
        if(this.count % DATA.BATCH_SIZE != 0)
            this.count += (DATA.BATCH_SIZE - this.count % DATA.BATCH_SIZE);
        
        this.latch = latch;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        int partNumber = 1;
        DecimalFormat df = new DecimalFormat("0");
        
        String fieldSeparator = ConfUtil.getFieldSeparator();
        String lineSeparator = ConfUtil.getLineSeparator();
        
        switch (ConfUtil.getStorage()) {
            case "local": {

                try {
                    writer = new PrintWriter(CONFIG.OUTPUT + "device" + id + ".tbl");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.exit(1);
                }

                for (double i = 0; i < count;) {
                    records.setLength(0);
                    String datetime = format.format(DATA.getDateTime());
                    for (int a = 0; a < DATA.DEVICE_TYPES.length; a++) {
                        for (int c = 0; c < 30; c++) {
                            for (int d = 0; d < 100; d++) {
                                for (int e = 0; e < 10; e++) {
                                    for (int f = 0; f < DATA.METRICS_NAMES.length; f++) {
                                        records.append(datetime);
                                        records.append(".");
                                        records.append(getRandom3Number());
                                        records.append(fieldSeparator);

                                        records.append(DATA.DEVICE_TYPES[a]);
                                        records.append(fieldSeparator);


                                        records.append("{\"");
                                        records.append(DATA.TAG_NAMES[0]);
                                        records.append("\":\"");
                                        records.append(DATA.DEVICE_TYPES[a]);
                                        records.append(DATA.TAG_NAMES[0]);
                                        records.append("-");
                                        records.append(c);
                                        records.append("\",\"");
                                        records.append(DATA.TAG_NAMES[1]);
                                        records.append("\":\"");
                                        records.append(DATA.DEVICE_TYPES[a]);
                                        records.append(DATA.TAG_NAMES[1]);
                                        records.append("-");
                                        records.append(d);
                                        records.append("\",\"");
                                        records.append(DATA.TAG_NAMES[2]);
                                        records.append("\":\"");
                                        records.append(DATA.DEVICE_TYPES[a]);
                                        records.append(DATA.TAG_NAMES[2]);
                                        records.append("-");
                                        records.append(e);
                                        records.append("\"}");
                                        records.append(fieldSeparator);

                                        records.append(DATA.METRICS_NAMES[f]);
                                        records.append(fieldSeparator);

                                        records.append(getRandom2Number());
                                        records.append(lineSeparator);
                                    }
                                }
                            }
                        }
                    }

                    if (records.length() > 0) {
                        writer.write(records.toString());
                        writer.flush();
                    }

                    i += DATA.BATCH_SIZE;
                    //LOG.info(decimalFormat.format(i) + " records for file[%s] has been generated ," + (int) (((double) i / count) * 100) + "% completed.");
                    LOG.info(String.format("%s records for file[%s] has been generated ,%d%% completed", decimalFormat.format(i), "device" + id + ".tbl", (int) ((i / count) * 100)));
                }

                writer.close();
                break;
            }

            case "cos": {
                COSClient cosClient = COSUtils.getCOSClient();
                String bucketName = ConfUtil.getBucket();
                String key = CONFIG.OUTPUT + "device" + id + ".tbl";
                InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, key);
                InitiateMultipartUploadResult initiateMultipartUploadResult = cosClient.initiateMultipartUpload(initiateMultipartUploadRequest);
                String uploadId = initiateMultipartUploadResult.getUploadId();
                List<PartETag> partETags = new ArrayList<>();
    
                for (double i = 0; i < count; i++) {
                    records.setLength(0);
                    String datetime = format.format(DATA.getDateTime());
                    for (int a = 0; a < DATA.DEVICE_TYPES.length; a++) {
                        for (int c = 0; c < 30; c++) {
                            for (int d = 0; d < 100; d++) {
                                for (int e = 0; e < 10; e++) {
                                    for (int f = 0; f < DATA.METRICS_NAMES.length; f++) {
                                        records.append(datetime);
                                        records.append(".");
                                        records.append(getRandom3Number());
                                        records.append(fieldSeparator);

                                        records.append(DATA.DEVICE_TYPES[a]);
                                        records.append(fieldSeparator);


                                        records.append("{\"");
                                        records.append(DATA.TAG_NAMES[0]);
                                        records.append("\":\"");
                                        records.append(DATA.DEVICE_TYPES[a]);
                                        records.append(DATA.TAG_NAMES[0]);
                                        records.append("-");
                                        records.append(c);
                                        records.append("\",\"");
                                        records.append(DATA.TAG_NAMES[1]);
                                        records.append("\":\"");
                                        records.append(DATA.DEVICE_TYPES[a]);
                                        records.append(DATA.TAG_NAMES[1]);
                                        records.append("-");
                                        records.append(d);
                                        records.append("\",\"");
                                        records.append(DATA.TAG_NAMES[2]);
                                        records.append("\":\"");
                                        records.append(DATA.DEVICE_TYPES[a]);
                                        records.append(DATA.TAG_NAMES[2]);
                                        records.append("-");
                                        records.append(e);
                                        records.append("\"}");
                                        records.append(fieldSeparator);

                                        records.append(DATA.METRICS_NAMES[f]);
                                        records.append(fieldSeparator);

                                        records.append(getRandom2Number());
                                        records.append(lineSeparator);
                                    }
                                }
                            }
                        }
                    }
                    
                    byte[] bytes = records.toString().getBytes(StandardCharsets.UTF_8);
                    if (bytes.length > CONFIG.MIN_UPLOAD_SIZE) {
                        LOG.info(String.format("Loading file: name[%s],partNumber[%d]",key,partNumber));
                        PartETag partETag = uploadPart(cosClient, bucketName, key, uploadId, partNumber, bytes);
                        partETags.add(partETag);
                        partNumber++;
                    } else {
                        LOG.error(String.format("The upoading size is less than min uploading size[%d]", CONFIG.MIN_UPLOAD_SIZE));
                        cosClient.shutdown();
                        System.exit(1);
                    }

                    i += DATA.BATCH_SIZE;
                    //LOG.info(decimalFormat.format(i) + " records for file[%s] has been generated ," + (int) (((double) i / count) * 100) + "% completed.");
                    LOG.info(String.format("%s records for file[%s] has been generated ,%d%% completed", decimalFormat.format(i), "device" + id + ".tbl", (int) ((i / count) * 100)));

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
        LOG.info(String.format("The data for file[%s] has been completedly, and costs %.2f minutes","device" + id + ".tbl",((float)(end - start)/(1000*60))));
        latch.countDown();
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

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public int getRandom3Number(){
        return random.nextInt(1000);
    }

    public float getRandom2Number(){
        return random.nextFloat()*100;
    }
    
    public static void main(String[] args){
//        CountDownLatch latch1 = new CountDownLatch(2);
//        Writer writer1 = new Writer(0,1000000,latch1);
//        Writer writer2 = new Writer(1,1000000,latch1);
//        Thread t1 = new Thread(writer1);
//        Thread t2 = new Thread(writer2);
//        t1.start();
//        t2.start();
        System.out.println(4000000.0 % DATA.BATCH_SIZE);
    }
}
