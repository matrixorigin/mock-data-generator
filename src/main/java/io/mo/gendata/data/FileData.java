package io.mo.gendata.data;

import io.mo.gendata.CoreAPI;
import io.mo.gendata.constant.DATA;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileData {
    private String path;
    private List<String[]> rows = new ArrayList<>();
    private Map<Integer,String[]> groups = new HashMap<>();
    private Random random = new Random();

    private static Logger LOG = Logger.getLogger(FileData.class.getName());
    
    
    public FileData(String path){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            LOG.info(String.format("Loading data from file[%s], and will take a moment....",path));
            String line = reader.readLine();
            while(line != null){
                if(line.equalsIgnoreCase(""))
                    continue;
                
                String[] row = line.split(",");
                rows.add(row);
                line = reader.readLine();
            }
            LOG.info(String.format("Loading data from file[%s] has completed.",path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public String[] row(){
        int rit = random.nextInt(rows.size());
        return rows.get(rit);
    }
    
    
    public static void main(String[] args){
        FileData fileData = new FileData("/Users/sudong/area.csv");
        System.out.println(Arrays.toString(fileData.row()));
    }
}
