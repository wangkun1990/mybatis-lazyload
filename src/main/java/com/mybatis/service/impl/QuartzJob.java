package com.mybatis.service.impl;

import com.mybatis.async.AbstractTracerRunnable;
import com.mybatis.service.IStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuartzJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJob.class);

    @Autowired
    private IStudentService studentService;

    @Scheduled(cron = "0/20 * *  * * ?")
    public void test() {
        LOGGER.info("job execute at {}", new Date());
        new Thread(new AbstractTracerRunnable() {
            @Override
            public void runWithMDC() {
                LOGGER.info("run from QuartzJob");
                studentService.selectOne(1);
            }
        }).start();
    }
}
