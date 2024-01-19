package io.mo.gendata;

import com.github.javafaker.Faker;

import java.util.Locale;

public class Test {
    public static void main(String args[]){
        Faker zh_faker = new Faker(Locale.CHINA);
        System.out.println(zh_faker.address().fullAddress());
    }
}
