package com.mybatis.controller;

import com.alibaba.fastjson.JSON;
import com.mybatis.annotation.ResponseEncryptBody;
import com.mybatis.async.AbstractTracerRunnable;
import com.mybatis.entity.Student;
import com.mybatis.service.IStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RequestMapping
@RestController
public class HttpTestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpTestController.class);

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());

    private ExecutorService fixExecutorService = new ThreadPoolExecutor(2, 2,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    @Resource
    private IStudentService studentService;

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
            threadPoolExecutor.submit(() -> {
                for (int i = 0; i < 1; i++) {
                    try {
                        String a = null;
                        a.toLowerCase();
                        Thread.sleep(1);
                    } catch (Exception e) {
                        LOGGER.error("Thread Exception = ", e);
                    }
                    ;
                }
            });
        } catch (Exception e) {
            LOGGER.error("Exception = ", e);
        }
    }

    @GetMapping(value = "/get")
    public Student get() {
        LOGGER.info("get method start thread id = {}", Thread.currentThread().getId());

        AbstractTracerRunnable tracerRunnable = new AbstractTracerRunnable() {
            @Override
            public void runWithMDC() {
                System.out.println(Thread.currentThread().getId());
                try {
                    Thread.sleep(100);
                } catch (Exception e) {

                }
            }
        };
        new Thread(new AbstractTracerRunnable() {
            @Override
            public void runWithMDC() {
                LOGGER.info("a new thread id = {}", Thread.currentThread().getId());
                studentService.selectOne(1);
            }
        }).start();
        for (int i = 0; i < 100; i++) {
            fixExecutorService.execute(tracerRunnable);
        }
        Student student = studentService.selectOne(1);
        LOGGER.info("result = {}, thread id = {}", JSON.toJSONString(student), Thread.currentThread().getId());
        return student;
    }

    @GetMapping(value = "/getStudent/{id}")
    public Student getStudent(@PathVariable("id") Integer id) {
        LOGGER.info("get getStudent method.param id = {} ,start thread id = {}", id, Thread.currentThread().getId());
        Student student = studentService.selectOne(id);
        LOGGER.info("result = {}, thread id = {}", JSON.toJSONString(student), Thread.currentThread().getId());
        return student;
    }

    @RequestMapping(value = "/student")
    @ResponseEncryptBody
    public Student test(Student student) {
        return student;
    }
}