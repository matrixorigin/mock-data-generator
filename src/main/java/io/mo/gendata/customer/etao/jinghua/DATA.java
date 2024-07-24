package io.mo.gendata.customer.etao.jinghua;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DATA {
    public static double DEFAUT_COUNT = 100000000;
    public static int DEFAULT_WORKER_COUNT = 10;

    public static double BATCH_SIZE = 30*3000*5;
    public static Calendar STARTTIME = Calendar.getInstance();
    
    public static String[] DEVICE_TYPES = {"芯片","电路板","插槽"};
    public static String[] TAG_NAMES = {"产线","面板","设备编码"};
    public static String[] METRICS_NAMES = {"长度","高度","宽度","湿度","温度"};
    
    
    public synchronized static Date getDateTime(){
         STARTTIME.add(Calendar.SECOND,1);
         return STARTTIME.getTime();
    }
}
