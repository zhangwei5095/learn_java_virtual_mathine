package xyz.anduo.jvm.ch06;

/**
 * java编译器并不会为所有的类都产生<clinit>初始化函数，若果一个类既没有赋值语句，
 * 也没有static语句块，那么生成的<clinit>函数就应该为空，因此，编译器就不会为该
 * 类插入<clinit>函数
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/19
 * time   : 下午3:22
 */
public class StaticFinalClass {
    public static final int i = 1;
    public static final int j = 2;
}
