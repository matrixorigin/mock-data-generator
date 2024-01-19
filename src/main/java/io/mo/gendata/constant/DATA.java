package io.mo.gendata.constant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class DATA {
    //int tpye
    public static int INT_MIN_VALUE_DEFAULT = 0;
    public static int INT_MAX_VALUE_DEFAULT = 10000;

    public static int DECIAM_LEN_DEFAULT = 10;
    public static int DECIMAL_SCALE_DEFAULT = 5;

    public static int VARCHAR_LEN = 50;


    public static int MAXVALUE[] = new int[]{0,9,99,999,9999,99999,999999,9999999,99999999,999999999};

    public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static Date START_DATE;
    public static Date END_DATE;

    static {
        try {
            START_DATE = DATE_FORMAT.parse("1970-01-01");
            END_DATE = DATE_FORMAT.parse("2030-13-31");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    //public static lock object
    public String LOCK = "lock";
}
