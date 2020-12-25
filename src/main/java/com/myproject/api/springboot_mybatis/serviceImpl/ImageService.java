package com.myproject.api.springboot_mybatis.serviceImpl;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {
    public static String getImgStr(String imgName){
        InputStream in = null;
        byte[] data =null;
        try{
            String path = "file:/root/ViewImage/";
            Resource resource = new ClassPathResource(path+imgName+".jpg");
            File imgFile = resource.getFile();
            in = new FileInputStream(imgFile.getPath());
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(data));
    }
}
