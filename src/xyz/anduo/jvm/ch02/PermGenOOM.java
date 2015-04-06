package xyz.anduo.jvm.ch02;

import java.io.File;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * PermGen溢出
 * 分析:
 * 首先必须保证在jdk1.8以下版本，因为在1.8中已经将PermGen移除，改为Metaspace（元空间）
 * 关于元空间参照http://caoyaojun1988-163-com.iteye.com/blog/1969853
 * Perm即JDK中的永久去，也对应JDK中的方法区
 * 方法区主要保存的时装载的类信息（类型常量、字段、方法信息、方法字节码）
 * 运行时的常量池就是保存在方法区中的，溢出有可能是运行时常量池溢出；
 * 也有可能是方法区中保存的class对象没有被及时回收掉或者class信息占用的内存超过了我们设置的大小。
 * 当永久带语出的时候会抛出：java.lang.OutOfMemoryError:PermGen Space
 * 基本思路：对于这些区域的测试，基本思路是运行时产生大量的类去填满方法区，直到溢出
 * VM Args:
 * jdk1.7 :-XX:PermSize5M -XX:MaxPermSize5m -verbose -verbose:gc
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/6
 * time   : 下午2:58
 */
public class PermGenOOM {
    private static final String classPath = "~/Doucment/workspace/jvm/out/production/jvm/xyz/anduo/jvm/ch01/DigitsBits.class";
    private static final String className = "xyz.anduo.jvm.ch01.DigitsBits";

    public static void main(String[] args) {
        try {
            final URL url = new File(classPath).toURL();
            URL[] urls = { url };
            // 获得有关类型加载的JMX接口
            ClassLoadingMXBean loadingMXBean = ManagementFactory
                    .getClassLoadingMXBean();
            List<ClassLoader> classLoaders = new ArrayList<ClassLoader>();
            while (true) {
                //加载类型并缓存类加载器实例
                ClassLoader classLoader = new URLClassLoader(urls);
                classLoaders.add(classLoader);//缓存到本地
                classLoader.loadClass(className);
                //显示数量信息（共加载的类型数目，当前有效的类型数目，已经被卸载的类型数目）
                System.out.printf("total:%d /t active:%d /t unloaded:%d \n",
                        loadingMXBean.getTotalLoadedClassCount(),
                        loadingMXBean.getLoadedClassCount(),
                        loadingMXBean.getUnloadedClassCount());
                //Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
