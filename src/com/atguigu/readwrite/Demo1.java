package com.atguigu.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @Description:
 * @Date:Create：in 2022/2/17 17:00
 */
public class Demo1 {
    public static void main(String[] args) {
        //可重入锁读写锁对象
        ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwlock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwlock.writeLock();

        //降级锁
        //1.获取写锁
        writeLock.lock();
        System.out.println("atguigu");

        //2.获取读锁
        readLock.lock();
        System.out.println("---read");

        //3.释放写锁
        writeLock.unlock();
        //4.释放读锁
        readLock.unlock();

    }
}
