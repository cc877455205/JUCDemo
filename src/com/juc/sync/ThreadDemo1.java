package com.juc.sync;

/**
 *
 * @Description: Synchronized实现线程间通信
 * @Date:Create：in 2022/2/10 15:08
 */
//第一步 创建资源类，定义属性和操作方法
class Share {
    //初始值
    private int number = 0;

    //+1的方法
    public synchronized void incr() throws InterruptedException {
        //第二步 判断 干活 通知
        while (number != 0) {//判断number值是否是0，如果不是0等待
            this.wait();
        }
        //如果number是0，就+1操作
        number++;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        //通知其他线程
        this.notifyAll();
    }

    //-1的方法
    public synchronized void decr() throws InterruptedException {
        //第二步 判断 干活 通知
        while (number != 1) {//判断number值是否是0，如果不是0等待
            this.wait();
        }
        //如果number是0，就-1操作
        number--;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        //通知其他线程
        this.notifyAll();
    }
}

public class ThreadDemo1 {
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
