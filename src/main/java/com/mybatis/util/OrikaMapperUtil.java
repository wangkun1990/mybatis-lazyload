package com.mybatis.util;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.List;

/**
 * 对象拷贝工具类
 */
public final class OrikaMapperUtil {

    private OrikaMapperUtil() {

    }

    private static MapperFactory mapperFactory;
    private static MapperFacade mapperFacade;
    static {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFacade = mapperFactory.getMapperFacade();
    }

    public static <A, B> B map(A source, Class<B> destClazz) {
        return mapperFacade.map(source, destClazz);
    }

    public static <S, D> List<D> mapAsList(Iterable<S> iterable, Class<D> destClass) {
        return mapperFacade.mapAsList(iterable, destClass);
    }
}
