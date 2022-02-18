package com.juc.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @Description: 可重入锁
 * @Date:Create：in 2022/2/14 16:32
 */
public class SyncLockDemo {
    public synchronized void add() {
        add();
    }

    public static void main(String[] args) {
        //lock演示可重入锁
        Lock lock = new ReentrantLock();
        new Thread(() -> {
            try {
                //上锁
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "：外层");

                try {
                    //上锁
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "：内层");
                } finally {
                    //释放锁
                    lock.unlock();
                }
            } finally {
                //释放锁
                lock.unlock();
            }

        }, "T0").start();
        //创建新线程
        new Thread(() -> {
            lock.lock();
            System.out.println("aaaa");
            lock.unlock();
        }, "aa").start();


        //synchronized演示可重入锁
//        Object object = new Object();
//
//        new SyncLockDemo().add();
//        new Thread(() -> {
//            synchronized (object) {
//                System.out.println(Thread.currentThread().getName() + "：外层");
//                synchronized (object) {
//                    System.out.println(Thread.currentThread().getName() + "：中层");
//                    synchronized (object) {
//                        System.out.println(Thread.currentThread().getName() + "：内层");
//                    }
//                }
//            }
//        }, "T1").start();
    }
}
