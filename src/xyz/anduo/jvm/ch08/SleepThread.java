package xyz.anduo.jvm.ch08;

/**
 * Summary: 线程栈空间溢出
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/5/10
 * time   : 12:33
 */
public class SleepThread implements Runnable {
    @Override public void run() {
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new SleepThread(), "Thread" + i).start();
            System.out.println("Thread " + i + " created");
        }
    }
}
