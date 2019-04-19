package com.mybatis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.commons.util.MD5Util.class);

    private MD5Util() {

    }


    /**
     * 16bit MD5加密
     *
     * @param input
     * @return
     */
    public static byte[] digest(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return md5.digest(input.getBytes("utf-8"));
    }


    /**
     * 32bit MD5加密
     *
     * @param input
     * @return
     */
    public static String md5Encrypt32(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte b[] = md.digest();
        int i;
        StringBuffer buf = new StringBuffer();
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }

}
