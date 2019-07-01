package com.mybatis.service.impl;

import com.mybatis.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class AsyncServiceImpl implements AsyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Async(value = "asyncExecutor")
    @Override
    public void asyncTest() {
        LOGGER.info("asyncTest start");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {

        }
        LOGGER.info("asyncTest end");
    }
}
