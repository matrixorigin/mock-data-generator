package io.mo.gendata;

import cn.binarywang.tools.generator.ChineseIDCardNumberGenerator;
import cn.binarywang.tools.generator.base.GenericGenerator;
import com.github.javafaker.Faker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import io.mo.gendata.constant.DATA;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

public class CoreAPI {
    private static Logger LOG = Logger.getLogger(CoreAPI.class.getName());
    private  Faker us_faker = new Faker(Locale.US);
    private  Faker cn_faker = new Faker(Locale.CHINA);
    private  Faker faker = us_faker;
    private static GenericGenerator CIDCardNG = ChineseIDCardNumberGenerator.getInstance();

    private  DateFormat d_format = new SimpleDateFormat("yyyy-MM-dd");
    private  DateFormat dt_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    // fake random int(x,y)
    public int nextInt(int x,int y){
        int num = faker.random().nextInt(x,y);
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
    public String nextVarchar(int length){
        return RandomStringUtils.randomAlphabetic(length);
    }

    //builtin filed: name
    public String getName(){
        return faker.name().name();
    }


    //builtin filed: phonenumber
    public String getPhonenumber(){
        return faker.phoneNumber().phoneNumber();
    }

    //builtin filed: cellphone
    public String getCellphone(){
        return faker.phoneNumber().cellPhone();
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






    public static void main(String args[]){
        Faker us_faker = new Faker(Locale.US);
        CoreAPI faker = new CoreAPI();
        //float f = 2/100;
        //System.out.println(f);
    }
}
