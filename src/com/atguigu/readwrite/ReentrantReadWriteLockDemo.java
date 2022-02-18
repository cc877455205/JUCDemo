package com.atguigu.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @Description: juc---读写锁案例
 * @Date:Create：in 2022/2/17 14:31
 */
//资源类
class MyCache {

    private volatile Map<String, Object> map = new HashMap<>();

    //创建读写锁对象
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //放数据
    public void put(String key, Object value) {
        //添加写锁
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在进行写操作" + key);
            //暂停一会
            TimeUnit.MICROSECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放写锁
            readWriteLock.writeLock().unlock();
        }
        //放数据
        map.put(key, value);

        System.out.println(Thread.currentThread().getName() + " 写完了" + key);
    }


    //取数据
    public Object get(String key) {
        //添加读锁
        readWriteLock.readLock().lock();
        Object result = null;
        try {
            System.out.println(Thread.currentThread().getName() + " 正在进行读操作" + key);
            //暂停一会
            TimeUnit.MICROSECONDS.sleep(300);
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 读完了" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放读锁
            readWriteLock.readLock().unlock();
        }

        return result;
    }
}

public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) throws InterruptedException {
        MyCache myCache = new MyCache();
        //创建线程放数据
        for (int i = 0; i < 6; i++) {
            final int num = i;
            new Thread(() -> {
                myCache.put(num + "", num + "");
            }, String.valueOf(i)).start();
        }

        TimeUnit.MICROSECONDS.sleep(300);

        //创建线程取数据
        for (int i = 0; i < 6; i++) {
            final int num = i;
            new Thread(() -> {
                myCache.get(num + "");
            }, String.valueOf(i)).start();
        }
    }
}
