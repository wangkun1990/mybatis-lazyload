package com.mybatis.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.mybatis.entity.Student;
import com.mybatis.util.TracerIdGenerator;
import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

public class RabbitMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMessageListener.class);

    @Resource
    private Chain chain;

    /**
     * 不使用AOP拦截给traceId赋值
     * @param message
     */
    @RabbitListener(queues = "student")
    public void message(byte[] message) {
        MDC.put(TracerIdGenerator.TRACE_ID, TracerIdGenerator.generate());
        String json = new String(message);
        LOGGER.info("RabbitMessageListener message = {}", json);
        Context context = new ContextBase();
        context.put("student", JSON.parseObject(json, Student.class));
        try {
            chain.execute(context);
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            LOGGER.error("chain execute error", e);
        } finally {
            MDC.remove(TracerIdGenerator.TRACE_ID);
        }
    }
}
