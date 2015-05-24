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
public class MethodHandleInfo {
    private byte tag;
    private byte referenceKind;
    private short referenceIndex;

    public MethodHandleInfo(byte tag) {
        this.tag = tag;
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public byte getReferenceKind() {
        return referenceKind;
    }

    public void setReferenceKind(byte referenceKind) {
        this.referenceKind = referenceKind;
    }

    public short getReferenceIndex() {
        return referenceIndex;
    }

    public void setReferenceIndex(short referenceIndex) {
        this.referenceIndex = referenceIndex;
    }
}
