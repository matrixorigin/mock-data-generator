package io.mo.gendata.meta;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;

public class Prefix {
    private String type = "enum";
    private ArrayList<String> values = new ArrayList<>();
    private int start = 0;
    private int end = 0;
    
    public Prefix(){
        
    }

    public Prefix(int start,int end){
        this.start = start;
        this.end = end;
        this.type = "random";
    }
    
    public void addValue(String value){
        values.add(value);
    }

    public void addValues(String[] values){
        for (String value:values) {
            this.values.add(value);
        }
    }
    
    public String getValue(){
        if(this.type.equalsIgnoreCase("enum")){
            int i = RandomUtils.nextInt(0,values.size());
            return values.get(i);
        }

        if(this.type.equalsIgnoreCase("random")){
            return String.valueOf(RandomUtils.nextInt(start,end));
        }
        
        return null;
    }
}
