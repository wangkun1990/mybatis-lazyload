package com.mybatis.service.impl;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.base.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class GuavaRetryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaRetryTest.class);

    public static void main(String[] args) {
        Retryer<Boolean> retryer = RetryerBuilder
                .<Boolean>newBuilder()
                //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                .retryIfException()
                //返回false也需要重试
                .retryIfResult(Predicates.equalTo(false))
                //重调策略
                //.withWaitStrategy(WaitStrategies.fixedWait(10, TimeUnit.SECONDS))
                //尝试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();

        try {
            retryer.call(() -> {
                try {
                    int i = 1 / 0;
                    return true;
                } catch (Exception e) {
                    LOGGER.error("", e);
                    return false;
                }
            });
        } catch (ExecutionException | RetryException e) {
            LOGGER.error("重试失败 = ", e);
        }
    }
}
