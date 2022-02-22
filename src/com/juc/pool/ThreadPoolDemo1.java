package com.juc.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 线程池的三种常用分类
 * @Date:Create：in 2022/2/22 19:38
 */
public class ThreadPoolDemo1 {
    public static void main(String[] args) {
        //一池五线程
//        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);//五个线程

        //一池一线程
//        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();//一个窗口

        //一池可扩容线程
        ExecutorService threadPool3 = Executors.newCachedThreadPool();
        try {
            //10个顾客请求
            for (int i = 0; i < 10; i++) {
                //执行
                threadPool3.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭
            threadPool3.shutdown();
        }
    }
}
