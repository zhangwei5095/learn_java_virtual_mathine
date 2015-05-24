// Copyright (C) 2015 qq
// All rights reserved
package xyz.anduo.jvm.ch10;

import java.util.HashMap;
import java.util.Map;

/**
 * Summary: 常量信息
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/5/24
 * time   : 18:18
 */
public class ConstDef {
    //常量池表项和其他TAG值对应关系
    public static final Map<Integer, String> CONSTANT_TAG_MAP = new HashMap<Integer, String>(14) {{
        put(1, "Uft8");
        put(3, "Integer");
        put(4, "Float");
        put(5, "Long");
        put(7, "Class");
        put(6, "Double");
        put(8, "String");
        put(9, "FieldRef");
        put(10, "MethodRef");
        put(11, "InterfaceMethodRef");
        put(12, "NameAndType");
        put(15, "MethodHandle");
        put(16, "MethodType");
        put(18, "InvokeDynamic");
    }};

    // Class Access Flag标记位和含义
    public static final Map<Integer, String> CLASS_ACCESS_FLAG_MAP = new HashMap<Integer, String>(8) {{
        put(0x0001, "ACC_PUBLIC");
        put(0x0010, "ACC_FINAL");
        put(0x0020, "ACC_SUPER");
        put(0x0200, "ACC_INTERFACE");
        put(0x0400, "ACC_ABSTRACT");
        put(0x1000, "ACC_SYNTHETIC");
        put(0x2000, "ACC_ANNOTATION");
        put(0x4000, "ACC_ENUM");
    }};

    public static final Map<Integer, String> FIELD_ACCESS_FLAG_MAP = new HashMap<Integer, String>(8) {{
        put(0x0001, "ACC_PUBLIC");
        put(0x0002, "ACC_PRIVATE");
        put(0x0004, "ACC_PROTECTED");
        put(0x0008, "ACC_STATIC");
        put(0x0010, "ACC_FINAL");
        put(0x0040, "ACC_VOLATILE");
        put(0x1000, "ACC_SYNTHETIC");
        put(0x4000, "ACC_ENUM");
    }};

    public static final Map<Integer, String> METHOD_ACCESS_FLAG_MAP = new HashMap<Integer, String>(8) {{
        put(0x0001, "ACC_PUBLIC");
        put(0x0002, "ACC_PRIVATE");
        put(0x0004, "ACC_PROTECTED");
        put(0x0008, "ACC_STATIC");
        put(0x0010, "ACC_FINAL");
        put(0x0020, "ACC_SYNCHRONIZED");
        put(0x0040, "ACC_BRIDGE");
        put(0x0080, "ACC_VARARGS");
        put(0x0100, "ACC_NATIVE");
        put(0x0400, "ACC_ABSTRACT");
        put(0x0800, "ACC_STRICT");
        put(0x1000, "ACC_SYNTHETIC");
    }};
}
