package io.mo.gendata.util;
import io.mo.gendata.constant.CONFIG;
import io.mo.gendata.defination.ColumnDef;
import io.mo.gendata.defination.PRECISSION;
import io.mo.gendata.defination.TableDef;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DDLParser {
    private static BufferedReader reader = null;
    private static StringBuffer content = new StringBuffer();
    private static ArrayList<TableDef> tableDefs = new ArrayList<>();
    private static Logger LOG = Logger.getLogger(DDLParser.class.getName());
    
    public static void parse(String ddlFile){
        File file = new File(ddlFile);
        
        if(!file.exists()){
            LOG.error(String.format("The input file or dir[%s] does not exists, please check...",ddlFile));
            System.exit(1);
        }
        
        if(file.isFile()){
            try {
                reader = new BufferedReader(new FileReader(ddlFile));
                String line = reader.readLine();
                while (line != null){
                    line = line.toLowerCase();
                    if(line.contains(" comment "))
                        line = line.substring(0,line.indexOf(" comment "));
                    content.append(line + " ");
                    line = reader.readLine();
                }

                String[] items = content.toString().split(" ");
                TableDef tableDef = null;
                for(int i = 0; i < items.length - 1 ; i++){
                    if(isTableName(items,i)){
                        tableDef = new TableDef();
                        tableDefs.add(tableDef);
                        tableDef.setName(items[i]);
                    }
                    
                    

                    if(isTinyIntType(items,i)){
                        ColumnDef columnDef = new ColumnDef();
                        tableDef.addColumn(columnDef);
                        columnDef.setName(items[i]);
                        if(isAutoIncrementType(items,i))
                            columnDef.setType("auto");
                        else 
                            columnDef.setType("int"+ PRECISSION.TINYINT_DEFAULT);
                    }

                    if(isTinyIntUnsignedType(items,i)){
                        ColumnDef columnDef = new ColumnDef();
                        tableDef.addColumn(columnDef);
                        columnDef.setName(items[i]);
                        if(isAutoIncrementType(items,i))
                            columnDef.setType("auto");
                        else
                            columnDef.setType("int"+ PRECISSION.TINYINT_UNSIGNED_DEFAULT);
                    }

                    if(isSmallIntType(items,i)){
                        ColumnDef columnDef = new ColumnDef();
                        tableDef.addColumn(columnDef);
                        columnDef.setName(items[i]);
                        if(isAutoIncrementType(items,i))
                            columnDef.setType("auto");
                        else
                            columnDef.setType("int"+ PRECISSION.SMALLINT_DEFAULT);
                    }

                    if(isSmallIntUnsignedType(items,i)){
                        ColumnDef columnDef = new ColumnDef();
                        tableDef.addColumn(columnDef);
                        columnDef.setName(items[i]);
                        if(isAutoIncrementType(items,i))
                            columnDef.setType("auto");
                        else
                            columnDef.setType("int"+ PRECISSION.SMALLINT_UNSIGNED_DEFAULT);
                    }

                    if(isIntType(items,i)){
                        ColumnDef columnDef = new ColumnDef();
                        tableDef.addColumn(columnDef);
                        columnDef.setName(items[i]);
                        if(isAutoIncrementType(items,i))
                            columnDef.setType("auto");
                        else
                            columnDef.setType("int"+ PRECISSION.INT_DEFAULT);
                    }

                    if(isIntUnsignedType(items,i)){
                        ColumnDef columnDef = new ColumnDef();
                        tableDef.addColumn(columnDef);
                        columnDef.setName(items[i]);
                        if(isAutoIncrementType(items,i))
                            columnDef.setType("auto");
                        else
                            columnDef.setType("int"+ PRECISSION.INT_UNSIGNED_DEFAULT);
                    }

                    if(isBigIntType(items,i)){
                        //System.out.println(String.format("%s|%s|%s|i=%d",items[i],items[i+1],items[i+2],i));
                        ColumnDef columnDef = new ColumnDef();
                        tableDef.addColumn(columnDef);
                        columnDef.setName(items[i]);
                        if(isAutoIncrementType(items,i))
                            columnDef.setType("auto");
                        else
                            columnDef.setType("int"+ PRECISSION.INT_DEFAULT);
                    }

                    if(isBigIntUnsignedType(items,i)){
                        ColumnDef columnDef = new ColumnDef();
                        tableDef.addColumn(columnDef);
                        columnDef.setName(items[i]);
                        if(isAutoIncrementType(items,i))
                            columnDef.setType("auto");
                        else
                            columnDef.setType("int"+ PRECISSION.INT_DEFAULT);
                    }

                    if(isCharType(items,i) ||
                            isVarharType(items,i) ||
                            isDateType(items,i) ||
                            isDateTimeType(items,i) ||
                            isDecimalType(items,i) ||
                            isUUIDType(items,i)
                    ){
                        //System.out.println("columNmae = " + items[i]);
                        ColumnDef columnDef = new ColumnDef();
                        tableDef.addColumn(columnDef);
                        columnDef.setName(items[i]);
                        columnDef.setType(items[i+1]);
                    }

                    if(isBigObjectType(items,i)){
                        ColumnDef columnDef = new ColumnDef();
                        tableDef.addColumn(columnDef);
                        columnDef.setName(items[i]);
                        columnDef.setType("varchar(5000)");
                    }
                }
                
                for(int k = 0; k < tableDefs.size(); k++) {
                    TableDef def = tableDefs.get(k);
                    //System.out.println("name = " + def.getName());
                    if (def != null && def.getName() != null) {
                        //System.out.println(CONFIG.OUTPUT + def.getName());
                        DumperOptions options = new DumperOptions();
                        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                        Yaml yaml = new Yaml(options);
                        String yamlString = yaml.dumpAsMap(def);
                        try {
                            //yaml.dumpAsMap(tableDefs.get(0),new FileWriter(CONFIG.INPUT+tableDefs.get(0).getName()+".yaml"));
                            FileWriter writer = new FileWriter(CONFIG.OUTPUT + def.getName() + ".yaml");
                            writer.write(yamlString);
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                    }

                    LOG.info(String.format("The config file for table[%s] has been generated successfully in path[%s]",def.getName(),CONFIG.OUTPUT + def.getName() + ".yaml"));

                }
                
                
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        if(file.isDirectory()){
            for(int i = 0; i < file.listFiles().length; i++){
                parse(file.listFiles()[i].getPath());
            }
        }
        
        
    }
    
    public static boolean isTableName(String[] items, int index){
        //System.out.println(String.format("%s|%s|%s|%s|%s",items[index],items[index+1],items[index+2],items[index+3],items[index+4],items[index+5]));
        if(     index >= 5 &&
                items[index - 1].equalsIgnoreCase("exists") && 
                items[index - 2].equalsIgnoreCase("not") &&
                items[index - 3].equalsIgnoreCase("if") &&
                items[index - 4].equalsIgnoreCase("table") &&
                items[index - 5].equalsIgnoreCase("create")
        ){
            return true;
        }

        if(     index >= 2 &&
                items[index - 1].equalsIgnoreCase("table") &&
                items[index - 2].equalsIgnoreCase("create")
        ){
            return true;
        }
        
        return false;
    }
    
    public static boolean isAutoIncrementType(String[] items, int index){
        int temp = index;
        for(;index < items.length;index++){
            if(items[index].toLowerCase().startsWith("auto_increment")){
                    return true;
            }
            
            if(items[index].contains(",")){
                break;
            }
        }
        
        return false;
    }

    public static boolean isTinyIntType(String[] items, int index){
        if(
                items[index + 1].toLowerCase().startsWith("tinyint") &&
                        !items[index + 2].equalsIgnoreCase("unsigned")

        ){
            return true;
        }

        return false;
    }

    public static boolean isTinyIntUnsignedType(String[] items, int index){
        if(
                items[index + 1].toLowerCase().startsWith("tinyint") && 
                items[index + 2].equalsIgnoreCase("unsigned")

        ){
            return true;
        }

        return false;
    }

    public static boolean isSmallIntType(String[] items, int index){
        if(
                items[index + 1].toLowerCase().startsWith("smallint") &&
                        !items[index + 2].equalsIgnoreCase("unsigned")

        ){
            return true;
        }

        return false;
    }

    public static boolean isSmallIntUnsignedType(String[] items, int index){
        if(
                items[index + 1].toLowerCase().startsWith("smallint") &&
                        items[index + 2].equalsIgnoreCase("unsigned")

        ){
            return true;
        }

        return false;
    }

    public static boolean isBigIntType(String[] items, int index){
        if(
                items[index + 1].toLowerCase().startsWith("bigint") &&
                        !items[index + 2].equalsIgnoreCase("unsigned")

        ){
            return true;
        }

        return false;
    }

    public static boolean isBigIntUnsignedType(String[] items, int index){
        if(
                items[index + 1].toLowerCase().startsWith("bigint") &&
                        items[index + 2].equalsIgnoreCase("unsigned")

        ){
            return true;
        }

        return false;
    }

    public static boolean isIntType(String[] items, int index){
        if(
                items[index + 1].toLowerCase().startsWith("int") &&
                        !items[index + 2].equalsIgnoreCase("unsigned")

        ){
            return true;
        }
        
        if (items[index + 1].toLowerCase().startsWith("number(") &&
                !items[index + 1].toLowerCase().contains(",")){
            return true;
        }

        return false;
    }

    public static boolean isIntUnsignedType(String[] items, int index){
        if(
                items[index + 1].toLowerCase().startsWith("int") &&
                        items[index + 2].equalsIgnoreCase("unsigned")

        ){
            return true;
        }

        return false;
    }

    public static boolean isDecimalType(String[] items, int index){
        if(
                items[index + 1].toLowerCase().startsWith("float") ||
                        items[index + 1].toLowerCase().startsWith("double") ||
                        items[index + 1].toLowerCase().startsWith("decimal(") ||
                        (items[index + 1].toLowerCase().startsWith("number(") &&
                                items[index + 1].toLowerCase().contains(","))

        ){
            return true;
        }
        
        return false;
    }

    public static boolean isDateType(String[] items, int index){
        if(
                items[index + 1].equalsIgnoreCase("date")

        ){
            return true;
        }

        return false;
    }

    public static boolean isDateTimeType(String[] items, int index){
        if(
                items[index + 1].toLowerCase().startsWith("datetime") ||
                items[index + 1].toLowerCase().startsWith("timestamp")

        ){
            return true;
        }

        return false;
    }

    public static boolean isCharType(String[] items, int index){
        if(
                items[index + 1].toLowerCase().startsWith("char(")

        ){
            return true;
        }

        return false;
    }

    public static boolean isVarharType(String[] items, int index){
        if(
                items[index + 1].toLowerCase().startsWith("varchar(") ||
                items[index + 1].toLowerCase().startsWith("varchar2(") ||
                items[index + 1].toLowerCase().startsWith("varchar2(")
                        

        ){
            return true;
        }

        return false;
    }

    public static boolean isBigObjectType(String[] items, int index){
        if(
                    items[index + 1].equalsIgnoreCase("text") ||
                        items[index + 1].equalsIgnoreCase("clob") ||
                        items[index + 1].equalsIgnoreCase("mediumtext") ||
                        items[index + 1].equalsIgnoreCase("blob") ||
                        items[index + 1].equalsIgnoreCase("mediumblob")

        ){
            return true;
        }

        return false;
    }

    public static boolean isUUIDType(String[] items, int index){
        if(
                items[index + 1].equalsIgnoreCase("uuid")

        ){
            return true;
        }

        return false;
    }
    
    public static void main(String[] args){
        parse("/Users/sudong/Work/专项测试/中移物联");
//        System.out.println(tableDefs.size());
//        for(int i = 0 ; i < tableDefs.size(); i++){
//            System.out.println(tableDefs.get(i).getName());
//            TableDef def = tableDefs.get(i);
//            for(int j = 0 ; j < def.getColumns().size(); j ++){
//                ColumnDef columnDef = def.getColumn(j);
//                System.out.println(columnDef.getName());
//                System.out.println(columnDef.getType());
//            }
//        }
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        String yamlString = yaml.dumpAsMap(tableDefs.get(0));
        try {
            //yaml.dumpAsMap(tableDefs.get(0),new FileWriter(CONFIG.INPUT+tableDefs.get(0).getName()+".yaml"));
            FileWriter writer = new FileWriter(CONFIG.INPUT+tableDefs.get(0).getName()+".yaml");
            writer.write(yamlString);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(yamlString);
    }
}
