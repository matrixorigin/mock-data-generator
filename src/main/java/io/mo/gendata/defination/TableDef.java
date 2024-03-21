package io.mo.gendata.defination;

import java.math.BigInteger;
import java.util.ArrayList;

public class TableDef {
    

    private String name;
    private BigInteger count = null;
    private ArrayList<ColumnDef>  columns = new ArrayList<>();
    
    public TableDef(){
        count = new BigInteger("10");
    }
    
    
    public TableDef(String name, String count){
        this.name = name;
        this.count = new BigInteger(count);
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.replace("`","");
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }
    
    public void addColumn(ColumnDef column){
        columns.add(column);
    }
    
    public ColumnDef getColumn(int i){
        return  columns.get(i);
    }

    public ArrayList<ColumnDef> getColumns() {
        return  columns;
    }

    public void setColumns(ArrayList<ColumnDef> columns) {
        this.    columns = columns;
    }
    
}
