// Copyright (C) 2015 meituan
// All rights reserved
package xyz.anduo.jvm.ch07;

/**
 * Summary: 4个线程，相互死锁(A等待B，B等待C，C等待D，D等待A)，用户分析堆栈
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/26
 * time   : 17:57
 */
public class WaitThreadDeadlock {
    // create 4 lock for 4 threads
    public static Object lock1 = new Object();
    public static Object lock2 = new Object();
    public static Object lock3 = new Object();
    public static Object lock4 = new Object();

    public static void main(String[] args) {
        Thread ta = new ThreadA();
        Thread tb = new ThreadB();
        Thread tc = new ThreadC();
        Thread td = new ThreadD();
        ta.start();
        tb.start();
        tc.start();
        td.start();
    }

    private static class ThreadA extends Thread{
        public ThreadA() {
            this.setName("ThreadA");
        }

        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println("Thread A : Holding lock a...");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {}
                System.out.println("Thread A : Waiting for lock a...");
                synchronized (lock2) {
                    System.out.println("Thread A : Holding lock a & b...");
                }
            }
        }
    }

    private static class ThreadB extends Thread{
        public ThreadB() {
            this.setName("ThreadB");
        }

        @Override
        public void run() {
            synchronized (lock2) {
                System.out.println("Thread B : Holding lock b...");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {}
                System.out.println("Thread B : Waiting for lock c...");
                synchronized (lock3) {
                    System.out.println("Thread B : Holding lock b & c...");
                }
            }
        }
    }

    private static class ThreadC extends Thread{
        public ThreadC() {
            this.setName("ThreadC");
        }

        @Override
        public void run() {
            synchronized (lock3) {
                System.out.println("Thread C : Holding lock c...");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {}
                System.out.println("Thread C : Waiting for lock d...");
                synchronized (lock4) {
                    System.out.println("Thread C : Holding lock c & d...");
                }
            }
        }
    }

    private static class ThreadD extends Thread{
        public ThreadD() {
            this.setName("ThreadD");
        }

        @Override
        public void run() {
            synchronized (lock4) {
                System.out.println("Thread D : Holding lock d...");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {}
                System.out.println("Thread D : Waiting for lock d...");
                synchronized (lock1) {
                    System.out.println("Thread D : Holding lock d & a...");
                }
            }
        }
    }

}
