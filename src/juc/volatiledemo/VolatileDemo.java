package juc.volatiledemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {

    volatile int number = 0;

    public void addTo60() {
        this.number = 60;
    }

    // 请注意，此时number前面是加了volatile关键字修饰的，volatile不保证原子性
    public void addPlusPlus() {
        number++;// ++/-- 不是原子操作 1 获取number 2 执行number+1 3 赋值number
    }


    AtomicInteger atomicInteger = new AtomicInteger(0);

    public void addAtomic() {
        atomicInteger.getAndIncrement();
    }
}

/**
 * volatile 保证可见性，不保证原子性，禁止指令重排, 是一种轻量级的同步解决方案，乞丐版
 * 1 验证volatile的可见性 ，可见性是一种及时通知机制
 * 1.1 假如int number = 0; number变量之前根本没有添加volatile关键字修饰,没有可见性
 * 1.2 添加了volatile，可以解决可见性问题。
 *
 * 2 验证volatile不保证原子性
 * 2.1 原子性指的是什么意思？
 * 不可分割，完整性，也即某个线程正在做某个业务时，中间不可以加塞或被分割。需要整体完整，要么同时成功，要么同时失败。
 *
 * 2.2 volatile不保证原子性的代码验证
 *
 * 2.3 why
 *
 * 2.4 如何解决volatile不保证原子性
 * 使用synchronized 可以 属于高射炮打蚊子
 * solution:使用原子类
 */
public class VolatileDemo {

    public static void main(String[] args) {
        //seeOkByVolatile();
        MyData myData = new MyData();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            }, String.valueOf(i)).start();
        }

        // 需要等待上面20个线程全部执行完，再用main线程取得最终的结果看是多少？
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t finally number value:" + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t finally atomicInteger value:" + myData.atomicInteger.get());
    }

    // volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
    private static void seeOkByVolatile() {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                // 线程暂停3 s
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t update number value:" + myData.number);
        }, "AAA").start();

        while (myData.number == 0) {
            // main 线程就一直在这里等待循环，直到number值不再等于零。
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over, main get number value:" + myData.number);
    }
}