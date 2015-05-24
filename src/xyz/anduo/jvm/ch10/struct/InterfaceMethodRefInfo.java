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
public class InterfaceMethodRefInfo {
    private byte tag;
    private short classIndex;
    private short nameAndTypeIndex;

    public InterfaceMethodRefInfo(byte tag) {
        this.tag = tag;
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public short getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(short classIndex) {
        this.classIndex = classIndex;
    }

    public short getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    public void setNameAndTypeIndex(short nameAndTypeIndex) {
        this.nameAndTypeIndex = nameAndTypeIndex;
    }
}
