package com.mybatis.util;


import org.apache.commons.codec.binary.Base64;

/**
 * @author wangkun
 */
public final class Base64Util {

    private Base64Util() {

    }

    /**
     * base64解码
     * @param base64String
     * @return
     */
    public static String decodeBase64(String base64String) {
        return new String(Base64.decodeBase64(base64String));
    }

    /**
     * base64 编码
     * @param bytes
     * @return
     */
    public static String encodeBase64(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }
}
