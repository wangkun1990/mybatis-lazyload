package com.mybatis.service.impl;

import com.alibaba.fastjson.JSON;
import com.mybatis.entity.Student;
import com.mybatis.service.AbstractPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XsPayServiceImpl extends AbstractPayService {
    private static final Logger LOGGER = LoggerFactory.getLogger(XsPayServiceImpl.class);

    @Override
    protected void log() {
        LOGGER.info("xs pay start");
        List<Student> students = studentMapper.selectAll();
        LOGGER.info("students = {}", JSON.toJSONString(students));
    }
}
