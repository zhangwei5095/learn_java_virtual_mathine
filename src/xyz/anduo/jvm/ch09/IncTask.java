// Copyright (C) 2015 anduo
// All rights reserved
package xyz.anduo.jvm.ch09;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Summary: 带锁+任务
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/5/16
 * time   : 22:46
 */
public class IncTask implements Runnable {

    private MyInt myInt;//使用ReentrantLock来控制+操作的线程安全
    private int maxValue;//最大阀值

    public IncTask(MyInt myInt, int maxValue) {
        this.myInt = myInt;
        this.maxValue = maxValue;
    }

    @Override public void run() {
        int value = myInt.increase();
        while (value < maxValue) {
            value = myInt.increase();
        }
    }

}



