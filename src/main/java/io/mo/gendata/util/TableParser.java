package io.mo.gendata.util;

import io.mo.gendata.meta.Field;
import io.mo.gendata.meta.Table;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableParser {
    private static ArrayList<Table> tables = new ArrayList<Table>();

    private static YamlUtil yaml = new YamlUtil();
    private static Map input = null;
    private static Map tab_map = null;
    private static Logger LOG = Logger.getLogger(TableParser.class.getName());

    public static void parse_old(String path){
        File file = new File(path);
        //if file
        if(file.isFile()){
            try {
                input = yaml.getInfo(file.getPath());
                List tab_list = (ArrayList) input.get("tables");
                if(tab_list == null){
                    LOG.warn("There is no table defination in file["+path+"].");
                    return;
                }

                for(int i = 0; i < tab_list.size();i++){
                    Map tab_map = (HashMap)tab_list.get(i);

                    String t_name = (String)tab_map.get("name");
                    int count = (Integer) tab_map.get("count");

                    Table table = new Table();
                    table.setName(t_name);
                    table.setCount(count);
                    tables.add(table);

                    List field_list = (ArrayList)tab_map.get("columns");
                    for(int j = 0; j < field_list.size(); j++){
                        Map field_map = (HashMap)field_list.get(j);

                        String f_name = (String)field_map.get("name");
                        String f_type = (String)field_map.get("type");
                        String f_builtin = (String)field_map.get("builtin");
                        String f_enum = (String)field_map.get("enum");
                        String f_index = (String)field_map.get("index");
                        String f_ref = (String)field_map.get("ref");
                        String f_prefix = (String)field_map.get("prefix");
                        int f_null_ratio = field_map.get("null_ratio") == null?100:(int)field_map.get("null_ratio");
                        
                        if(f_builtin == null && f_type == null && f_enum == null) {
                            LOG.error("The column[" + f_name + "] in table[" + table.getName() + "] must one of attributes[type,builtin,ref,enum].please check.");
                            System.exit(1);
                        }

                        Field field = new Field();
                        if(f_name == null){
                            LOG.warn("There is one column in table["+table.getName()+"] that don't have name attribute.please check.");
                        }
                        field.setName(f_name);

                        if(f_type != null){
                            //parse the type
                            if(f_type.indexOf("(") != -1){
                                //if the type with paras,only support int type now
                                String[] paras = f_type.substring(f_type.indexOf("(")+1,f_type.indexOf(")")).split(",");
                                for (String para : paras){
                                    field.addPara(Integer.parseInt(para));
                                }

                                field.setType(f_type.substring(0,f_type.indexOf("(")));
                            }else {
                                //if the type with no paras
                                field.setType(f_type);
                            }
                        }
                        
                        if(f_builtin != null ) {
                            field.setBuiltin(f_builtin);
                        }
                        
                        if(f_enum !=null ){
                            String[] eValues = f_enum.split(",");
                            field.addEnum(eValues);
                        }

                        if(f_prefix !=null ){
                            String[] pValues = f_prefix.split(",");
                            field.addPrefix(pValues);
                        }
                        
                        if(f_index != null)
                            field.setIndex(f_index);
                        
                        if(f_ref != null)
                            field.setRef(f_ref);
                        
                        if(f_null_ratio < 100){
                            field.setNull_ratio(f_null_ratio);
                        }
                        
                        table.addField(field);
                    }

                    table.makeIndex();
                }

            } catch (FileNotFoundException e) {
                LOG.error("The input file["+path+"] does not exist,please check.");
                e.printStackTrace();
                System.exit(1);
            } catch (UnsupportedEncodingException e) {
                LOG.error("The input file["+path+"] is not the well-yaml-formatted,please check.");
                e.printStackTrace();
                System.exit(1);
            }
        }

        //if directory
        if(file.isDirectory()){
            for(int i = 0; i < file.listFiles().length; i++){
                parse_old(file.listFiles()[i].getPath());
            }
        }
    }

    public static void parse(String path){
        File file = new File(path);
        //if file
        if(file.isFile()){
            try {
                tab_map = yaml.getInfo(file.getPath());
                System.out.println(file.getPath());
                String t_name = (String)tab_map.get("name");
                int count = (Integer) tab_map.get("count");

                Table table = new Table();
                table.setName(t_name);
                table.setCount(count);
                tables.add(table);

                List field_list = (ArrayList)tab_map.get("columns");
                for(int j = 0; j < field_list.size(); j++){
                    Map field_map = (HashMap)field_list.get(j);

                    String f_name = (String)field_map.get("name");
                    String f_type = (String)field_map.get("type");
                    String f_builtin = (String)field_map.get("builtin");
                    String f_enum = (String)field_map.get("enum");
                    String f_index = (String)field_map.get("index");
                    String f_ref = (String)field_map.get("ref");
                    String f_prefix = (String)field_map.get("prefix");
                    int f_null_ratio = field_map.get("null_ratio") == null?100:(int)field_map.get("null_ratio");

                    if(f_builtin == null && f_type == null && f_enum == null) {
                        LOG.error("The column[" + f_name + "] in table[" + table.getName() + "] must one of attributes[type,builtin,ref,enum].please check.");
                        System.exit(1);
                    }

                    Field field = new Field();
                    if(f_name == null){
                        LOG.warn("There is one column in table["+table.getName()+"] that don't have name attribute.please check.");
                    }
                    field.setName(f_name);

                    if(f_type != null){
                        //parse the type
                        if(f_type.indexOf("(") != -1){
                            //if the type with paras,only support int type now
                            String[] paras = f_type.substring(f_type.indexOf("(")+1,f_type.indexOf(")")).split(",");
                            for (String para : paras){
                                field.addPara(Integer.parseInt(para));
                            }

                            field.setType(f_type.substring(0,f_type.indexOf("(")));
                        }else {
                            //if the type with no paras
                            field.setType(f_type);
                        }
                    }

                    if(f_builtin != null ) {
                        field.setBuiltin(f_builtin);
                    }

                    if(f_enum !=null ){
                        String[] eValues = f_enum.split(",");
                        field.addEnum(eValues);
                    }

                    if(f_prefix !=null ){
                        String[] pValues = f_prefix.split(",");
                        field.addPrefix(pValues);
                    }

                    if(f_index != null)
                        field.setIndex(f_index);

                    if(f_ref != null)
                        field.setRef(f_ref);

                    if(f_null_ratio < 100){
                        field.setNull_ratio(f_null_ratio);
                    }

                    table.addField(field);
                }

                table.makeIndex();

            } catch (FileNotFoundException e) {
                LOG.error("The input file["+path+"] does not exist,please check.");
                e.printStackTrace();
                System.exit(1);
            } catch (UnsupportedEncodingException e) {
                LOG.error("The input file["+path+"] is not the well-yaml-formatted,please check.");
                e.printStackTrace();
                System.exit(1);
            }
        }

        //if directory
        if(file.isDirectory()){
            for(int i = 0; i < file.listFiles().length; i++){
                parse(file.listFiles()[i].getPath());
            }
        }
    }


    public static ArrayList<Table> getTables() {
        return tables;
    }

    public static void setTables(ArrayList<Table> tables) {
        TableParser.tables = tables;
    }

    public static void main(String args[]){
        TableParser.parse("def/");
        for(int i = 0; i < TableParser.getTables().size();i++){
            Table table = TableParser.getTables().get(i);
            System.out.println(table.getName()+":\n");
            for(int j = 0; j < table.getFields().size();j++){
                Field field = table.getFields().get(j);
                System.out.println(field.getName()+":"+field.getType()+":"+field.nextValue());
            }
        }
    }



}
