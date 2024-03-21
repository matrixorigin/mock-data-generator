package io.mo.gendata.defination;

public class ColumnDef {
    private String name;
    private String type;
    
    public ColumnDef(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.replace("`","");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
