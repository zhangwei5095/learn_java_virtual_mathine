package xyz.anduo.jvm.ch01;

/**
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/4/6
 * time   : 下午2:49
 */
public class DigitsBits {
    public static void main(String[] args) {
        DigitsBits digitsBits = new DigitsBits();
        digitsBits.printBinary(Float.floatToIntBits(100.2f));
    }

    /**
     * 打印一个整数的二进制数
     *
     * @param digit
     */
    void printBinary(final int digit) {
        for (int i = 0; i < 32; i++) {
            int t = (digit & 0x80000000 >>> i) >>> (31 - i);
            System.out.print(t);
        }
        System.out.println();
    }
}
