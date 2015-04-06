package xyz.anduo.jvm.ch04;

import java.util.HashMap;
import java.util.Map;

/**
 * 消耗内存线程
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/6
 * time   : 下午12:55
 */
public class ConSumeThread extends Thread {
    Map<Long,byte[]> map = new HashMap<Long, byte[]>();
    @Override public void run() {
        try{
            while (true){
                if (map.size()*512/1024/1024>=450){
                   //大于450M时，清空内存
                    System.err.println("===before clean map===");
                    System.err.println("size of map is :" + map.size());
                    map.clear();
                }
                for (int i = 0; i < 1024; i++) {
                    map.put(System.nanoTime(),new byte[512]);
                }
                Thread.sleep(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
