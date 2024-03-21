package io.mo.gendata.meta;

import io.mo.gendata.CoreAPI;
import io.mo.gendata.constant.CONFIG;
import io.mo.gendata.constant.FIELDATTR;
import io.mo.gendata.constant.MAPPING;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Field {

    private String name;
    private String type;
    

    private String builtin;


    private String index;
    private String ref;
    private int null_ratio = 100;

    private ArrayList<String> _enum = new ArrayList();
    private ArrayList<String> prefix = new ArrayList();
    private List<Object> paras = new ArrayList<Object>();
    private Method method;
    

    private static Logger LOG = Logger.getLogger(Field.class.getName());
    private CoreAPI api =  new CoreAPI();

    public Field(){

    }



    public int getNull_ratio() {
        return null_ratio;
    }

    public void setNull_ratio(int null_ratio) {
        this.null_ratio = null_ratio;
    }


    public Field(String type){
        this.type = type;
    }

    public Field(String name,String type){
        this.type = type;
    }

    public String getBuiltin() {
        return builtin;
    }

    public void setBuiltin(String builtin) {
        if(builtin == null) return;
        this.builtin = builtin;
        try {
            String methodName = MAPPING.getMethodName(builtin);
            if(methodName == null){
                LOG.error("The builtin["+builtin+"] is invalid,please check.");
                System.exit(1);
            }
            this.method = CoreAPI.class.getDeclaredMethod(MAPPING.getMethodName(builtin));
        } catch (NoSuchMethodException e) {
            LOG.error("The builtin["+builtin+"] can not be processed.");
            System.exit(1);
        }
    }
    
    public void addEnum(String[] values){
        for(int i = 0; i < values.length; i++)
            _enum.add(values[i]);
    }
    
    public void addPrefix(String[] values){
        for(int i = 0; i < values.length; i++)
            prefix.add(values[i]);
    }

    public ArrayList<String> getPrefixList(){
        return prefix;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void addPara(Object o){
        paras.add(o);
    }

    public Object[] getParas(){
        return paras.toArray();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
    
    public boolean isIndex(){
        return index != null;
    }
    
    public boolean isRefIndex(String index){
        if (this.ref == null)
            return false;
        
        if(this.ref.equals(index))
            return true;
        return false;
    }

    public boolean isRefIndex(){
        if (this.ref == null)
            return false;
        
        return true;
    }


    public void setType(String type) {
        if(type == null) return;
        this.type = type;
//        try {
//            String methodName = MAPPING.getMethodName(type);
//            if(methodName == null){
//                LOG.error("The type["+type+"] is invalid,please check.");
//                System.exit(1);
//            }
//            if(paras.size() == 0) {
//                this.method = CoreAPI.class.getDeclaredMethod(MAPPING.getMethodName(type));
//            }
//            else{
//                Class[] p_types = new Class[paras.size()];
//                for(int i = 0; i < p_types.length;i++)
//                    p_types[i] = int.class;
//                this.method = CoreAPI.class.getDeclaredMethod(MAPPING.getMethodName(type),p_types);
//            }
//        } catch (NoSuchMethodException e) {
//            LOG.error("The type["+type+"] can not be processed.");
//            System.exit(1);
//        }
    }

    public Object nextValue(Object o){
        try {
            return method.invoke(o,paras.toArray());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object nextValue(){
        
        if(null_ratio < 100){
            int randomV = RandomUtils.nextInt(1,100);
            if(randomV < null_ratio)
                return CONFIG.NULL_VALUE;
        }
        String value = null;
        
        if(builtin != null){
            switch (builtin){
                case FIELDATTR.NAME_BUILTIN:
                    value = api.getName();
                    break;
                case FIELDATTR.PHONENUMBER_BUILTIN:
                    value = api.getPhonenumber();
                    break;
                case FIELDATTR.CELLPHONE_BUILTIN: 
                    value = api.getCellphone();
                    break;
                case FIELDATTR.IDCARD_BUILTIN: 
                    value = api.getIdCardNum();
                    break;
                case FIELDATTR.SSN_BUILTIN: 
                    value = api.getSSN();
                    break;
                case FIELDATTR.EMAIL_BUILTIN: 
                    value = api.getEmail();
                    break;
                case FIELDATTR.COUNTRY_BUILTIN: 
                    value = api.getCountry();
                    break;
                case FIELDATTR.CARVIN_BUILTIN: 
                    value = api.getCarVin();
                    break;
                case FIELDATTR.CITY_BUILTIN: 
                    value = api.getCity();
                    break;
                case FIELDATTR.BANKACCOUNT_BUILTIN: 
                    value = api.getBankCardNum();
                    break;
                case FIELDATTR.PROVINCE_BUILTIN: 
                    value = api.getProvince();
                    break;
                case FIELDATTR.PROVINCECODE_BUILTIN: 
                    value = api.getProvinceCode();
                    break;
                case FIELDATTR.ADDRESS_BUILTIN: 
                    value = api.getAddress();
                    break;
                case FIELDATTR.OFFICECARDNUM_BUILTIN: 
                    value = api.getOfficalCardNum();
                    break;
                case FIELDATTR.CARPLATENUM_BUILTIN: 
                    value = api.getCarPlateNumber();
                    break;
                case FIELDATTR.NATIONALITY_BUILTIN: 
                    value = api.getNationality();
                    break;
                case FIELDATTR.COLLAGE_BUILTIN: 
                    value = api.getCollegeName();
                    break;
                case FIELDATTR.QUALIFICATION_BUILTIN: 
                    value = api.getQualificationName();
                    break;
                case FIELDATTR.COUNTRYNAME_BUILTIN: 
                    value = api.getCountryName();
                    break;
                case FIELDATTR.COUNTRYCODE_BUILTIN: 
                    value = api.getCountryCode();
                    break;
                case FIELDATTR.SCHOOL_BUILTIN: 
                    value = api.getSchoolName();
                    break;
                case FIELDATTR.USNAME_BUILTIN: 
                    value = api.getUSName();
                    break;
                case FIELDATTR.SWIFTCODE_BUILTIN: 
                    value = api.getSwiftCode();
                    break;
                case FIELDATTR.DEGREE_BUILTIN: 
                    value = api.getDegree();
                    break;
                case FIELDATTR.LICENSENUM_BUILTIN: 
                    value = api.getDriveLicenseNum();
                    break;
                case FIELDATTR.QQ_BUILTIN: 
                    value = api.getQQNum();
                    break;
                case FIELDATTR.WECHAT_BUILTIN: 
                    value = api.getWechatNum();
                    break;
                case FIELDATTR.HKPHONENUM_BUILTIN: 
                    value = api.getHKPhoneNum();
                    break;
                case FIELDATTR.PASSPORT_BUILTIN: 
                    value = api.getPassportCode();
                    break;
                case FIELDATTR.PASSPORTHK_BUILTIN: 
                    value = api.getPassportHKCode();
                    break;
                case FIELDATTR.PASSPORTMA_BUILTIN: 
                    value = api.getPassportMACode();
                    break;
                case FIELDATTR.PASSPORTHKMA_BUILTIN: 
                    value = api.getPassportHKMACode();
                    break;
                case FIELDATTR.IPADDRV4_BUILTIN: 
                    value = api.getIPAddrV4();
                    break;
                case FIELDATTR.IPADDRV6_BUILTIN: 
                    value = api.getIPAddrV6();
                    break;
                case FIELDATTR.MACADDR_BUILTIN: 
                    value = api.getMACAddr();
                    break;
            }
        }

        if(type != null){
            switch (type){
                case FIELDATTR.AUTO_TPYE:
                    value = api.getAutoIncrement();
                    break;
                    
                case FIELDATTR.INT_TYPE : {
                    int x = (Integer) paras.get(0);
                    int y = (Integer) paras.get(1);
                    value = String.valueOf(api.nextInt(x, y));
                    break;
                }
                case FIELDATTR.DECIMAL_TYPE : {
                    int x = (Integer) paras.get(0);
                    int y = (Integer) paras.get(1);
                    value = api.nextDecimal(x, y);
                    break;
                }
                case FIELDATTR.DATE_TYPE : {
                    value = api.nextDate();
                    break;
                }
                case FIELDATTR.DATETIME_TYPE : {
                    value = api.nextDateTime();
                    break;
                }
                case FIELDATTR.VARCHAR_TYPE : {
                    int x = (Integer)paras.get(0);
                    value = api.nextVarchar(x);
                    break;
                }
                case FIELDATTR.CHAR_TYPE : {
                    int x = (Integer)paras.get(0);
                    value = api.nextChar(x);
                    break;
                }

                case FIELDATTR.UUID_TYPE: {
                    value = api.nextUUID();
                    break;
                }
            }
        }
        
        if(_enum.size() > 0){
           int index = RandomUtils.nextInt(0,_enum.size());
           value = _enum.get(index);
        }


        if(prefix.size() > 0){
            int randomV = RandomUtils.nextInt(0,prefix.size());
            value = prefix.get(randomV)+ value;
        }
        
        return value;
    }


    public CoreAPI getApi() {
        return api;
    }

    public void setApi(CoreAPI api) {
        this.api = api;
    }

    public Field clone(){
        Field field = new Field();
        field.setName(name);
        for(int i = 0; i < paras.size();i++){
            field.addPara(paras.get(i));
        }
        field.setBuiltin(builtin);
        field.setType(type);
        field.addEnum((String[])_enum.toArray(new String[_enum.size()]));
        field.setIndex(index);
        field.setRef(ref);
        field.setNull_ratio(null_ratio);
        field.addPrefix((String[])prefix.toArray(new String[prefix.size()]));
        //System.out.println(field.getPrefixList().size());
        return field;
    }

    public static void main(String args[]){
        CoreAPI faker = new CoreAPI();
        /*Class<CoreAPI>  clazz = CoreAPI.class;
        Class[] p_types = new Class[]{int.class,int.class};
        Object[] p_values = new Object[]{1,5};
        try {
            Method method = clazz.getDeclaredMethod("nextInt",p_types);
            System.out.println("method = "+method);
            System.out.println(" value = " + method.invoke(faker,p_values));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }*/
        System.out.println(faker.nextVarchar(10));
    }
}
