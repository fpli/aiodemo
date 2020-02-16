package lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1 CAS是什么 ？ ===> compareAndSet
 *      比较并交换
 *
 * 2 CAS底层原理:自旋锁 + Unsafe 类
 *
 *  2.1 Unsafe
 *   Unsafe是CAS的核心类，由于Java方法无法直接访问底层操作系统，需要通过本地方法来访问，Unsafe相当于一个后门，基于该类可以直接操作特定内存的数据。
 *   Unsafe内部方法操作可以像C的指针一样直接操作内存，因为Java中CAS操作的执行依赖与Unsafe类的方法。
 *   注意Unsafe类中的所有方法都是native修饰的，也就说Unsafe类中的方法都直接调用操作系统底层资源执行相应任务
 *
 *  2.2 变量valueOffset，表示该变量在内存中的偏移地址，因为Unsafe就是根据内存偏移地址获取数据的。
 *
 *  2.3 变量value用volatile修饰，保证了多线程之间的内存可见性。
 *
 *
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(4);
        atomicInteger.compareAndSet(4, 2020);
        atomicInteger.getAndIncrement();
    }
}
