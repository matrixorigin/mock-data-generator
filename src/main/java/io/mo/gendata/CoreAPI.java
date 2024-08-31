package io.mo.gendata;

import cn.binarywang.tools.generator.ChineseIDCardNumberGenerator;
import cn.binarywang.tools.generator.base.GenericGenerator;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.mo.gendata.builtin.CarUtils;
import io.mo.gendata.builtin.DataFaker;
import io.mo.gendata.builtin.UUID;
import io.mo.gendata.constant.CONFIG;
import io.mo.gendata.constant.DATA;
import io.mo.gendata.data.FileGroupData;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CoreAPI {
    private static Logger LOG = Logger.getLogger(CoreAPI.class.getName());
    private  Faker us_faker = new Faker(Locale.US);
    private  Faker cn_faker = new Faker(Locale.CHINA);
    private  Faker faker = us_faker;
    private static GenericGenerator CIDCardNG = ChineseIDCardNumberGenerator.getInstance();
    private StringBuilder buffer = new StringBuilder();

    private ObjectMapper objectMapper = new ObjectMapper();

    private  DateFormat d_format = new SimpleDateFormat("yyyy-MM-dd");
    private  DateFormat dt_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
    private Random random = new Random();
    
    private UUID uuid = new UUID();
    
    private String fileGroupTag;
    
    // autoincrement
    public String getAutoIncrement(){
        return CONFIG.NULL_VALUE;
    }
    
    // fake random int(x,y)
    public int nextInt(int x,int y){
        int num = Math.abs(random.nextInt());
        num = num%(y-x+1) + x;
        return num;
    }

    //fake random decimal(l,s)
    public String nextDecimal(int length,int scale){
        if(scale > length){
            LOG.error("The scale length must be less than decimal number length for the decimal type,program will exit.");
        }
        int s_l = nextInt(1,scale);
        if(s_l >= DATA.MAXVALUE.length) s_l = DATA.MAXVALUE.length/2;
        int s_n = nextInt(0,DATA.MAXVALUE[s_l]) + 1;

        int i_l = length - s_l;
        if(i_l >= DATA.MAXVALUE.length) i_l = DATA.MAXVALUE.length/2;
        int i_n = nextInt(0,DATA.MAXVALUE[i_l]);

        return i_n+"."+s_n;
    }

    //fake random date
    public String nextDate(){
        return d_format.format(faker.date().between(DATA.START_DATE,DATA.END_DATE));
    }

    //fake random datetime
    public String nextDateTime(){
        return dt_format.format(faker.date().between(DATA.START_DATE,DATA.END_DATE));

    }

    //fake random varchar
    public String nextChar(int length){
        return randomStr(length, 0, 0, true, false, null, random);
    }
    
    //fake random varchar
    public String nextVarchar(int length){
        int len = nextInt(1,length);
        return randomStr(len, 0, 0, true, false, null, random);
    }
    
    //get UUID
    public String nextUUID(){
        
        return uuid.generateUUID();
    }

    public String nextVector(int dimension){
        buffer.delete(0,buffer.length());

        buffer.append("[");
        for(int i = 0; i < dimension; i++){
            buffer.append(nextDecimal(5,2));
            if(i != dimension -1)
                buffer.append(",");
        }
        buffer.append("]");
        return buffer.toString();
    }

    public String nextJson(){
        return generateJson();
    }

    //builtin filed: name
    
    public String getFileData(String path,int cid,int gid){
        if(gid == -1){
            return DATA.FILES.get(path).row()[cid];
        }else {
            
            if(DATA.FILEGROUPDATA.containsKey(fileGroupTag)){
                List<FileGroupData> fileGroupDataList = DATA.FILEGROUPDATA.get(fileGroupTag);
                for(int i = 0; i < fileGroupDataList.size();i++){
                    FileGroupData fileGroupData = fileGroupDataList.get(i);
                    if(fileGroupData.getPath().equalsIgnoreCase(path) &&
                            fileGroupData.getGid() == gid){
                        return fileGroupData.getRow()[cid];
                    }
                }
                
                String[] row = DATA.FILES.get(path).row();
                fileGroupDataList.add(new FileGroupData(path,gid,row));
                return row[cid];
            }else {
                String[] row = DATA.FILES.get(path).row();
                List<FileGroupData> fileGroupDataList = new ArrayList();
                fileGroupDataList.add(new FileGroupData(path,gid,row));
                DATA.FILEGROUPDATA.put(fileGroupTag,fileGroupDataList);
                return row[cid];
            }
        }
    }
    
    
    public String getUnique(){
        return String.valueOf(System.currentTimeMillis());
    }
    public String getName(){
        return cn_faker.name().name();
    }


    //builtin filed: phonenumber
    public String getPhonenumber(){
        return cn_faker.phoneNumber().phoneNumber();
    }

    //builtin filed: cellphone
    public String getCellphone(){
        return cn_faker.phoneNumber().cellPhone();
    }

    //builtin filed: idcardnum
    public String getIdCardNum(){
        return CIDCardNG.generate();
    }

    //builtin fiedl: SSN
    public String getSSN(){
        return faker.idNumber().ssnValid();
    }

    //builtin fiedl: email
    public String getEmail(){
        return faker.internet().emailAddress();
    }

    //builtin fiedl: country
    public String getCountry(){
        return cn_faker.country().name();
    }

    public  String getCarVin(){
        return CarUtils.getRandomCarVin();
    }

    public String getCity(){
        return cn_faker.address().city();
    }

    public String getBankCardNum(){
        return DataFaker.getRandomBankCardNum();
    }
    
    public String getProvince(){
        return DataFaker.getRandomProvince();
    }

    public String getProvinceCode(){
        return DataFaker.getRandomProvinceCode();
    }
    
    public String getCreditCardNum(){
        return DataFaker.getRandomCreditCardNum();
    }

    public String getOfficalCardNum(){
        return DataFaker.getRandomOfficalCardNum();
    }

    public String getCarPlateNumber(){
        return DataFaker.getRandomCarPlateNumber();
    }

    public String getNationality(){
        return DataFaker.getRandomCNationality();
    }

    public String getCollegeName(){
        return DataFaker.getRandomCollegeName();
    }

    public String getQualificationName(){
        return DataFaker.getRandomQualificationName();
    }

    public String getCountryName(){
        return DataFaker.getRandomCountryName();
    }

    public String getSchoolName(){
        return DataFaker.getRandomSchoolName();
    }

    public String getUSName(){
        return DataFaker.getRandomUSName();
    }

    public String getCountryCode(){
        return DataFaker.getRandomCountryCode();
    }

    public String getSwiftCode(){
        return DataFaker.getRandomSwiftCode();
    }

    public String getDegree(){
        return DataFaker.getRandomDegree();
    }

    public String getDriveLicenseNum(){
        return DataFaker.getRandomDriveLicenseNum();
    }

    public String getQQNum(){
        return DataFaker.getRandomQQNum();
    }

    public String getWechatNum(){
        return DataFaker.getRandomWechatNum();
    }

    public String getHKPhoneNum(){
        return DataFaker.getRandomHKPhoneNum();
    }

    public String getUSPhoneNum(){
        return DataFaker.getRandomUSPhoneNum();
    }



    public String getIPAddrV4(){
        return DataFaker.getRandomIPAddrV4();
    }

    public String getIPAddrV6(){
        return DataFaker.getRandomIPAddrV6();
    }

    public String getMACAddr(){
        return DataFaker.getRandomMACAddr();
    }

    public String getPassportCode(){
        return DataFaker.getRandomPassport();
    }

    public String getPassportHKCode(){
        return DataFaker.getRandomPassportHK();
    }

    public String getPassportMACode(){
        return DataFaker.getRandomPassportMA();
    }

    public String getPassportHKMACode(){
        return DataFaker.getRandomPassportHK();
    }

    public String getAddress(){
      return cn_faker.address().fullAddress();
    }

    public static String randomStr(int count, int start, int end, final boolean letters, final boolean numbers,
                                   final char[] chars, final Random random) {
        if (count == 0) {
            return StringUtils.EMPTY;
        } else if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        if (chars != null && chars.length == 0) {
            throw new IllegalArgumentException("The chars array must not be empty");
        }

        if (start == 0 && end == 0) {
            if (chars != null) {
                end = chars.length;
            } else {
                if (!letters && !numbers) {
                    end = Character.MAX_CODE_POINT;
                } else {
                    end = 'z' + 1;
                    start = ' ';
                }
            }
        } else {
            if (end <= start) {
                throw new IllegalArgumentException("Parameter end (" + end + ") must be greater than start (" + start + ")");
            }
        }

        final int zero_digit_ascii = 48;
        final int first_letter_ascii = 65;

        if (chars == null && (numbers && end <= zero_digit_ascii
                || letters && end <= first_letter_ascii)) {
            throw new IllegalArgumentException("Parameter end (" + end + ") must be greater then (" + zero_digit_ascii + ") for generating digits " +
                    "or greater then (" + first_letter_ascii + ") for generating letters.");
        }

        final StringBuilder builder = new StringBuilder(count);
        final int gap = end - start;

        while (count-- != 0) {
            int codePoint;
            if (chars == null) {
                codePoint = random.nextInt(gap) + start;

                switch (Character.getType(codePoint)) {
                    case Character.UNASSIGNED:
                    case Character.PRIVATE_USE:
                    case Character.SURROGATE:
                        count++;
                        continue;
                }

            } else {
                codePoint = chars[random.nextInt(gap) + start];
            }

            final int numberOfChars = Character.charCount(codePoint);
            if (count == 0 && numberOfChars > 1) {
                count++;
                continue;
            }

            if (letters && Character.isLetter(codePoint)
                    || numbers && Character.isDigit(codePoint)
                    || !letters && !numbers) {
                builder.appendCodePoint(codePoint);

                if (numberOfChars == 2) {
                    count--;
                }

            } else {
                count++;
            }
        }
        return builder.toString();
    }

    public String generateJson(){
        String key = null;
        String value = null;
        JSONObject json = new JSONObject();
        Random random = new Random();
        json.putOpt("id", nextInt(10000,1000000));
        json.putOpt("name", cn_faker.name().fullName());
        json.putOpt("value", nextInt(10000000,90000000));
        json.putOpt("isActive", RandomUtils.nextBoolean());
        for(int i =0 ; i < 10; i ++){
            int len_key = nextInt(0,10);
            int len_value = nextInt(0,10);
            key = randomStr(len_key, 0, 0, true, false, null, random);
            //key = RandomStringUtils.randomAlphabetic(len_key);
            value = randomStr(len_value, 0, 0, true, false, null, random);
            //value = RandomStringUtils.randomAlphabetic(len_value);
            json.putOpt(key,value);
        }

        return json.toString();
    }


    public String getFileGroupTag() {
        return fileGroupTag;
    }

    public void setFileGroupTag(String fileGroupTag) {
        this.fileGroupTag = fileGroupTag;
    }

    public static void main(String args[]){
        Faker us_faker = new Faker(Locale.US);
        CoreAPI faker = new CoreAPI();
        //float f = 2/100;
        //System.out.println(f);
        for(int i = 0; i < 100; i++){
            System.out.println(faker.nextInt(1,100));
        }
    }
    
    
}
