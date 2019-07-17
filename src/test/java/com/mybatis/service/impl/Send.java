package com.mybatis.service.impl;

import com.alibaba.fastjson.JSON;
import com.mybatis.entity.Student;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Random;

public class Send {

    private final static String QUEUE_NAME = "student";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String[] firstName = {"张", "王"};
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                Student student = new Student();
                student.setAge(new Random().nextInt(20));
                student.setName(firstName[i % firstName.length] + i);
                channel.basicPublish("", QUEUE_NAME, null, JSON.toJSONBytes(student));
                try {
                    Thread.sleep(100);
                } catch (Exception e) {

                }
            }
        }
    }
}
