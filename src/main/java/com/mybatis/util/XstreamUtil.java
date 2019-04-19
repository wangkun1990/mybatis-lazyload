package com.mybatis.util;

import com.thoughtworks.xstream.XStream;

public final class XstreamUtil {

    private XstreamUtil() {

    }


    public static String toXML(Object object) {
        XStream xStream = new XStream();

        xStream.processAnnotations(object.getClass());
        return xStream.toXML(object);
    }


    public static <T> T fromXML(Class<T> T, String xml) {
        XStream xStream = new XStream();
        xStream.processAnnotations(T);
        // 安全处理
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[]{T});
        return (T) xStream.fromXML(xml);
    }
}
