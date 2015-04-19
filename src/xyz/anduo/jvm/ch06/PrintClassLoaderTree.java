package xyz.anduo.jvm.ch06;


/**
 * 理解类加载器之间的继承关系
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/19
 * time   : 下午4:02
 */
public class PrintClassLoaderTree {
    public static void main(String[] args) {
        ClassLoader cl = PrintClassLoaderTree.class.getClassLoader();
        while (cl != null) {
            System.out.println(cl);
            cl = cl.getParent();
        }
        System.out.println(String.class.getClassLoader());
    }
}
