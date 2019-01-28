package com.mybatis.service.impl;

import com.mybatis.entity.Department;
import com.mybatis.service.IDepartmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-service.xml"})
public class DepartmentServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(DepartmentServiceImplTest.class);

    @Autowired
    private IDepartmentService departmentService;

    @Test
    public void getDepartmentById() {
        Department department = departmentService.getDepartmentById(1);
        logger.info("Department Class = {}", department.getClass());
    }
}