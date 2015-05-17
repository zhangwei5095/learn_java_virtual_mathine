// Copyright (C) 2015 anduo
// All rights reserved
package xyz.anduo.jvm.ch09;

/**
 * Summary: 使用synchronized的myint
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/5/16
 * time   : 23:58
 */
public class MyIntSynchronized implements  MyInt{
    private int value;

    public MyIntSynchronized(int value) {
        this.value = value;
    }

    @Override public int increase() {
        synchronized (this){
            value ++;
        }
        return value;
    }
}
