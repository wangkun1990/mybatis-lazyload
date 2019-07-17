package com.mybatis.controller;

import com.mybatis.async.AbstractTracerRunnable;
import com.mybatis.service.AsyncService;
import com.mybatis.service.impl.SyncServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping
@RestController
public class SyncController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncController.class);

    @Autowired
    private SyncServiceImpl syncService;

    @Resource
    private AsyncService asyncService;

    @GetMapping(value = "/async")
    public void async() {
        syncService.async();
    }

    @GetMapping(value = "/sync")
    public void sync() {
        syncService.sync();
    }


    @GetMapping(value = "/async1")
    public void async1() {
        syncService.async1();
    }


    @GetMapping(value = "/deferredResult")
    public DeferredResult<String> deferredResult() {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        deferredResult.setResult("OK");
        deferredResult.setResultHandler((Object o) -> {
            System.out.println(o);
        });
        return deferredResult;
    }

    @GetMapping(value = "/springAsync")
    public String springAsync() {
        LOGGER.info(this.getClass() + "springAsync start");
        long start = System.currentTimeMillis();
        asyncService.asyncTest();
        LOGGER.info("cost time = {}", System.currentTimeMillis() - start);
        return "ok";
    }

    @PostMapping(value = "/httpServletSyncTest")
    public void httpServletSyncTest(HttpServletRequest request) {
        LOGGER.info("main thread request method = {}", request.getMethod());
        new Thread(new AbstractTracerRunnable() {
            @Override
            public void runWithMDC() {
                // 有可能获取不到，主线程结束后HttpServletRequest对象就会被销毁，但是子线程的request.getMethod()为什不报空指针
                LOGGER.info("child thread request method = {}", request.getMethod());
            }
        }).start();
    }
}
