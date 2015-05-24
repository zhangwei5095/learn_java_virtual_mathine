package xyz.anduo.jvm.ch05;

import sun.applet.Main;

import javax.sound.midi.Soundbank;
import java.util.Objects;

/**
 * volatitle的使用
 * volatile的作用是使CPU在读取变量值时，一定得去主存读取，不能直接从缓存读取
 * 在使用volatile关键字时要慎重，并不是只要简单类型变量使用volatile修饰，对
 * 这个变量的所有操作都是原来操作，当变量的值由自身的上一个决定时，如n=n+1、n++等，
 * volatile关键字将失效，只有当变量的值和自身上一个值无关时对该变量的操作才是原子级别的，
 * 如n = m + 1，这个就是原级别的。所以在使用volatile关键时一定要谨慎，如果自己没有把握，
 * 可以使用synchronized来代替volatile。
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/16
 * time   : 下午12:02
 */
public class VolatileTest extends Thread{

    //非volatile标志
    private static  boolean flag1 = false;
    //volatile标志
    private static volatile boolean flag2 = false;

    private int i = 0;

    @Override
    public void run() {
        Object obj = new Object();
        synchronized (obj){
            // 外围标志flag1为非volatile，该线程(t)跑起来后由另一线程(main)将flag1改为true后，
            // 如果出现情况1.flag1如果不从主存重新读取，那他将继续以false运行，所以会继续循环并
            // 进入内部的flag2的if判断；如果出现情况2.flag1从主存重新读取，那他将以true运行，
            // 所以会跳出循环，也就没有机会进入flag2的if判断了；
            while (!flag1){
                i++;
                //System.out.println("over:"+i);
                // 如果出现情况1，将进入该判断，内部标志flag2为volatile，
                // 当线程(main)将flag2改为true后，因为flag2会从主存重新读取，
                // 将以true运行，所以将跳出循环，并打印"over"语句
                if (flag2){
                    System.out.println("over:"+i);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        VolatileTest t = new VolatileTest();
        t.start();

        try {
            Thread.currentThread().sleep(2000);
            //先更改flag1
            t.flag1 = true;
            // 为了确保flag1的变更有机会被t察觉，
            // 并保证flag2能在flag1变为true后进行一次以上while(!flag1)条件判断后
            // 再判断if(flag2),sleep1秒（1秒可以跑很多循环了）
            Thread.currentThread().sleep(1000);
            //将flag2置为true，如果有机会进入if(flag2)，则将推出循环
            t.flag2 = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
