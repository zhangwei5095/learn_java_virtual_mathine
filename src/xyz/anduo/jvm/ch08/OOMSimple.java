package xyz.anduo.jvm.ch08;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Summary: OOM的几个实例
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/5/10
 * time   : 12:09
 */
public class OOMSimple {

    // 直接内存溢出
    // 解决方法：减少堆内存，有意地出发GC
    static void directOOM(){
        for (int i = 0; i < 1024; i++) {
            ByteBuffer.allocate(1024*1024);
            System.out.println(i);
            System.gc();
        }
    }

    // 栈溢出
    static void stackOOM() {

    }

    // 方法区溢出，增大Perm区大小，允许class回收
    static void permOOM() {
        for (int i = 0; i < 100000; i++) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (object, method, args, proxy) -> proxy.invokeSuper(object, args));
            enhancer.create();
        }
    }

    // 堆溢出，
    // 解决方法：增大堆空间，及时释放内存
    static void heapOOM() {
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 1024; i++) {
            list.add(new byte[1024 * 1024]);
        }
    }

    static class OOMObject {
    }
}
