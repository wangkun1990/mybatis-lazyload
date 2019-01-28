package com.mybatis.service.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncTest {
    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        int a = method1();
        int b = method2();
        int c = method3();
        System.out.println("cost time = " + (System.currentTimeMillis() - start));
        int result = a + b + c;
        System.out.println("result = " + result);

        ExecutorService ex = Executors.newFixedThreadPool(3);

        start = System.currentTimeMillis();
        Future result1 = ex.submit(() -> {
            try {
                Thread.sleep(1000);
                return 1;
            } catch (Exception e) {
                return 0;
            }
        });

        Future result2 = ex.submit(() -> {
            try {
                Thread.sleep(2000);
                return 2;
            } catch (Exception e) {
                return 0;
            }
        });

        Future result3 = ex.submit(() -> {
            try {
                Thread.sleep(3000);
                return 3;
            } catch (Exception e) {
                return 0;
            }
        });
        a = (Integer) result1.get();
        b = (Integer) result2.get();
        c = (Integer) result3.get();
        System.out.println("Future cost time = " + (System.currentTimeMillis() - start));
        result = a + b + c;
        System.out.println("result = " + result);

        ex.shutdown();

        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(3));

        start = System.currentTimeMillis();
        ListenableFuture<Integer> listenableFuture1 = listeningExecutorService.submit(() -> {
            try {
                Thread.sleep(1000);
                return 1;
            } catch (Exception e) {
                return 0;
            }
        });

        ListenableFuture<Integer> listenableFuture2 = listeningExecutorService.submit(() -> {
            try {
                Thread.sleep(2000);
                return 2;
            } catch (Exception e) {
                return 0;
            }
        });


        ListenableFuture<Integer> listenableFuture3 = listeningExecutorService.submit(() -> {
            try {
                Thread.sleep(3000);
                return 3;
            } catch (Exception e) {
                return 0;
            }
        });

        result = listenableFuture1.get() + listenableFuture2.get() + listenableFuture3.get();

        System.out.println("ListenableFuture cost time = " + (System.currentTimeMillis() - start));
        System.out.println("ListenableFuture result = " + result);
        listeningExecutorService.shutdown();


        start = System.currentTimeMillis();
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> method1());
        //CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> method2());

        //CompletableFuture<Integer> completableFuture3 = CompletableFuture.supplyAsync(() -> method3());

        CompletableFuture<Integer> completableFuture4 = completableFuture1.thenCombine(
                //第二个任务
                CompletableFuture.supplyAsync(() -> method2()),
                //合并函数
                (r1, r2) -> r1 + r2).thenCombine(CompletableFuture.supplyAsync(() -> method3()), (r3, r4) -> r3 + r4);


        //result = completableFuture1.get() + completableFuture2.get() + completableFuture3.get();
        result = completableFuture4.get();
        System.out.println("CompletableFuture cost time = " + (System.currentTimeMillis() - start));
        System.out.println("CompletableFuture result = " + result);
    }

    public static Integer method1() {
        try {
            Thread.sleep(1000);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int method2() {
        try {
            Thread.sleep(2000);
            return 2;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int method3() {
        try {
            Thread.sleep(3000);
            return 3;
        } catch (Exception e) {
            return 0;
        }
    }
}
