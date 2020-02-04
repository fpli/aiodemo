package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁等价于while + cas
 * 此时原子引用的对象是线程
 * 自旋锁是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，这样的好处是减少线程上下文的切换，缺点是循环会消耗CPU
 */
public class SpinLockDemo {
    // 原子引用线程
    AtomicReference<Thread> threadAtomicReference = new AtomicReference<>();

    // 加锁
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t come in ...");
        while (!threadAtomicReference.compareAndSet(null, thread)){

        }
    }
    // 释放锁
    public void myUnLock(){
        Thread thread = Thread.currentThread();
        threadAtomicReference.compareAndSet(thread, null);
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.myLock();
            // 线程做自己的任务
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            spinLockDemo.myUnLock();
        }, "AA").start();

        new Thread(()->{
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            spinLockDemo.myUnLock();
        }, "BB").start();
    }
}
