// Copyright (C) 2015 anduo
// All rights reserved
package xyz.anduo.jvm.ch10.struct;

/**
 * Summary: TODO 描述信息
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/5/24
 * time   : 18:21
 */
public class DoubleInfo {
    private byte tag;
    private double value;

    public DoubleInfo(byte tag, double value) {
        this.tag = tag;
        this.value = value;
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
