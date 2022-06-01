package io.mo.gendata.constant;

import java.util.HashMap;
import java.util.Map;

public class MAPPING {
    private static Map map = new HashMap();
    static {
        map.put("int","nextInt");
        map.put("decimal","nextDecimal");
        map.put("date","nextDate");
        map.put("datetime","nextDateTime");
        map.put("varchar","nextVarchar");
        map.put("name","getName");
        map.put("phonenumber","getPhonenumber");
        map.put("cellphone","getCellphone");
        map.put("idcardnum","getIdCardNum");
        map.put("email","getEmail");
        map.put("country","getCountry");
    }

    public static String getMethodName(String type){
        return (String)map.get(type);
    }
}
