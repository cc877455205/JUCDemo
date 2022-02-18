package com.atguigu.lock;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 * @Description: list线程不安全
 * @Date:Create：in 2022/2/11 19:08
 */
public class TreadDemo {
    public static void main(String[] args) {

        //1.vector解决
//        List<String> list = new Vector<>();
        //2.Collections解决
//        List<String> list = Collections.synchronizedList(new ArrayList<>());

        //3.CopyOnWriteArrayList解决
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //向集合添加内容
                list.add(UUID.randomUUID().toString().substring(0, 8));

                //从集合中获取内容
                System.out.println(list);

            }, String.valueOf(i)).start();

        }

        Set<String> set = new CopyOnWriteArraySet<>();
        Map<String,String> map = new ConcurrentHashMap<>();
    }
}
