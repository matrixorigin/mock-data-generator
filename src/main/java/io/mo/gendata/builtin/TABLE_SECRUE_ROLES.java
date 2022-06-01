package io.mo.gendata.builtin;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TABLE_SECRUE_ROLES {
    private PrintWriter pw;

    public TABLE_SECRUE_ROLES(){
        try {
            pw  = new PrintWriter("table_secure_roles.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeRecord(int count){
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < count; i++){
            buffer.append(DataFaker.getRandomDriveLicenseNum()+",");
            buffer.append(DataFaker.getRandomPassportHK()+",");
            buffer.append(DataFaker.getRandomHKPhoneNum()+",");
            buffer.append(DataFaker.getRandomBankCardNum()+",");
            buffer.append(DataFaker.getRandomMoney()+",");
            buffer.append(DataFaker.getRandomCarVin()+",");
            buffer.append(DataFaker.getRandomCarPlateNumber()+",");
            buffer.append(DataFaker.getRandomIdCardNum()+",");
            buffer.append(DataFaker.getRandomWeUSSSN()+",");
            buffer.append(DataFaker.getRandomProvince()+",");
            buffer.append(DataFaker.getRandomUSPhoneNum()+",");
            buffer.append(DataFaker.getRandomPhoneNum()+",");
            buffer.append(DataFaker.getRandomPassportMA()+",");
            buffer.append(DataFaker.getRandomHKMAPass()+",");
            buffer.append(DataFaker.getRandomCNationality()+",");
            buffer.append(DataFaker.getRandomPoliticCountenance()+",");
            buffer.append(DataFaker.getRandomCMobileNum()+",");
            buffer.append(DataFaker.getRandomSex()+",");
            buffer.append(DataFaker.getRandomWechatNum()+",");
            buffer.append(DataFaker.getRandomReligion()+",");
            buffer.append(DataFaker.getRandomCollegeName()+",");
            buffer.append(DataFaker.getRandomSchoolName()+",");
            buffer.append(DataFaker.getRandomQualificationName()+",");
            buffer.append(DataFaker.getRandomDegree()+",");
            buffer.append(DataFaker.getRandomMaritalstatus()+",");
            buffer.append(DataFaker.getRandomCName()+",");
            buffer.append(DataFaker.getRandomUSName()+",");
            buffer.append(DataFaker.getRandomCountryName()+",");
            buffer.append(DataFaker.getRandomCountryCode()+",");
            buffer.append(DataFaker.getRandomOfficalCardNum()+",");
            buffer.append(DataFaker.getRandomCreditCardNum()+",");
            buffer.append(DataFaker.getRandomPassport()+",");
            buffer.append(DataFaker.getRandomSwiftCode()+",");
            buffer.append(DataFaker.getRandomQQNum()+",");
            buffer.append(DataFaker.getRandomMACAddr()+",");
            buffer.append(DataFaker.getRandomIPAddrV4()+",");
            buffer.append(DataFaker.getRandomIPAddrV6()+",");
            buffer.append(DataFaker.getRandomEmai()+"\n");
            pw.write(buffer.toString());
            pw.flush();
            buffer.delete(0,buffer.length());
        }
        pw.close();
    }

    public static void main(String args[]){
        TABLE_SECRUE_ROLES t = new TABLE_SECRUE_ROLES();
        t.writeRecord(1000000);
    }
}
