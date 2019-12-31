package com.mybatis.service;

import com.mybatis.mapper.StudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public abstract class AbstractPayService implements PayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPayService.class);

    @Resource
    protected StudentMapper studentMapper;

    @Override
    public void pay() {
        LOGGER.info("pay start");
        log();
    }

    protected abstract void log();
}
