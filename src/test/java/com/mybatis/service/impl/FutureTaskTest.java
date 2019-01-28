package com.mybatis.service.impl;

import java.util.concurrent.FutureTask;

public class FutureTaskTest {
    public static void main(String[] args) throws Exception {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            try {
                System.out.println("start...");
                Thread.sleep(4000);
                System.out.println("end...");
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("被中断了...");
            }

            System.out.println("===============");
        }, 100);
        System.out.println("isDone1 = " + futureTask.isDone());
        System.out.println("isCancelled1 = " + futureTask.isCancelled());
        Thread thread = new Thread(() -> {
            System.out.println("run");
            futureTask.run();
        });
        thread.setName("FutureTask");
        thread.start();
        Thread.sleep(2000);
        futureTask.cancel(true);
        System.out.println("isDone2 = " + futureTask.isDone());
        System.out.println("isCancelled2 = " + futureTask.isCancelled());
    }
}
