package com.mybatis.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest {

    static ExecutorService executorService = Executors.newFixedThreadPool(1);

    static ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        /*ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("hello from ThreadLocal");

        System.out.println(threadLocal.get());
        new Thread(() -> {
            // 获取不到
            System.out.println(threadLocal.get());
        }).start();



        inheritableThreadLocal.set("hello from InheritableThreadLocal");
        System.out.println(inheritableThreadLocal.get());
        new Thread(() -> {
            // 可以获取到
            System.out.println(inheritableThreadLocal.get());
        }).start();*/

        inheritableThreadLocal.set("hello from InheritableThreadLocal");

        executorService.execute(() -> {
            System.out.println(inheritableThreadLocal.get());
        });

        inheritableThreadLocal.set("hello from InheritableThreadLocal------");

        executorService.execute(() -> {
            System.out.println(inheritableThreadLocal.get());
        });
        System.out.println("main thread get = " + inheritableThreadLocal.get());
        executorService.shutdown();
    }
}
