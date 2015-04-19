package xyz.anduo.jvm.ch06;

/**
 * //TODO 描述信息
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/19
 * time   : 下午4:10
 */
public class FindClassOrder {
    public static void main(String[] args) {
        HelloLoader loader = new HelloLoader();
        loader.print();
    }
}
class HelloLoader{
    public void print(){
        System.out.println("I am in Boot ClassLoader!");
    }
}
