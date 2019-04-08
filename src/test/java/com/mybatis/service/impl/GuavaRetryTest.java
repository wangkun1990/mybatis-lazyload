package com.mybatis.service.impl;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.util.concurrent.ExecutionException;

public class GuavaRetryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaRetryTest.class);

    public static void main(String[] args) {
        /*Retryer<Boolean> retryer = RetryerBuilder
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
        }*/
        test();
        test2();
    }

    public static void test() {
        Retryer<Boolean> retryer = RetryerBuilder
                .<Boolean>newBuilder()
                //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                .retryIfException()
                //返回false也需要重试
                .retryIfResult(Predicates.equalTo(false))
                //重调策略
                //.withWaitStrategy(WaitStrategies.fixedWait(10, TimeUnit.SECONDS))
                //尝试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(1))
                .build();
        try {
            retryer.call(()-> {
               try {
                   String s = null;
                   s.toLowerCase();
                   return true;
               } catch (Exception e) {
                   LOGGER.error("Exception = ", e);
                   throw new ExtensionException(1, e.getMessage(), e);
                   //return false;
               }
            });
        } catch (RetryException e) {
            Attempt attempt = e.getLastFailedAttempt();
            if (attempt.hasException()) {
                LOGGER.error("e.getLastFailedAttempt().getExceptionCause() = ", attempt.getExceptionCause());
                LOGGER.warn("instanceof Exception = {}", attempt.getExceptionCause() instanceof Exception);
            } else {
                LOGGER.error("RetryException = ", e);
            }
        } catch (ExecutionException e) {
            LOGGER.error("ExecutionException = ", e);
        }
    }

    public static void test2() {
        try {
            try {
                int i = 1 / 0;
            } catch (Exception e) {
                LOGGER.error("Exception = ", e);
                throw new ExtensionException(1, "除数为零", e);
            }
        } catch (Exception e) {
            LOGGER.error("Exception = ", e);
            LOGGER.error("e.getCause() = ", e.getCause());
        }
    }
}

class ExtensionException extends RuntimeException {

    private int errCode;

    private String errMsg;

    public ExtensionException(int errCode, String errMsg, Throwable e) {
        super(e);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}