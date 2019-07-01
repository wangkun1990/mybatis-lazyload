package com.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.stereotype.Service;

@Service
public class CommandHelloWorld extends HystrixCommand<String> {

    public CommandHelloWorld() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(2000)
                        // This property, if true, forces the circuit breaker into an open (tripped) state in which it will reject all requests
                        //.withCircuitBreakerForceOpen(true)
                        .withExecutionIsolationThreadInterruptOnTimeout(false)
                        .withMetricsRollingStatisticalWindowInMilliseconds(10000)
                        .withMetricsRollingStatisticalWindowBuckets(10)
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)
                        .withCircuitBreakerErrorThresholdPercentage(50)
                        // This property sets the minimum number of requests in a rolling window that will trip the circuit.
                        // 设置在滑动窗口内可以触发断路的最小请求数量
                        //For example, if the value is 20, then if only 19 requests are received in the rolling window (say a window of 10 seconds) the circuit will not trip open even if all 19 failed.
                        // 例如，设置为20，但是在一个滑动窗口内收到了19个请求，但是这些请求即使全部失败也不会触发断路
                        .withCircuitBreakerRequestVolumeThreshold(20)
                        //This property, if true, forces the circuit breaker into a closed state in which it will allow requests regardless of the error percentage
                        .withCircuitBreakerForceClosed(false)));
    }

    @Override
    protected String run() throws Exception {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            System.err.println(e);
        }
        System.out.println("-------------------");
        return "hello";
    }

    @Override
    protected String getFallback() {
        return "fallBack";
    }
}
