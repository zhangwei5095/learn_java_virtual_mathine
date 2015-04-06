package xyz.anduo.jvm.ch05;

import java.util.ArrayList;
import java.util.List;

/**
 * PigEater – 它会模仿巨蟒不停吞食大肥猪的过程。代码是通过往java.util.List中添加 32MB字节来实现这点的，每次吞食完后会睡眠100ms。
 * PigDigester – 它模拟异步消化的过程。实现消化的代码只是将猪的列表置为空。由于这是个很累的过程，因此每次清除完引用后这个线程都会睡眠2000ms。
 * 两个线程都会在一个while循环中运行，不停地吃了消化直到蛇吃饱为止。这大概得吃掉5000头猪。
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/12
 * time   : 下午8:03
 */
public class PigInThePython {
    static volatile List<byte[]> pigs = new ArrayList<byte[]>();
    static volatile int pigsEaten = 0;
    static final int ENOUGH_PIGS = 5000;

    public static void main(String[] args) {
        new PigEater().start();
        new PigDigester().start();
    }

    static class PigEater extends Thread {
        @Override
        public void run() {
            while (true) {
                pigs.add(new byte[32 * 1024 * 1024]);//32M per pig
                if (pigsEaten > ENOUGH_PIGS)
                    return;
                takeANap(1000);
            }
        }

    }

    static class PigDigester extends Thread {
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            while (true) {
                takeANap(2000);
                pigsEaten += pigs.size();
                pigs = new ArrayList<byte[]>();
                if (pigsEaten > ENOUGH_PIGS) {
                    System.out.format("Digested %d pigs in %d ms.%n", pigsEaten, System.currentTimeMillis() - start);
                    return;
                }
            }
        }
    }

    static void takeANap(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
