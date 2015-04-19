package xyz.anduo.jvm.ch06;

/**
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/19
 * time   : 下午3:20
 */
public class ChildStatic extends SimpleStatic{
    static {
        number = 2;
    }

    public static void main(String[] args) {
        System.out.println(number);
    }
}
