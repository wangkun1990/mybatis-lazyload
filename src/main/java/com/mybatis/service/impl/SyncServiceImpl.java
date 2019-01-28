package com.mybatis.service.impl;

import com.mybatis.entity.Department;
import com.mybatis.entity.Student;
import com.mybatis.mapper.StudentMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class SyncServiceImpl {

    private static final ExecutorService BOSS_EXECUTOR_SERVICE = new ThreadPoolExecutor(2, Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));


    private static final ExecutorService WORKER_EXECUTOR_SERVICE = new ThreadPoolExecutor(2, Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncServiceImpl.class);
    @Autowired
    private StudentMapper studentMapper;

    @Resource
    private SyncServiceImpl self;

    public void async() {

        long start = System.currentTimeMillis();
        List<String> urls = getUrls();


        BOSS_EXECUTOR_SERVICE.submit(() -> {
            long start1 = System.currentTimeMillis();
            try {
                final List<Student> students = new ArrayList<Student>(urls.size());
                for (final String url : urls) {
                    try {
                        long asyncFutureStart = System.currentTimeMillis();
                        Future<String> future = WORKER_EXECUTOR_SERVICE.submit(() -> {
                            // fb系统的url
                            String fbUrl = StringUtils.EMPTY;

                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                            }
                            return fbUrl;
                        });
                        // 这里导致阻塞
                        System.out.println("fbUrl = " + future.get());
                        System.out.println("async get cost time = " + (System.currentTimeMillis() - asyncFutureStart));
                        students.add(assembleStudent(url));
                    } catch (Exception e) {
                        LOGGER.error("异步执行图片上传异常 = ", e);
                    }
                }
                self.batchInsert(students);
            } catch (Exception e) {
                LOGGER.error("异步处理任务异常 = ", e);
            }
            System.out.println((System.currentTimeMillis() - start1));
        });

        System.out.println(SyncServiceImpl.class.getClass() + "async cost time = " + (System.currentTimeMillis() - start));
    }


    private Student assembleStudent(String url) {
        Student student = new Student();
        student.setName(url.substring(0, 5));
        Department department = new Department();
        department.setId(1);
        student.setDepartment(department);
        student.setSex(1);
        return student;
    }

    public void async1() {
        long start = System.currentTimeMillis();
        List<String> urls = getUrls();
        BOSS_EXECUTOR_SERVICE.submit(() -> {
            long start1 = System.currentTimeMillis();
            try {
                final List<Student> students = new ArrayList<Student>(urls.size());
                List<Future> futures = new ArrayList<>();
                for (final String url : urls) {
                    try {
                        Future<Student> future = WORKER_EXECUTOR_SERVICE.submit(() -> {
                            // fb系统的url
                            String fbUrl = StringUtils.EMPTY;

                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                            }

                            return assembleStudent(url);
                        });
                        futures.add(future);
                    } catch (Exception e) {
                        LOGGER.error("异步执行图片上传异常 = ", e);
                    }
                }
                long async1BatchFutureGet = System.currentTimeMillis();
                for (Future<Student> future : futures) {
                    Student student = future.get();
                    students.add(student);
                }
                System.out.println("async1BatchFutureGet cost time = " + (System.currentTimeMillis() - async1BatchFutureGet));
                self.batchInsert(students);
            } catch (Exception e) {
                LOGGER.error("异步处理任务异常 = ", e);
            }
            System.out.println((System.currentTimeMillis() - start1));
        });

        System.out.println(SyncServiceImpl.class.getClass() + "async cost time = " + (System.currentTimeMillis() - start));
    }


    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<Student> studentList) {
        for (Student student : studentList) {
            System.out.println(student);
            studentMapper.insert(student);
        }
    }

    private List<String> getUrls() {

        List<String> urls = new ArrayList<>();
        int total = 20;
        for (int i = 0; i < total; i++) {
            urls.add("http://www.rabbitmq.com/img/tutorials/python-five.png");
        }
        return urls;
    }

    public void sync() {
        List<String> urls = getUrls();
        long start = System.currentTimeMillis();
        List<Student> students = new ArrayList<>(urls.size());
        Student student;
        for (String url : urls) {
            // 模拟耗时
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            student = new Student();
            student.setName(url.substring(0, 5));
            Department department = new Department();
            department.setId(1);
            student.setDepartment(department);
            student.setSex(1);
            students.add(student);
        }
        self.batchInsert(students);
        System.out.println(SyncServiceImpl.class.getClass() + "sync cost time = " + (System.currentTimeMillis() - start));
    }
}
