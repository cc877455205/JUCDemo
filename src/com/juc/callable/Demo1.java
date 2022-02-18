package com.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *
 * @Description: 比较Runnable接口和Callable接口
 * @Date:Create：in 2022/2/15 14:17
 */
class MyThread1 implements Runnable {

    @Override
    public void run() {

    }
}

class MyThread2 implements Callable {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " come in callable");
        return 200;
    }
}

public class Demo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Runnable接口实现
//        new Thread(new MyThread1(), "Runnable").start();

        //Callable接口实现
//        new Thread(new MyThread2(), "Callable").start();//报错

        //FutureTask
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread2());
        //lamda表达式
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + " come in callable");
            return 1024;
        });
        new Thread(futureTask2, "Callable").start();
        new Thread(futureTask1, "Runnable").start();

//        while (!futureTask2.isDone()) {
//            System.out.println("wait...........");
//        }

        System.out.println(futureTask2.get());
        System.out.println(futureTask1.get());
        System.out.println(Thread.currentThread().getName() + " come over");

    }

}
