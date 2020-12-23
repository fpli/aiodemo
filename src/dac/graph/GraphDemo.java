package dac.graph;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class GraphDemo {

    public static void main(String[] args) {
        System.out.println(Math.floorMod(4, -3));
        System.out.println(-4 % 3);

        BigInteger b1 = new BigInteger("-23");
        BigInteger b2 = new BigInteger("3");
        System.out.println(b1.mod(b2));// BigInteger 取模时，除数不能是负数
        System.out.println(b1.remainder(b2));

        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 400; i++){
            System.out.println(longAdder.intValue());
            longAdder.increment();
        }

        AtomicLong atomicLong = new AtomicLong();
        for (int i = 0; i < 400; i++) {
            System.out.println(atomicLong.getAndIncrement());
        }
    }
}
