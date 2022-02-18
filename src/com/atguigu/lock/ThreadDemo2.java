package com.atguigu.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @Description: 通过Lock接口线程间通信
 * @Date:Create：in 2022/2/11 14:35
 */
//第一步 创建资源类，定义属性和操作方法
class Share {
    //初始值
    private int number = 0;
    //创建Lock
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //+1的方法
    public void incr() throws InterruptedException {
        //上锁
        lock.lock();

        try {
            //判断
            while (number != 0) {
                condition.await();
            }
            // 干活
            number++;
            System.out.println(Thread.currentThread().getName() + " :: " + number);
            // 通知
            condition.signalAll();
        } finally {
            //解锁
            lock.unlock();
        }
    }

    //-1的方法
    public void decr() throws InterruptedException {

        lock.lock();
        try {
            //判断
            while (number != 1) {
                condition.await();
            }
            // 干活
            number--;
            System.out.println(Thread.currentThread().getName() + " :: " + number);
            // 通知
            condition.signalAll();
        } finally {
            //解锁
            lock.unlock();
        }
    }
}

public class ThreadDemo2 {
    //第三步 创建多个线程，调用资源类的方法
    public static void main(String[] args) {
        Share share = new Share();
        //创建线程
        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    share.incr();//+1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    share.decr();//-1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    share.incr();//+1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();

        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    share.decr();//-1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DD").start();
    }
}