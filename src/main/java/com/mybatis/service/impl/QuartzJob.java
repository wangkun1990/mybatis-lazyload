package com.mybatis.service.impl;

import com.alibaba.fastjson.JSON;
import com.mybatis.async.AbstractTracerRunnable;
import com.mybatis.entity.Student;
import com.mybatis.service.IStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class QuartzJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJob.class);

    @Autowired
    private IStudentService studentService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

    @Scheduled(cron = "0/20 * *  * * ?")
    public void rabbitMqSendMessage() {
        String[] firstName = {"张", "王"};
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setAge(new Random().nextInt(20));
            student.setName(firstName[i % firstName.length] + i);
            rabbitTemplate.send(new Message(JSON.toJSONBytes(student), new MessageProperties()));
        }

    }
}
