package io.mo.gendata.cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.IOUtils;
import io.mo.gendata.util.ConfUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class COSUtils {
    public static void main(String[] args){
        
    }
    
    public static COSClient getCOSClient(){
        ClientConfig config = new ClientConfig(new Region(ConfUtil.getRegion()));
        COSCredentials cred = new BasicCOSCredentials(ConfUtil.getSecretId(),ConfUtil.getSecretKey());

        COSClient cosClient = new COSClient(cred,config);
        return cosClient;
    }
    
    
}
