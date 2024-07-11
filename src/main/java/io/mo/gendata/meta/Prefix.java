package io.mo.gendata.meta;

import io.mo.gendata.constant.DATA;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;

public class Prefix {
    private String type = "enum";
    private ArrayList<String> values = new ArrayList<>();
    private int start = 0;
    private int end = 0;
    
    private String columnName = null;
    
    public Prefix(){
        
    }

    public Prefix(int start,int end){
        this.start = start;
        this.end = end;
        this.type = "random";
    }

    public Prefix(String columnName){
        this.type = "column";
        this.columnName = columnName;
    }
    
    public void addValue(String value){
        values.add(value);
    }

    public void addValues(String[] values){
        for (String value:values) {
            this.values.add(value);
        }
    }

    public void setValue(String value){
        values.set(0,value);
    }
    
    public String getValue(){
        if(this.type.equalsIgnoreCase("enum")){
            int i = RandomUtils.nextInt(0,values.size());
            return values.get(i);
        }

        if(this.type.equalsIgnoreCase("random")){
            return String.valueOf(RandomUtils.nextInt(start,end));
        }
        
        if(this.type.equalsIgnoreCase("column")){
            return DATA.COLUMN_PREFIX.get(columnName);
        }
        
        return null;
    }
}
