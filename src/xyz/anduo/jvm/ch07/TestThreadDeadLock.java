// Copyright (C) 2015 meituan
// All rights reserved
package xyz.anduo.jvm.ch07;

/**
 * Summary: thread deadlock
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/26
 * time   : 17:31
 */
public class TestThreadDeadLock {
    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void main(String[] args) {
        ThreadDemo1 t1 = new ThreadDemo1();
        ThreadDemo2 t2 = new ThreadDemo2();
        t1.start();
        t2.start();
    }

    private static class ThreadDemo1 extends Thread {
        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println("Thread 1 : Holding lock 1...");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread 1 : Waiting for lock 2...");
                synchronized (lock2) {
                    System.out.println("Thread 1 : Holding lock 1 & 2...");
                }
            }
        }
    }

    private static class ThreadDemo2 extends Thread {
        @Override
        public void run() {
            synchronized (lock2) {
                System.out.println("Thread 2 : Holding lock 2...");
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread 2 : Waiting for lock 2...");
                synchronized (lock1) {
                    System.out.println("Thread 2 : Holding lock 1 & 2...");
                }
            }
        }
    }


}
