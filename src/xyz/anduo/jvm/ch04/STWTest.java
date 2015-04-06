package xyz.anduo.jvm.ch04;

/**
 * Stop The World 现象的测试
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/6
 * time   : 下午12:51
 */
public class STWTest {
    // -Xmx521M -Xms512M -XX:UseSerialGC -Xloggc:gc.log
    // -XX:+PrintGCDetails -Xmn1m
    // -XX:PretenureSizeThreshold=50
    // -XX:MaxTenuringThreshold=1
    public static void main(String[] args) throws InterruptedException {
        Thread printThread = new PrintThread();
        Thread conSumeThread = new ConSumeThread();
        printThread.start();
        conSumeThread.start();
        Thread.currentThread().join();
    }
}
