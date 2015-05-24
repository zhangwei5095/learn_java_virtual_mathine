package xyz.anduo.jvm.ch06;

/**
 * 对于<clinit>函数的调用，也就是类的初始化，虚拟机在内部确保其多线程环境中的安全性，
 * 也就是说，当多个线程试图初始化同一个类时，只有一个线程可以进入<clinit>函数，其他线程必须
 * 等待，如果之前的线程成功加载了类，则等待队列中的线程就没有机会再执行了。
 * <p/>
 * 因为函数<clinit>是带锁线程安全的，因此，在多线程环境下进行类初始化的时候，可能会引起死锁，
 * 并且这种死锁是很难发现的，因为看起来他们并没有可用的锁信息。
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/19
 * time   : 下午3:27
 */
public class StaticDealLockMain extends Thread {
    private char flag;

    public StaticDealLockMain(char flag) {
        this.flag = flag;
        this.setName("Thread-" + flag);
    }

    @Override
    public void run() {
        try {
            Class.forName("xyz.anduo.jvm.ch06.Static" + flag);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(getName() + " over !");
    }

    public static void main(String[] args) {
        StaticDealLockMain loadA = new StaticDealLockMain('A');
        loadA.start();
        StaticDealLockMain loadB = new StaticDealLockMain('B');
        loadB.start();
    }
}

class StaticA {
    static {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("xyz.anduo.jvm.ch06.StaticB");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("StaticA init OK");
    }
}

class StaticB {
    static {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("xyz.anduo.jvm.ch06.StaticA");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("StaticB init OK");
    }
}




