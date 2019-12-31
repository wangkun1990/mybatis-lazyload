package com.mybatis.service;

import com.mybatis.service.impl.AbstractBaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class PayServiceTest extends AbstractBaseTest {

    @Resource(name = "aliPayServiceImpl")
    private PayService aliPayService;
    @Resource(name = "xsPayServiceImpl")
    private PayService xsPayService;

    @Test
    public void payTest() {
        //aliPayService.pay();

        xsPayService.pay();
    }
}
