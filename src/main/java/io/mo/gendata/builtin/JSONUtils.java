package io.mo.gendata.builtin;

import cn.hutool.json.JSONObject;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Locale;
import java.util.Random;

public class JSONUtils {
    private static Faker cn_faker = new Faker(Locale.CHINA);
    public static String generateJson(){
        String key = null;
        String value = null;
        JSONObject json = new JSONObject();
        Random random = new Random();
        json.putOpt("id", RandomUtils.nextInt());
        json.putOpt("name", cn_faker.name().fullName());
        json.putOpt("value", RandomUtils.nextDouble());
        json.putOpt("isActive", RandomUtils.nextBoolean());
        for(int i =0 ; i < 10; i ++){
            int len_key = RandomUtils.nextInt(0,10);
            int len_value = RandomUtils.nextInt(0,10);
            key = RandomStringUtils.randomAlphabetic(len_key);
            value = RandomStringUtils.randomAlphabetic(len_value);
            json.putOpt(key,value);
        }
        
        return json.toString();
    }
}
