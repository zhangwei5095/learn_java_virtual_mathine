package xyz.anduo.jvm.ch04;

/**
 * 打印时间线程
 * Author : anduo@meituan.com
 * Version: 1.0
 * Date   : 15/4/6
 * time   : 下午12:52
 */
public class PrintThread extends  Thread{

    //当前时间
    private static final long startTime = System.currentTimeMillis();

    @Override public void run() {
        try {
            //每100ms打印一次时间
            while (true){
                Thread.sleep(100);
                long t = System.currentTimeMillis() - startTime;
                System.out.println("time:" + t);
            }
        }catch (Exception e){
        }
    }
}
