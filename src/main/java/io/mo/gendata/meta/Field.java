package io.mo.gendata.meta;

import io.mo.gendata.CoreAPI;
import io.mo.gendata.constant.FIELDATTR;
import io.mo.gendata.constant.MAPPING;
import io.mo.gendata.util.TableParser;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Field {

    private String name;
    private String type;

    private String builtin;
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
            this.method = CoreAPI.class.getDeclaredMethod(MAPPING.getMethodName(builtin));
        } catch (NoSuchMethodException e) {
            LOG.error("The builtin["+builtin+"] can not be processed.");
            System.exit(1);
        }
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

    public void setType(String type) {
        if(type == null) return;
        this.type = type;
        try {
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
            }
        }

        if(type != null){
            switch (type){
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
            }
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
