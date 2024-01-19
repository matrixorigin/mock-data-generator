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
        map.put("char","nextChar");
        map.put("varchar","nextVarchar");
        map.put("name","getName");
        map.put("phonenumber","getPhonenumber");
        map.put("cellphone","getCellphone");
        map.put("idcardnum","getIdCardNum");
        map.put("email","getEmail");
        map.put("address","getAddress");
        
        map.put("country","getCountry");
        map.put("auto","getAutoIncrement");
        map.put("uuid","nextUUID");
        map.put("carvin","getCarVin");
        map.put("city","getCity");
        map.put("bankaccount","getBankCardNum");
        map.put("province","getProvince");
        map.put("provincecode","getProvinceCode");
        map.put("creditcard","getCreditCardNum");

        map.put("officecardnum","getOfficalCardNum");
        map.put("carplatenum","getCarPlateNumber");
        map.put("nationality","getNationality");
        map.put("college","getCollegeName");
        map.put("qualification","getQualificationName");
        map.put("countryname","getCountryName");
        map.put("school","getSchoolName");
        map.put("usname","getUSName");
        map.put("countrycode","getCountryCode");
        map.put("swiftcode","getSwiftCode");
        map.put("degree","getDegree");
        map.put("licensenum","getDriveLicenseNum");
        map.put("qq","getQQNum");
        map.put("wechat","getWechatNum");
        map.put("HKphonenum","getHKPhoneNum");
        map.put("ipaddrv4","getIPAddrV4");
        map.put("ipaddrv6","getIPAddrV6");
        map.put("macaddr","getMACAddr");
        map.put("passport","getPassportCode");
        map.put("passportHK","getPassportHKCode");
        map.put("passportMA","getPassportMACode");
        map.put("passportHKMA","getPassportHKMACode");
        
    }

    public static String getMethodName(String type){
        return (String)map.get(type.toLowerCase());
    }
}
