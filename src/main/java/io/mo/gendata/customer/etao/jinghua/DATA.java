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
    
    public static String[] DEVICE_TYPES = {"CPU","BOARD","SLOT"};
    public static String[] TAG_NAMES = {"LINE","PANEL","CODE"};
    public static String[] METRICS_NAMES = {"LENGTH","HEIGHT","WIDTH","HUMIDITY","TEMPERATURE"};
    
    
    public synchronized static Date getDateTime(){
         STARTTIME.add(Calendar.SECOND,1);
         return STARTTIME.getTime();
    }
}
