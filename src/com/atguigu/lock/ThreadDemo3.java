package com.atguigu.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @Description: 线程间定制化通信
 * @Date:Create：in 2022/2/11 16:15
 */
//第一步 创建资源类
class ShareResource {
    //定义标志位
    private int flag = 1;

    //创建lock锁
    private Lock lock = new ReentrantLock();

    //创建三个condition
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    //打印5次，参数 第几轮
    public void print5(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (1 != flag) {
                //等待
                c1.await();
            }

            //干活
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " :" + i + ": " + loop);
            }
            //通知
            flag = 2;//修改标志位的值
            c2.signal();//通知BB 线程

        } finally {
            //释放锁
            lock.unlock();
        }
    }

    //打印10次，参数 第几轮
    public void print10(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (2 != flag) {
                //等待
                c2.await();
            }

            //干活
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " :" + i + ": " + loop);
            }
            //通知
            flag = 3;//修改标志位的值
            c3.signal();//通知CC 线程

        } finally {
            //释放锁
            lock.unlock();
        }
    }

    //打印15次，参数 第几轮
    public void print15(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (3 != flag) {
                //等待
                c3.await();
            }

            //干活
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + " :" + i + ": " + loop);
            }
            //通知
            flag = 1;//修改标志位的值
            c1.signal();//通知AA 线程

        } finally {
            //释放锁
            lock.unlock();
        }
    }
}

public class ThreadDemo3 {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    shareResource.print5(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    shareResource.print10(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    shareResource.print15(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();
    }
}
