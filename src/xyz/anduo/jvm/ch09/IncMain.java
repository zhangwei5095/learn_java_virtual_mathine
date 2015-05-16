// Copyright (C) 2015 anduo
// All rights reserved
package xyz.anduo.jvm.ch09;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Summary: 锁性能测试
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/5/16
 * time   : 23:13
 */
public class IncMain {

    public static void main(String[] args) {
        int maxValue = 1000000;
        int[] threadNums = new int[]{1,3,10,30,300,1000};
        for (int i = 0; i < threadNums.length; i++) {
            test(threadNums[i],maxValue,new MyIntSynchronized(0));
            test(threadNums[i],maxValue,new MyIntWithLock(0));
            test(threadNums[i],maxValue,new MyIntNoLock(new AtomicInteger(0)));
        }


    }

    private static void test(int threadNum, int maxValue, MyInt myInt) {
        String hasLock;
        if (myInt instanceof MyIntNoLock) {
            hasLock = "无";
        } else if (myInt instanceof MyIntSynchronized){
            hasLock = "有Synchronized";
        }else {
            hasLock = "有ReentrantLock";
        }

        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNum; i++) {
            exec.execute(new IncTask(myInt, maxValue));
        }
        while (!exec.isTerminated()) {
            exec.shutdown();
        }

        long endTime = System.currentTimeMillis();

        System.out.printf("线程数为%-4d的情况下【%-14s锁】的执行时间是:%5d ms \n", threadNum, hasLock, endTime - startTime);
    }

}
