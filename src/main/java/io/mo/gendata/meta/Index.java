package io.mo.gendata.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Index {
    private String name;
    private String field_name;
    private Object lastValue;
    private ArrayList<Field> refFields = new ArrayList<Field>();
    private HashMap<Object,Boolean> values = new HashMap();
    private ConcurrentHashMap indexRefValues = new ConcurrentHashMap();
    
    public Index(){
    }

    public Index(String name){
        this.name = name;
    }

    public Index(String name,String field_name){
        this.name = name;
        this.field_name = field_name;
    }
    
    public void addRefField(Field field){
        refFields.add(field);
    }
    
    public void addIndexRefValue(Object indexValue, String field_name,Object field_vale){
        indexRefValues.put(indexValue.toString().concat(field_name),field_vale);
    }
    
    public Object getIndexRefValues(Object indexValue, String field_name){
        return indexRefValues.get(indexValue.toString().concat(field_name));
    }
    
    public boolean indexValueExisted(Object value){
        return values.containsKey(value);
    }
    
    public void addIndexValue(Object value){
        values.put(value,true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }
    
    public ArrayList<Field> getRefFields(){
        return refFields;
    }
    
}
