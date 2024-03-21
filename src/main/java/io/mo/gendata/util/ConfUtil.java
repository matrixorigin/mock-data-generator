package io.mo.gendata.util;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConfUtil {
    private static final YamlUtil mo_conf = new YamlUtil();
    private static Map conf = null;

    public static void init(){
        try {
            conf = mo_conf.getInfo("conf.yml");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    
    public static String getFieldSeparator(){
        if(conf == null) init();
        return (String)conf.get("field_separator");
    }

    public static String getLineSeparator(){
        if(conf == null) init();
        return (String) conf.get("line_separator");
    }

    public static String getEscapeSeparator(){
        if(conf == null) init();
        return (String) conf.get("escape_separator");
    }

    public static String getEncloseSeparator(){
        if(conf == null) init();
        return (String) conf.get("enclose_separator");
    }

    public static int getBatchSize(){
        if(conf == null) init();
        if(conf.get("batch_size") == null)
            return 0;
        return (int) conf.get("batch_size");
    }

    public static int getFileCount(){
        if(conf == null) init();
        if(conf.get("file_count") == null)
            return 0;
        return (int) conf.get("file_count");
    }

    public static String getStorage(){
        if(conf == null) init();
        if(conf.get("storage") == null)
            return "local";
        return (String) conf.get("storage");
    }

    public static String getSecretId(){
        if(conf == null) init();
        if(conf.get("secret_id") == null)
            return null;
        return (String) conf.get("secret_id");
    }

    public static String getSecretKey(){
        if(conf == null) init();
        if(conf.get("secret_key") == null)
            return null;
        return (String) conf.get("secret_key");
    }

    public static String getBucket(){
        if(conf == null) init();
        if(conf.get("bucket") == null)
            return null;
        return (String) conf.get("bucket");
    }

    public static String getRegion(){
        if(conf == null) init();
        if(conf.get("region") == null)
            return null;
        return (String) conf.get("region");
    }
}
