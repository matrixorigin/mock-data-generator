package io.mo.gendata.meta;

import io.mo.gendata.CoreAPI;
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

    private ArrayList _enum = new ArrayList();
    private List<Object> paras = new ArrayList<Object>();
    private Method method;


    private static Logger LOG = Logger.getLogger(Field.class.getName());
    private CoreAPI api =  new CoreAPI();

    public Field(){

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
        try {
            String methodName = MAPPING.getMethodName(type);
            if(methodName == null){
                LOG.error("The type["+type+"] is invalid,please check.");
                System.exit(1);
            }
            if(paras.size() == 0) {
                this.method = CoreAPI.class.getDeclaredMethod(MAPPING.getMethodName(type));
            }
            else{
                Class[] p_types = new Class[paras.size()];
                for(int i = 0; i < p_types.length;i++)
                    p_types[i] = int.class;
                this.method = CoreAPI.class.getDeclaredMethod(MAPPING.getMethodName(type),p_types);
            }
        } catch (NoSuchMethodException e) {
            LOG.error("The type["+type+"] can not be processed.");
            System.exit(1);
        }
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
        
        if(builtin != null){
            switch (builtin){
                case FIELDATTR.NAME_BUILTIN: return api.getName();
                case FIELDATTR.PHONENUMBER_BUILTIN: return api.getPhonenumber();
                case FIELDATTR.CELLPHONE_BUILTIN: return api.getCellphone();
                case FIELDATTR.IDCARD_BUILTIN: return api.getIdCardNum();
                case FIELDATTR.SSN_BUILTIN: return api.getSSN();
                case FIELDATTR.EMAIL_BUILTIN: return api.getEmail();
                case FIELDATTR.COUNTRY_BUILTIN: return api.getCountry();
                case FIELDATTR.CARVIN_BUILTIN: return api.getCarVin();
                case FIELDATTR.CITY_BUILTIN: return api.getCity();
                case FIELDATTR.BANKACCOUNT_BUILTIN: return api.getBankCardNum();
                case FIELDATTR.PROVINCE_BUILTIN: return api.getProvince();
                case FIELDATTR.PROVINCECODE_BUILTIN: return api.getProvinceCode();
                case FIELDATTR.ADDRESS_BUILTIN: return api.getAddress();
                case FIELDATTR.OFFICECARDNUM_BUILTIN: return api.getOfficalCardNum();
                case FIELDATTR.CARPLATENUM_BUILTIN: return api.getCarPlateNumber();
                case FIELDATTR.NATIONALITY_BUILTIN: return api.getNationality();
                case FIELDATTR.COLLAGE_BUILTIN: return api.getCollegeName();
                case FIELDATTR.QUALIFICATION_BUILTIN: return api.getQualificationName();
                case FIELDATTR.COUNTRYNAME_BUILTIN: return api.getCountryName();
                case FIELDATTR.COUNTRYCODE_BUILTIN: return api.getCountryCode();
                case FIELDATTR.SCHOOL_BUILTIN: return api.getSchoolName();
                case FIELDATTR.USNAME_BUILTIN: return api.getUSName();
                case FIELDATTR.SWIFTCODE_BUILTIN: return api.getSwiftCode();
                case FIELDATTR.DEGREE_BUILTIN: return api.getDegree();
                case FIELDATTR.LICENSENUM_BUILTIN: return api.getDriveLicenseNum();
                case FIELDATTR.QQ_BUILTIN: return api.getQQNum();
                case FIELDATTR.WECHAT_BUILTIN: return api.getWechatNum();
                case FIELDATTR.HKPHONENUM_BUILTIN: return api.getHKPhoneNum();
                case FIELDATTR.PASSPORT_BUILTIN: return api.getPassportCode();
                case FIELDATTR.PASSPORTHK_BUILTIN: return api.getPassportHKCode();
                case FIELDATTR.PASSPORTMA_BUILTIN: return api.getPassportMACode();
                case FIELDATTR.PASSPORTHKMA_BUILTIN: return api.getPassportHKMACode();
                case FIELDATTR.IPADDRV4_BUILTIN: return api.getIPAddrV4();
                case FIELDATTR.IPADDRV6_BUILTIN: return api.getIPAddrV6();
                case FIELDATTR.MACADDR_BUILTIN: return api.getMACAddr();
            }
        }

        if(type != null){
            switch (type){
                case FIELDATTR.AUTO_TPYE:
                    return api.getAutoIncrement();
                    
                case FIELDATTR.INT_TYPE : {
                    int x = (Integer) paras.get(0);
                    int y = (Integer) paras.get(1);
                    return api.nextInt(x, y);
                }
                case FIELDATTR.DECIMAL_TYPE : {
                    int x = (Integer) paras.get(0);
                    int y = (Integer) paras.get(1);
                    return api.nextDecimal(x, y);
                }
                case FIELDATTR.DATE_TYPE : {
                    return api.nextDate();
                }
                case FIELDATTR.DATETIME_TYPE : {
                    return api.nextDateTime();
                }
                case FIELDATTR.VARCHAR_TYPE : {
                    int x = (Integer)paras.get(0);
                    return api.nextVarchar(x);
                }
                case FIELDATTR.CHAR_TYPE : {
                    int x = (Integer)paras.get(0);
                    return api.nextChar(x);
                }

                case FIELDATTR.UUID_TYPE: {
                    return api.nextUUID();
                }
            }
        }
        
        if(_enum.size() > 0){
           int index = RandomUtils.nextInt(0,_enum.size());
           return _enum.get(index);
        }
        
        return null;
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
