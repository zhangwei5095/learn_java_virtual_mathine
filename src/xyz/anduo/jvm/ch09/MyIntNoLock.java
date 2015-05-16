// Copyright (C) 2015 meituan
// All rights reserved
package xyz.anduo.jvm.ch09;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Summary: TODO 描述信息
 * Author : anduo@meituan.com
 * Version: 1.0
 * Date   : 15/5/16
 * time   : 23:35
 */
class MyIntNoLock implements MyInt{

    private AtomicInteger value;

    public MyIntNoLock(AtomicInteger value) {
        this.value = value;
    }

    public int increase() {
        return value.getAndIncrement();
    }

}
