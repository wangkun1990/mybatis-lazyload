package com.mybatis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RequestMapping
@RestController
public class HttpTestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpTestController.class);

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());

    @RequestMapping(value = "/sleep/{time}")
    public String sleep(@PathVariable(value = "time") Integer time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {

        }
        return "success";
    }

    @GetMapping(value = "/npe")
    public void npe() {
        try {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 500000; i++) {
                        try {
                            String a = null;
                            a.toLowerCase();
                            Thread.sleep(1);
                        } catch (Exception e) {
                            LOGGER.error("Thread Exception = ", e);
                        };
                    }
                }
            });
        } catch (Exception e) {
            LOGGER.error("Exception = ", e);
        }
    }
}
