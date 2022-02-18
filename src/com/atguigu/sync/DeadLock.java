package com.atguigu.sync;


import java.util.concurrent.TimeUnit;

/**
 *
 * @Description: 死锁
 * @Date:Create：in 2022/2/15 10:34
 */
public class DeadLock {

    static Object aa = new Object();
    static Object bb = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (aa) {
                System.out.println(Thread.currentThread().getName() + " 持有锁A，试图获取锁B");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (bb) {
                    System.out.println(Thread.currentThread().getName() + " 获取锁B");
                }
            }

        }, "A").start();

        new Thread(() -> {
            synchronized (bb) {
                System.out.println(Thread.currentThread().getName() + " 持有锁B，试图获取锁A");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (aa) {
                    System.out.println(Thread.currentThread().getName() + " 获取锁A");
                }
            }

        }, "B").start();

    }
}
