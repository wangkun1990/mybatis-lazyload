package com.mybatis.beanconfigure;

import com.mybatis.async.TracerThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Component
@Configurable
public class ExecutorBeanConfig {

    @Bean(value = "asyncExecutor")
    public Executor contextAwarePoolExecutor() {
        return TracerThreadPoolExecutor.newWithInheritedMdc(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(500));
    }
}
