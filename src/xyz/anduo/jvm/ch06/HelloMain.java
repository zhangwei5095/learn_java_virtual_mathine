package xyz.anduo.jvm.ch06;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/19
 * time   : 下午12:09
 */
public class HelloMain {
    public static void main(String[] args) {
        while (true){
            try {
                MyClassLoader classLoader = new MyClassLoader("/Users/anduo/WorkSpace/jvm/out/production/jvm");
                Class clazz = classLoader.loadClass("xyz.anduo.jvm.ch06.Worker");
                Object work = clazz.newInstance();
                Method method = work.getClass().getMethod("doit",new Class[]{});
                method.invoke(work, new Object[]{});
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("not find class");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e1) {
                }
            }
        }
    }
}

