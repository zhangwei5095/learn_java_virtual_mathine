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
public class InvokeDynamicInfo {
    private byte tag;
    private short bootstrapMethodAttrIndex;
    private short nameAndTypeIndex;

    public InvokeDynamicInfo(byte tag) {
        this.tag = tag;
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public short getBootstrapMethodAttrIndex() {
        return bootstrapMethodAttrIndex;
    }

    public void setBootstrapMethodAttrIndex(short bootstrapMethodAttrIndex) {
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
    }

    public short getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    public void setNameAndTypeIndex(short nameAndTypeIndex) {
        this.nameAndTypeIndex = nameAndTypeIndex;
    }
}
