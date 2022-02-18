package com.atguigu.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *
 * @Description: juc辅助类---信号灯
 * @Date:Create：in 2022/2/16 15:09
 */
//模拟6辆汽车停入3个车位
public class SemaphoreDemo {
    public static void main(String[] args) {
        //创建Semaphore，设置许可数量
        Semaphore semaphore = new Semaphore(3);

        //模拟6辆汽车
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    //抢占
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 抢到了车位");

                    //设置随机停车时间
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));

                    System.out.println(Thread.currentThread().getName() + "---离开了车位");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //释放许可
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }

}
