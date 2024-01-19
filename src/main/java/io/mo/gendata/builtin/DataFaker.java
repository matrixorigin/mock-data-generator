package io.mo.gendata.builtin;

import cn.binarywang.tools.generator.*;
import cn.binarywang.tools.generator.base.GenericGenerator;
import com.github.javafaker.Faker;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;


public class DataFaker {

    public static Faker us_faker = new Faker(Locale.US);
    public static Faker zh_faker = new Faker(Locale.CHINA);

    //身份证号码生成类
    //public static IdCardGenerator IDCARDGENERATOR = new IdCardGenerator();
    //身份证号码生成类
    public static GenericGenerator CIDCardNG = ChineseIDCardNumberGenerator.getInstance();
    //银行卡号生成类
    public static GenericGenerator BCardNG = BankCardNumberGenerator.getInstance();
    //中文姓名生成类
    public static GenericGenerator CNameG = ChineseNameGenerator.getInstance();
    //邮箱生成类
    public static GenericGenerator EMailG = EmailAddressGenerator.getInstance();
    //手机号码生成类
    public static GenericGenerator CMobileNumG = ChineseMobileNumberGenerator.getInstance();
    //地区生成类
    public static GenericGenerator CAreaG = ChineseAddressGenerator.getInstance();
    //随机生成身份证号码
    public static String getRandomIdCardNum(){
        //return IDCARDGENERATOR.generate();
        //CIDCardNG.generate()
        return CIDCardNG.generate();
    }

    //随机生成银行卡号
    public static String getRandomBankCardNum(){
        return BCardNG.generate();
    }

    //随机生成中文姓名
    public static String getRandomCName(){
        return CNameG.generate();
    }

    //随机生成英文姓名
    public static String getRandomUSName(){
        return us_faker.name().fullName();
    }

    //随机生成邮箱
    public static String getRandomEmai(){
        return EMailG.generate();
    }

    //随机生成中国手机号码
    public static String getRandomCMobileNum(){
        return CMobileNumG.generate();
    }

    //随机生成省份
    public static String getRandomProvince(){
        return Commontils.getRandomProvince();
    }

    public static String getRandomProvinceCode(){
        return Commontils.getRandomProvinceCode();
    }

    //随机生成国籍
    public static String getRandomCountryName(){
        return Commontils.getRandomCountryName();
    }
    //随机生成国家代码
    public static String getRandomCountryCode(){
        int rom = (int)(Math.random()*4);
        if(rom == 0)
            return zh_faker.country().countryCode2();
        if(rom == 1)
            return zh_faker.country().countryCode2().toUpperCase();
        if(rom == 2)
            return zh_faker.country().countryCode3();
        if(rom == 3)
            return zh_faker.country().countryCode3().toUpperCase();
        return null;
    }

    //随机生成信用卡号
    public static String getRandomCreditCardNum(){
        int rom = (int)(Math.random()*2);
        if(rom == 1)
            return zh_faker.finance().creditCard();
        else
            return zh_faker.finance().creditCard().replaceAll("-","");
    }

    //随机生成车牌号
    public static @NotNull String getRandomCarPlateNumber(){
        return CarUtils.getCarPlateNumber();
    }

    //随机生成车架号
    public static String getRandomCarVin(){
        return CarUtils.getRandomCarVin();
    }

    //随机生成电话号码
    public static String getRandomPhoneNum(){
        int rom = (int)(Math.random()*2);
        if(rom == 1)
            return zh_faker.phoneNumber().phoneNumber();
        else
            return getRandomCMobileNum();
    }

    //随机生成美国电话号码
    public static String getRandomUSPhoneNum(){
        return us_faker.phoneNumber().phoneNumber();
    }

    //随机生成香港电话号码
    public static String getRandomHKPhoneNum(){
        return Commontils.getRandomHKphoneNum();
    }

    //随机生成民族
    public static String getRandomCNationality(){
        return Commontils.getRandomNationality();
    }

    //随机成成IP地址V4
    public static String getRandomIPAddrV4(){
        return zh_faker.internet().ipV4Address();
    }

    //随机成成IP地址V6
    public static String getRandomIPAddrV6(){
        return zh_faker.internet().ipV6Address();
    }

    //随机成成IP地址V6
    public static String getRandomMACAddr(){
        return zh_faker.internet().macAddress();
    }

    //随机生成政治面貌
    public static String getRandomPoliticCountenance(){
        return Commontils.getRandomPoliticCountenance();
    }

    //随机获取性别
    public static String getRandomSex(){
        return Commontils.getRandomSex();
    }

    //随机生成QQ号码
    public static String getRandomQQNum(){
        return Commontils.getRandomQQNum();
    }

    //随机生成微信号码
    public static String getRandomWechatNum(){
        return Commontils.getRandomWechatNum();
    }

    //随机生成学校名称
    public static String getRandomSchoolName(){
        return Commontils.getRandomSchoolName(zh_faker.address().city()+zh_faker.address().streetName());
    }

    //随机生成学院名称
    public static String getRandomCollegeName(){
        return Commontils.getRandomCollegeName();
    }

    //随机生成学历名称
    public static String getRandomQualificationName(){
        return Commontils.getRandomQualification();
    }

    //随机生成学位名称
    public static String getRandomDegree(){
        return Commontils.getRandomDegree();
    }

    //随机生成婚姻状态
    public static String getRandomMaritalstatus(){
        return Commontils.getRandomMaritalstatus();
    }

    //获取随机金额
    public static float getRandomMoney(){
        return Float.valueOf(zh_faker.commerce().price(0,1000000000L));
    }

    //获取随机宗教
    public static String getRandomReligion(){
        return Commontils.getRandomReligion();
    }

    //随机获取驾驶证档案号
    public static String getRandomDriveLicenseNum(){
        return Commontils.getRandomDriveLicenseNum();
    }

    //随机获取美国社保账号
    public static String getRandomWeUSSSN(){
        return us_faker.idNumber().ssnValid();
    }

    //随机获取中国护照号
    public static String getRandomPassport(){
        return Commontils.getRandomPassport();
    }

    //随机获取中国护照号
    public static String getRandomPassportHK(){
        return Commontils.getRandomPassportHK();
    }

    //随机获取中国护照号
    public static String getRandomPassportMA(){
        return Commontils.getRandomPassportMA();
    }

    //随机获取港澳通行证号码
    public static String getRandomHKMAPass(){
        return Commontils.getRandomHKMAPass();
    }

    //随机获取军官证号
    public static String getRandomOfficalCardNum(){
        return Commontils.getRandomOfficalCardNum();
    }

    //随机获取银行SwiftCode
    public static String getRandomSwiftCode(){
        return Commontils.getRandomSwiftCode();
    }


    public static void main(String args[]){
        /*System.out.println(DataFaker.getRandomSwiftCode());
        System.out.println(DataFaker.getRandomSwiftCode());
        System.out.println(DataFaker.getRandomSwiftCode());
        System.out.println(DataFaker.getRandomSwiftCode());
        System.out.println(DataFaker.getRandomSwiftCode());
        System.out.println(DataFaker.getRandomSwiftCode());*/

    }
}
