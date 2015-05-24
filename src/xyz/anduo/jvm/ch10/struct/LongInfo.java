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
public class LongInfo {
    private byte tag;
    private long value;

    public LongInfo(byte tag, long value) {
        this.tag = tag;
        this.value = value;
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
