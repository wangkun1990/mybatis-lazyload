package com.mybatis.service.impl;

import org.springframework.util.Base64Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class ImageBase64 {

    public static void main(String[] args) throws Exception {
        File file = new File("d:" + File.separator + "1.png");
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int length = 0;
        while ((length = fis.read(bytes)) != -1) {
            baos.write(bytes, 0, length);
        }
        System.out.println(Base64Utils.encodeToString(baos.toByteArray()));
    }

}
