package com.mybatis.service.impl;

import com.hystrix.CommandHelloWorld;
import org.junit.Test;

import javax.annotation.Resource;

public class CommandHelloWorldTest extends AbstractBaseTest {

    @Resource
    private CommandHelloWorld commandHelloWorld;

    @Test
    public void test() {
        String result = commandHelloWorld.execute();
        System.out.println("isCircuitBreakerOpen = " + commandHelloWorld.isCircuitBreakerOpen());
        System.out.println("isResponseFromFallback = " + commandHelloWorld.isResponseFromFallback());
        System.out.println("isResponseFromCache = " + commandHelloWorld.isResponseFromCache());
        System.out.println(result);
    }
}
