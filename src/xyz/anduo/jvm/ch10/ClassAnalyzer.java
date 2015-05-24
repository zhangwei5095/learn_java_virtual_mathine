// Copyright (C) 2015 anduo
// All rights reserved
package xyz.anduo.jvm.ch10;

import xyz.anduo.jvm.ch10.struct.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static xyz.anduo.jvm.ch10.ConstDef.*;

/**
 * Summary: class文件反编译解析
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/5/24
 * time   : 09:29
 */
public class ClassAnalyzer {

    public static void main(String[] args) throws IOException {
        //1、读取类文件的字节数据流，创建一个字节数组
        final String classFilePath = "/Users/anduo/Projects/jvm/out/production/jvm/xyz/anduo/jvm/ch10/Student.class";
        DataInputStream inputStream = null;
        inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(classFilePath)));
        analyze(inputStream);
    }

    //用于存储各个类型参数的map<index,Info>
    private static Map<Integer, Utf8Info> utf8InfoMap = new HashMap<Integer, Utf8Info>();
    private static Map<Integer, IntegerInfo> integerInfoMap = new HashMap<Integer, IntegerInfo>();
    private static Map<Integer, FloatInfo> floatInfoMap = new HashMap<Integer, FloatInfo>();
    private static Map<Integer, LongInfo> longInfoMap = new HashMap<>();
    private static Map<Integer, DoubleInfo> doubleInfoMap = new HashMap<>();
    private static Map<Integer, ClassInfo> classInfoMap = new HashMap<>();
    private static Map<Integer, StringInfo> stringInfoMap = new HashMap<>();
    private static Map<Integer, FieldRefInfo> fieldRefInfoMap = new HashMap<>();
    private static Map<Integer, MethodRefInfo> methodRefInfoMap = new HashMap<>();
    private static Map<Integer, InterfaceMethodRefInfo> interfaceMethodRefInfoMap = new HashMap<>();
    private static Map<Integer, NameAndTypeInfo> nameAndTypeInfoMap = new HashMap<>();
    private static Map<Integer, MethodHandleInfo> methodHandleInfoMap = new HashMap<>();
    private static Map<Integer, MethodTypeInfo> methodTypeInfoMap = new HashMap<>();
    private static Map<Integer, InvokeDynamicInfo> invokeDynamicInfoMap = new HashMap<>();


    private static void analyze(DataInputStream inputStream) throws IOException {
        //1、读取幻数
        //magic number，必须是0xCAFEBABE，用于快速识别是否是一个class文件
        int magic = u4(inputStream);
        if (magic == 0xCAFEBABE) {
            System.out.println("magic number = 0xCAFEBABE --- 标准class文件");
        } else {
            throw new RuntimeException("无效的幻数");
        }
        System.out.println("magic = " + magic);

        //2、读取副、主版本号
        //version，包括major和minor，假如版本号超过了JVM的识别范围，JVM将拒尽执行
        int minorVersion = u2(inputStream);
        int majorVersion = u2(inputStream);
        System.out.println("version = " + majorVersion + "." + minorVersion);

        //3 常量池
        //3.1、 读取常量池表中表项的个数
        short const_pool_count = u2(inputStream);
        System.out.println("constant pool size = " + const_pool_count);

        //3.2、 读取每个常量
        for (int i = 1; i < const_pool_count; ++i) {
            analyzeConstant(inputStream, i); // 分析常数
        }

        //4. 读取Class的声明中使用的修饰符掩码并解析
        // access flag，定义类的访问权限
        short access_flags = u2(inputStream);
        System.out.print("access_flags = " + access_flags);
        String access_flags_16 = "0x" + Integer.toHexString(access_flags);
        String access_tmp = "";
        Set<Integer> accessKeySet = CLASS_ACCESS_FLAG_MAP.keySet();
        for (int key : accessKeySet) {
            if ((access_flags & key) == key) {
                if (!"".equals(access_tmp)) {
                    access_tmp += " ";
                }
                access_tmp += CLASS_ACCESS_FLAG_MAP.get(key);
            }
        }
        System.out.println(" [" + access_tmp + " ]");
        System.out.println("Access flags: " + access_flags_16 + " [" + access_tmp + " ]");

        //5 当前类、父类和接口
        //u2 this_class_index
        //u2 super_class_index
        //u2 interfaces_count
        //u2 interfaces[interface_count]
        short this_class_index = u2(inputStream);
        short super_class_index = u2(inputStream);

        //super class name
        int superClassNameUtf8Index = classInfoMap.get((int) super_class_index).getIndex();
        String superClassName = new String(utf8InfoMap.get(superClassNameUtf8Index).getBytes());
        System.out.println("SuperClassName : " + superClassName);

        // read interfaces count:
        short interfaces_count = u2(inputStream);
        if (interfaces_count > 0) {
            System.out.println("------interfaces------");
        }
        // read each interfaces:
        for (int i = 1; i <= interfaces_count; i++) {
            short interface_index = u2(inputStream);
            short interfaceNameIndex = classInfoMap.get((int) interface_index).getIndex();
            String interfaceName = new String(utf8InfoMap.get((int) interfaceNameIndex).getBytes());
            System.out.println("interface class name : " + interfaceName);
        }

        //6 Class文件的字段
        //u2 fields_count
        //field_info fields[fields_count]
        // field_info struct
        // u2 access_flags
        // u2 name_index
        // u2 descriptor_index
        // u2 attributes_count;
        // attribute_info attributes[attributes_count]
        //  attribute_info struct
        //  u2 attribute_name_index
        //  u4 attribute_length
        //  u2 constantvalue_index
        short fields_count = u2(inputStream);
        System.out.println("------fields------");
        for (int i = 0; i < fields_count; i++) {
            short field_access_flags = u2(inputStream);
            short field_name_index = u2(inputStream);
            short field_descriptor_index = u2(inputStream);
            short field_attributes_count = u2(inputStream);
            for (int j = 0; j < field_attributes_count; j++) {
                short attribute_name_index = u2(inputStream);
                int attribute_length = u4(inputStream);
                short constantvalue_index = u2(inputStream);
            }

            String field_access_name = "";
            Set<Integer> fieldAccessSet = FIELD_ACCESS_FLAG_MAP.keySet();
            for (Integer fieldAccessFlag : fieldAccessSet) {
                if ((field_access_flags & fieldAccessFlag) == fieldAccessFlag) {
                    if (!"".equals(field_access_name)) {
                        field_access_name += " ";
                    }
                    field_access_name += FIELD_ACCESS_FLAG_MAP.get(fieldAccessFlag);
                }
            }
            String field_name = new String(utf8InfoMap.get((int) field_name_index).getBytes());
            String field_descriptor = new String(utf8InfoMap.get((int) field_descriptor_index).getBytes());
            System.out.println(field_access_name + " " + field_descriptor + " " + field_name);
        }

        //7 类的方法信息
        //u2 methods_count
        //method_info methods[methods_count]
        // method_info struct
        // u2 access_flags
        // u2 name_index
        // u2 descriptor_index
        // u2 attributes_count
        // attributes_info attributes[attributes_count]
        //   attribute_info struct
        //   u2 attribute_name_index
        //   u2 attribute_length
        //   u1 info[attribute_length]
        short method_count = u2(inputStream);
        System.out.println("------methods------");
        for (short i = 0; i < method_count; i++) {
            short method_access_flags = u2(inputStream);
            short method_name_index = u2(inputStream);
            short method_descriptor_index = u2(inputStream);

            String methodAccess = "";
            Set<Integer> methodAccessMap = METHOD_ACCESS_FLAG_MAP.keySet();
            for (Integer methodAccessFlag : methodAccessMap) {
                if ((method_access_flags & methodAccessFlag) == methodAccessFlag) {
                    if ("".equals(methodAccess)) {
                        methodAccess += " ";
                    }
                    methodAccess += METHOD_ACCESS_FLAG_MAP.get(methodAccessFlag);
                }
            }

            String methodName = new String(utf8InfoMap.get((int) method_name_index).getBytes());
            String methodDescripor = new String(utf8InfoMap.get((int) method_descriptor_index).getBytes());
            System.out.println(methodAccess + " " + methodName + " " + methodDescripor);

            short method_atrributes_count = u2(inputStream);
            for (int j = 0; j < method_atrributes_count; j++) {
                short method_attr_name_index = u2(inputStream);
                int method_attr_length = u4(inputStream);
                byte[] attributes_info = bytes(inputStream, method_attr_length);
            }
        }

    }





    private static void analyzeConstant(DataInputStream inputStream, int index) throws IOException {
        // 用于读：
        // byte n8;
        short n16;
        int n32;
        long n64;
        float f;
        double d;
        byte[] buffer;
        byte tag = inputStream.readByte(); // 读取数据类型标签
        System.out.println("\n常量索引 = " + index + ", 数据类型标签 = " + CONSTANT_TAG_MAP.get((int) tag));
        switch (tag) {
        case 1://utf-8 string;
            n16 = u2(inputStream);
            System.out.println(" length = " + n16);
            buffer = new byte[n16];
            inputStream.readFully(buffer); // 数组读满才返回
            System.out.println(" value = " + new String(buffer));
            Utf8Info info = new Utf8Info(tag, n16, buffer);
            utf8InfoMap.put(index, info);
            break;
        case 3: // integer
            n32 = u4(inputStream);
            System.out.println(" value = " + n32);
            IntegerInfo integerInfo = new IntegerInfo(tag, n32);
            integerInfoMap.put(index, integerInfo);
            break;
        case 4: // float
            f = u4(inputStream);// input.readFloat();
            System.out.println(" value = " + f);
            FloatInfo floatInfo = new FloatInfo(tag, f);
            floatInfoMap.put(index, floatInfo);
            break;
        case 5: // long
            n64 = u8(inputStream);
            System.out.println(" value = " + n64);
            LongInfo longInfo = new LongInfo(tag, n64);
            longInfoMap.put(index, longInfo);
            break;
        case 6: // double
            d = u8(inputStream);
            System.out.println(" value = " + d);
            DoubleInfo doubleInfo = new DoubleInfo(tag, d);
            doubleInfoMap.put(index, doubleInfo);
            break;
        case 7: // class or interface reference
            n16 = u2(inputStream);
            System.out.println(" index = " + n16 + " （在哪里可以找到类名）");
            ClassInfo classInfo = new ClassInfo(tag, n16);
            classInfoMap.put(index, classInfo);
            break;
        case 8: // string
            n16 = u2(inputStream);
            System.out.println(" string_index = " + n16);
            StringInfo stringInfo = new StringInfo(tag, n16);
            stringInfoMap.put(index, stringInfo);
            break;
        case 9: // field reference
            FieldRefInfo fieldRefInfo = new FieldRefInfo(tag);
            n16 = u2(inputStream);
            fieldRefInfo.setClassIndex(n16);
            System.out.println(" class_index = " + n16);
            n16 = u2(inputStream);
            fieldRefInfo.setNameAndTypeIndex(n16);
            System.out.println(" name_and_type_index = " + n16);
            fieldRefInfoMap.put(index, fieldRefInfo);
            break;
        case 10: // method reference
            MethodRefInfo methodRefInfo = new MethodRefInfo(tag);
            n16 = u2(inputStream);
            methodRefInfo.setClassIndex(n16);
            System.out.println(" class_index = " + n16 + " （在哪里可以找到类）");
            n16 = u2(inputStream);
            methodRefInfo.setNameAndTypeIndex(n16);
            System.out.println(" name_and_type_index = " + n16 + " （在哪里可以找到名称和类型）");
            methodRefInfoMap.put(index, methodRefInfo);
            break;
        case 11: // interface method reference
            InterfaceMethodRefInfo interfaceMethodRefInfo = new InterfaceMethodRefInfo(tag);
            n16 = u2(inputStream);
            interfaceMethodRefInfo.setClassIndex(n16);
            System.out.println(" class_index = " + n16 + " （在哪里可以找到接口）");
            n16 = u2(inputStream);
            interfaceMethodRefInfo.setNameAndTypeIndex(n16);
            System.out.println(" name_and_type_index = " + n16);
            interfaceMethodRefInfoMap.put(index, interfaceMethodRefInfo);
            break;
        case 12: // name and type reference
            NameAndTypeInfo nameAndTypeInfo = new NameAndTypeInfo(tag);
            n16 = u2(inputStream);
            nameAndTypeInfo.setNameIndex(n16);
            System.out.println(" name_index = " + n16);
            n16 = u2(inputStream);
            nameAndTypeInfo.setDescriptorIndex(n16);
            System.out.println(" descriptor_index = " + n16);
            nameAndTypeInfoMap.put(index, nameAndTypeInfo);
            break;
        case 15:// method handle
            MethodHandleInfo methodHandleInfo = new MethodHandleInfo(tag);
            byte reference_kind = inputStream.readByte();
            methodHandleInfo.setReferenceKind(reference_kind);
            System.out.println(" reference_kind = " + reference_kind);
            n16 = u2(inputStream);
            methodHandleInfo.setReferenceIndex(n16);
            System.out.println(" reference_index = " + n16);
            methodHandleInfoMap.put(index, methodHandleInfo);
            break;
        case 16:// method type
            n16 = u2(inputStream);
            MethodTypeInfo methodTypeInfo = new MethodTypeInfo(tag, n16);
            System.out.println(" descriptor_index = " + n16);
            methodTypeInfoMap.put(index, methodTypeInfo);
            break;
        case 18://InvokeDynamicInfo
            InvokeDynamicInfo invokeDynamicInfo = new InvokeDynamicInfo(tag);
            n16 = u2(inputStream);
            invokeDynamicInfo.setBootstrapMethodAttrIndex(n16);
            System.out.println(" bootstrap_method_attrIndex = " + n16);
            n16 = u2(inputStream);
            invokeDynamicInfo.setNameAndTypeIndex(n16);
            System.out.println(" name_and_type_index = " + n16);
            invokeDynamicInfoMap.put(index, invokeDynamicInfo);
        default:
            throw new RuntimeException("Invalid constant pool flag: " + tag);
        }
    }

    /////////////BEGIN 从DataInputStream中读取自定类型的值///////////////////////////
    private static byte u1(DataInputStream inputStream) throws IOException {
        return inputStream.readByte();
    }

    private static short u2(DataInputStream inputStream) throws IOException {
        return inputStream.readShort();
    }

    private static int u4(DataInputStream inputStream) throws IOException {
        return inputStream.readInt();
    }

    public static long u8(DataInputStream inputStream) throws IOException {
        return inputStream.readLong();
    }

    public static byte[] bytes(DataInputStream inputStream, int length) throws IOException {
        byte[] bytes = new byte[length];
        inputStream.readFully(bytes);
        return bytes;
    }
    /////////////EDN 从DataInputStream中读取自定类型的值///////////////////////////

}

