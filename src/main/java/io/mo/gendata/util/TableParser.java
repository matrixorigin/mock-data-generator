package io.mo.gendata.util;

import io.mo.gendata.constant.DATA;
import io.mo.gendata.constant.FIELDATTR;
import io.mo.gendata.meta.Field;
import io.mo.gendata.meta.Prefix;
import io.mo.gendata.meta.Table;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public static DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//    public static void parse_old(String path){
//        File file = new File(path);
//        //if file
//        if(file.isFile()){
//            try {
//                input = yaml.getInfo(file.getPath());
//                List tab_list = (ArrayList) input.get("tables");
//                if(tab_list == null){
//                    LOG.warn("There is no table defination in file["+path+"].");
//                    return;
//                }
//
//                for(int i = 0; i < tab_list.size();i++){
//                    Map tab_map = (HashMap)tab_list.get(i);
//
//                    String t_name = (String)tab_map.get("name");
//                    int count = (Integer) tab_map.get("count");
//
//                    Table table = new Table();
//                    table.setName(t_name);
//                    table.setCount(count);
//                    tables.add(table);
//
//                    List field_list = (ArrayList)tab_map.get("columns");
//                    for(int j = 0; j < field_list.size(); j++){
//                        Map field_map = (HashMap)field_list.get(j);
//
//                        String f_name = (String)field_map.get("name");
//                        String f_type = (String)field_map.get("type");
//                        String f_builtin = (String)field_map.get("builtin");
//                        String f_enum = (String)field_map.get("enum");
//                        String f_index = (String)field_map.get("index");
//                        String f_ref = (String)field_map.get("ref");
//                        String f_prefix = (String)field_map.get("prefix");
//                        int f_null_ratio = field_map.get("null_ratio") == null?100:(int)field_map.get("null_ratio");
//                        
//                        if(f_builtin == null && f_type == null && f_enum == null) {
//                            LOG.error("The column[" + f_name + "] in table[" + table.getName() + "] must one of attributes[type,builtin,ref,enum].please check.");
//                            System.exit(1);
//                        }
//
//                        Field field = new Field();
//                        if(f_name == null){
//                            LOG.warn("There is one column in table["+table.getName()+"] that don't have name attribute.please check.");
//                        }
//                        field.setName(f_name);
//
//                        if(f_type != null){
//                            //parse the type
//                            if(f_type.indexOf("(") != -1){
//                                //if the type with paras,only support int type now
//                                String[] paras = f_type.substring(f_type.indexOf("(")+1,f_type.indexOf(")")).split(",");
//                                for (String para : paras){
//                                    field.addPara(Integer.parseInt(para));
//                                }
//
//                                field.setType(f_type.substring(0,f_type.indexOf("(")));
//                            }else {
//                                //if the type with no paras
//                                field.setType(f_type);
//                            }
//                        }
//                        
//                        if(f_builtin != null ) {
//                            field.setBuiltin(f_builtin);
//                        }
//                        
//                        if(f_enum !=null ){
//                            String[] eValues = f_enum.split(",");
//                            field.addEnum(eValues);
//                        }
//
//                        if(f_prefix !=null ){
//                            Prefix prefix = null;
//
//                            //if the prefix is random from int range
//                            if(f_prefix.startsWith("(")){
//                                String[] paras = f_prefix.substring(f_prefix.indexOf("(")+1,f_prefix.indexOf(")")).split(",");
//                                if(paras.length != 2){
//                                    LOG.error(String.format("The prefix for field[%s] is not well-formated, should be formated as (10000,2000)",f_name));
//                                    LOG.error(String.format("The prefix for field[%s] can not be \"%s\"",f_name));
//                                    System.exit(1);
//                                }
//                                int start = Integer.parseInt(paras[0]);
//                                int end = Integer.parseInt(paras[1]);
//                                if(start >= end){
//                                    LOG.error(String.format("The prefix for field[%s] can not be \"%s\", because %d is not smaller than %d",f_name,start,end));
//                                    System.exit(1);
//                                }
//                                prefix = new Prefix(start,end);
//
//                                field.setPrefix(prefix);
//                                
//                            //if the prefix is another column value
//                            } else {
//                                //if the prefix is enum
//                                String[] pValues = f_prefix.split(",");
//                                prefix = new Prefix();
//                                prefix.addValues(pValues);
//                                field.setPrefix(prefix);
//                            }
//                        }
//                        
//                        if(f_index != null)
//                            field.setIndex(f_index);
//                        
//                        if(f_ref != null)
//                            field.setRef(f_ref);
//                        
//                        if(f_null_ratio < 100){
//                            field.setNull_ratio(f_null_ratio);
//                        }
//                        
//                        table.addField(field);
//                    }
//
//                    table.makeIndex();
//                }
//
//            } catch (FileNotFoundException e) {
//                LOG.error("The input file["+path+"] does not exist,please check.");
//                e.printStackTrace();
//                System.exit(1);
//            } catch (UnsupportedEncodingException e) {
//                LOG.error("The input file["+path+"] is not the well-yaml-formatted,please check.");
//                e.printStackTrace();
//                System.exit(1);
//            }
//        }
//
//        //if directory
//        if(file.isDirectory()){
//            for(int i = 0; i < file.listFiles().length; i++){
//                parse_old(file.listFiles()[i].getPath());
//            }
//        }
//    }

    public static void parse(String path){
        File file = new File(path);
        //if file
        if(file.isFile()){
            try {
                tab_map = yaml.getInfo(file.getPath());
                String t_name = (String)tab_map.get("name");
                double count = Double.parseDouble(String.valueOf(tab_map.get("count")));

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
                    String f_enum = null;
                    if(field_map.get("enum") != null) {
                        f_enum = String.valueOf(field_map.get("enum"));
                    }
                    String f_index = (String)field_map.get("index");
                    String f_ref = (String)field_map.get("ref");
                    String f_prefix = (String)field_map.get("prefix");
                    String f_path = (String)field_map.get("path");
                    int f_c_index = field_map.get("column") == null?0:(int)field_map.get("column");
                    int f_group = field_map.get("group") == null?-1:(int)field_map.get("group");
                    int f_null_ratio = field_map.get("null_ratio") == null?100:(int)field_map.get("null_ratio");
                    String f_from = (String)field_map.get("from");
                    String f_to = (String)field_map.get("to");
                    
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
                        Prefix prefix = null;

                        //if the prefix is random from int range
                        if(f_prefix.startsWith("(")){
                            String[] paras = f_prefix.substring(f_prefix.indexOf("(")+1,f_prefix.indexOf(")")).split(",");
                            if(paras.length != 2){
                                LOG.error(String.format("The prefix for field[%s] is not well-formated, should be formated as (10000,2000)",f_name));
                                LOG.error(String.format("The prefix for field[%s] can not be \"%s\"",f_name));
                                System.exit(1);
                            }
                            int start = Integer.parseInt(paras[0]);
                            int end = Integer.parseInt(paras[1]);
                            if(start >= end){
                                LOG.error(String.format("The prefix for field[%s] can not be \"%s\", because %d is not smaller than %d",f_name,start,end));
                                System.exit(1);
                            }
                            prefix = new Prefix(start,end);

                            field.setPrefix(prefix);
                            
                        //if the prefix is another column value
                        }else if(f_prefix.startsWith("$")){
                            String columnName = f_prefix.replace("$","");
                            prefix = new Prefix(columnName);
                            field.setPrefix(prefix);
                            DATA.COLUMN_PREFIX.put(columnName,null);
                        }
                        else {
                            //if the prefix is enum
                            String[] pValues = f_prefix.split(",");
                            prefix = new Prefix();
                            prefix.addValues(pValues);
                            field.setPrefix(prefix);
                        }
                    }

                    if(f_index != null)
                        field.setIndex(f_index);

                    if(f_ref != null)
                        field.setRef(f_ref);
                    
                    try {
                        if (f_from != null)
                            field.setFrom(DATETIME_FORMAT.parse(f_from));
                        else 
                            field.setFrom(DATETIME_FORMAT.parse("1970-01-01 00:00:01"));
                        
                        if(f_to != null)
                            field.setTo(DATETIME_FORMAT.parse(f_to));
                        else
                            field.setTo(DATETIME_FORMAT.parse("2030-13-31 23:59:59"));
                        
                    }catch (ParseException e) {
                        LOG.error("The Date or DateTime["+f_from+"] is not the well-formatted,please check.");
                        e.printStackTrace();
                        System.exit(1);
                    }

                    if(f_null_ratio < 100){
                        field.setNull_ratio(f_null_ratio);
                    }
                    
                    if(f_type!=null && f_type.equalsIgnoreCase(FIELDATTR.FILE_TYPE)) {
                        if (f_path == null) {
                            LOG.error(String.format("The path must be set for column[%s] with file type, please check....", f_name));
                            System.exit(1);
                        }
                        else
                            field.setPath(f_path);

                        field.setColumn_index(f_c_index);
                        field.setGroup(f_group);
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
        }else {
            LOG.error("The input file["+path+"] does not exist,please check.");
            System.exit(1);
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
