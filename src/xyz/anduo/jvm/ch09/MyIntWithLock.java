// Copyright (C) 2015 anduo
// All rights reserved
package xyz.anduo.jvm.ch09;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/5/16
 * time   : 23:34
 */
class MyIntWithLock implements MyInt{

    private int value;
    private Lock lock = new ReentrantLock();//锁对象

    public MyIntWithLock(int value) {
        this.value = value;
    }

    @Override
    public int increase() {
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
        return value;
    }

}
