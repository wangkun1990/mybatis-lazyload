package com.mybatis.service.impl;

import com.mybatis.entity.Student;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.Jdk;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;

public class DefaultObjectFactoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultObjectFactoryTest.class);

    public static void main(String[] args) {
        ObjectFactory objectFactory = new DefaultObjectFactory();
        Student student = objectFactory.create(Student.class);
        LOGGER.info("" + student);
        Type type = student.getClass();
        System.out.println(type);
        System.out.println(((Type)Student.class).getTypeName());

        List<?> list = objectFactory.create(List.class);
        LOGGER.info("" + list.getClass());

        LOGGER.info("{}", Jdk.parameterExists);
        LOGGER.info("{}", Jdk.dateAndTimeApiExists);

        Reflector reflector = new Reflector(Student.class);

        MetaClass metaClass = MetaClass.forClass(Student.class, new DefaultReflectorFactory()).metaClassForProperty("department");
        MetaClass.forClass(Student.class, new DefaultReflectorFactory()).metaClassForProperty("id");
        // metaClass对象是关于 Department 的，而不是Student，所以返回null
        String property = metaClass.findProperty("department");
        LOGGER.info("{}", property);
        // 此处的id查找的也是Department类中的id
        property = metaClass.findProperty("id");
        LOGGER.info("{}", property);

        property = metaClass.findProperty("id", true);
        LOGGER.info("{}", property);

        Class<?> clazz = MetaClass.forClass(Student.class, new DefaultReflectorFactory()).getGetterType("department.id");
        LOGGER.info("getSetterType = {}", clazz);

        boolean result = metaClass.hasGetter("id");
        LOGGER.info("hasGetter = {}",result);
        result = metaClass.hasSetter("id");
        LOGGER.info("hasSetter = {}",result);

        Invoker getInvoker = metaClass.getGetInvoker("id");
        LOGGER.info("getInvoker = {}", getInvoker);

        Invoker setInvoker = metaClass.getSetInvoker("id");
        LOGGER.info("setInvoker = {}", setInvoker);
        boolean hasDefaultConstructor = metaClass.hasDefaultConstructor();
        LOGGER.info("hasDefaultConstructor = {}", hasDefaultConstructor);
    }
}
