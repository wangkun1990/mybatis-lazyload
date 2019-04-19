package com.mybatis.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public final class MapUtil {

    private MapUtil() {

    }


    public static String mapToString(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
                builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return StringUtils.isNotBlank(builder) ? builder.subSequence(0, builder.length() - 1).toString() : builder.toString();
    }
}
