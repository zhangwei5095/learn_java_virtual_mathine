package xyz.anduo.jvm.ch02;

/**
 * 堆上分配
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/6
 * time   : 下午2:55
 */
public class OnStackTest {
    public static void alloc(){
        byte[] bytes = new byte[2];
        bytes[0] = 1;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
    }
}
