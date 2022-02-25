package com.juc.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @Description: Fork 与 Join分支
 * @Date:Create：in 2022/2/25 11:06
 */
class MyTask extends RecursiveTask<Integer> {

    //拆分差值不能超过10
    private static final Integer VALUE = 10;
    private int begin;//拆分开始值
    private int end;//拆分结束值
    private int result;//返回结果值

    //创建有参构造
    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    //拆分和合并过程
    @Override
    protected Integer compute() {
        //判断相加两个数是否大于10
        if ((end - begin) <= VALUE) {
            //相加操作
            for (int i = begin; i <= end; i++) {
                result = result + i;
            }
        } else {//进一步拆分
            //获取中间值
            int middle = (begin + end) / 2;
            //拆分左边
            MyTask myTask1 = new MyTask(begin, middle);
            //拆分右边
            MyTask myTask2 = new MyTask(middle + 1, end);

            myTask1.fork();
            myTask2.fork();
            //合并结果
            result = myTask1.join() + myTask2.join();
        }
        return result;
    }
}

public class ForkJoinDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建MyTask对象
        MyTask myTask = new MyTask(0, 100);

        //创建分支合并池对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
        //获取最终合并之后结果
        Integer result = forkJoinTask.get();
        System.out.println(result);
        //关闭池对象
        forkJoinPool.shutdown();
    }
}
