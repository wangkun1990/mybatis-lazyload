package com.mybatis.service.impl;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    public static void main(String[] args) throws Exception {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
    }

    public static void test1() throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
            completableFuture.complete("finish");
        }).start();
        String result = completableFuture.get();
        System.out.println("test1 cost time = " + (System.currentTimeMillis() - start));
        System.out.println("test1 result = " + result);
    }

    public static void test2() throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }
            return "finish";
        });
        String result = completableFuture.get();
        System.out.println("test2 cost time = " + (System.currentTimeMillis() - start));
        System.out.println("test2 result = " + result);
    }

    public static void test3() throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test3 return result1");
            //返回结果
            return "result1";
        });

        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test3 return result2");
            //返回结果
            return "result2";
        });

        CompletableFuture<Object> anyResult = CompletableFuture.anyOf(completableFuture1, completableFuture2);

        System.out.println("第一个完成的任务结果:" + anyResult.get());
        System.out.println("test3 cost time = " + (System.currentTimeMillis() - start));
        CompletableFuture<Void> allResult = CompletableFuture.allOf(completableFuture1, completableFuture2);

        //阻塞等待所有任务执行完成
        allResult.join();
        System.out.println("test3 cost time = " + (System.currentTimeMillis() - start));
        System.out.println("所有任务执行完成");

    }


    public static void test4() throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result1";
        });

        //等第一个任务完成后，将任务结果传给参数result，执行后面的任务并返回一个代表任务的completableFuture
        CompletableFuture<String> completableFuture2 = completableFuture1.thenCompose(result -> CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result2";
        }));
        System.out.println(completableFuture2.get());
        System.out.println("test4 cost time = " + (System.currentTimeMillis() - start));

    }


    public static void test5() throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return 100;
        });

        //将第一个任务与第二个任务组合一起执行，都执行完成后，将两个任务的结果合并
        CompletableFuture<Integer> completableFuture2 = completableFuture1.thenCombine(
                //第二个任务
                CompletableFuture.supplyAsync(() -> {
                    //模拟执行耗时任务
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //返回结果
                    return 2000;
                }),
                //合并函数
                (result1, result2) -> result1 + result2);

        System.out.println(completableFuture2.get());
        System.out.println("test5 cost time = " + (System.currentTimeMillis() - start));
    }

    public static void test6() throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return 100;
        });

        //注册完成事件
        completableFuture1.thenAccept(result -> System.out.println("task1 done,result:" + result));
        System.out.println("test6 completableFuture1 finish cost time = " + (System.currentTimeMillis() - start));

        CompletableFuture<Integer> completableFuture2 =
                //第二个任务
                CompletableFuture.supplyAsync(() -> {
                    //模拟执行耗时任务
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //返回结果
                    return 2000;
                });

        //注册完成事件
        completableFuture2.thenAccept(result -> System.out.println("task2 done,result:" + result));
        System.out.println("test6 completableFuture2 finish cost time = " + (System.currentTimeMillis() - start));
        //将第一个任务与第二个任务组合一起执行，都执行完成后，将两个任务的结果合并
        CompletableFuture<Integer> completableFuture3 = completableFuture1.thenCombine(completableFuture2,
                //合并函数
                (result1, result2) -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return result1 + result2;
                });
        System.out.println("test6 completableFuture3 finish cost time = " + (System.currentTimeMillis() - start));
        System.out.println("test6 result = " + completableFuture3.get());
        System.out.println("test6 cost time = " + (System.currentTimeMillis() - start));
    }

}
