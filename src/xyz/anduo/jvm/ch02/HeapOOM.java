package xyz.anduo.jvm.ch02;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anduo on 15/4/2.
 * VM Args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails
 * 使用jvisualvm 来分析dump文件
 */
public class HeapOOM {

    static class OOMObject{}

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true){
            list.add(new OOMObject());
        }
    }
}
