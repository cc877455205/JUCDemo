package com.atguigu.juc;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @Description: juc辅助类---CountDownLatch示例
 * @Date:Create：in 2022/2/16 10:49
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        //创建CountDownLatch，设置初始值
        CountDownLatch countDownLatch = new CountDownLatch(6);
        //6个同学离开教室后，班长关门
        for (int i = 0; i < 6; i++) {
            //6个同学离开教室后
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 号同学离开了");
                //计数器 -1
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        //等待
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + " 班长锁门");
    }
}
