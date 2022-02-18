package com.atguigu.juc;

import java.util.concurrent.CyclicBarrier;

/**
 *
 * @Description: juc辅助类---循环栅栏
 * @Date:Create：in 2022/2/16 14:10
 */
public class CyclicBarrierDemo {

    //创建固定值
    public static final int NUMBER = 7;

    public static void main(String[] args) {
        //创建CyclicBarrier对象
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER, () -> {

            System.out.println("***集齐7颗龙珠召唤神龙***");
        });

        //集齐7颗龙珠的过程
        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 星龙珠被收集到了");
                    //等待
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

    }
}
