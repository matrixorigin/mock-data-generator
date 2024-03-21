package io.mo.gendata;

import cn.binarywang.tools.generator.ChineseIDCardNumberGenerator;
import cn.binarywang.tools.generator.base.GenericGenerator;
import com.github.javafaker.Faker;
import io.mo.gendata.builtin.CarUtils;
import io.mo.gendata.builtin.DataFaker;
import io.mo.gendata.constant.CONFIG;
import io.mo.gendata.constant.DATA;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

public class CoreAPI {
    private static Logger LOG = Logger.getLogger(CoreAPI.class.getName());
    private  Faker us_faker = new Faker(Locale.US);
    private  Faker cn_faker = new Faker(Locale.CHINA);
    private  Faker faker = us_faker;
    private static GenericGenerator CIDCardNG = ChineseIDCardNumberGenerator.getInstance();

    private  DateFormat d_format = new SimpleDateFormat("yyyy-MM-dd");
    private  DateFormat dt_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
    // autoincrement
    public String getAutoIncrement(){
        return CONFIG.NULL_VALUE;
    }
    
    // fake random int(x,y)
    public int nextInt(int x,int y){
        int num = RandomUtils.nextInt(x,y);
        return num;
    }

    //fake random decimal(l,s)
    public String nextDecimal(int length,int scale){
        if(scale > length){
            LOG.error("The scale length must be less than decimal number length for the decimal type,program will exit.");
        }
        int s_l = nextInt(0,scale);
        if(s_l >= DATA.MAXVALUE.length) s_l = DATA.MAXVALUE.length/2;
        int s_n = nextInt(0,DATA.MAXVALUE[s_l]);

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
        return RandomStringUtils.randomAlphabetic(length);
    }
    
    //fake random varchar
    public String nextVarchar(int length){
        int len = faker.random().nextInt(1,length);
        return RandomStringUtils.randomAlphabetic(len);
    }
    
    //get UUID
    public String nextUUID(){
        return UUID.randomUUID().toString();
    }

    //builtin filed: name
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
    
    
    public static void main(String args[]){
        Faker us_faker = new Faker(Locale.US);
        CoreAPI faker = new CoreAPI();
        //float f = 2/100;
        //System.out.println(f);
    }
}
