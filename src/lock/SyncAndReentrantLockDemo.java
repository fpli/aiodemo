package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 问题: 线程1执行5次，线程2阻塞，线程3阻塞，接着 线程1阻塞 线程2执行10次，线程3阻塞， 再接着线程2阻塞，线程3执行15次，线程1阻塞，。。 线程3阻塞， 来10轮
 * 锁绑定多个条件Condition
 * A 线程打印5次 B线程打印10次 C线程打印15次 每次只限制一个线程执行
 * A线程唤醒B线程, B线程唤醒C线程， C线程唤醒A线程
 * 精确通知指定线程
 */
class ShareResource{

    private int number = 1;//标识位  1 : A 线程  2 : B线程  3 : C 线程

    private final Lock lock = new ReentrantLock();
    private final Condition condition1 = lock.newCondition();
    private final Condition condition2 = lock.newCondition();
    private final Condition condition3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try {
            // 1 判断 使用while判断，不使用if , if不能防止虚假唤醒
            while (number != 1){
                condition1.await();
            }

            // 2 干活
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3 通知
            number = 2;
            condition2.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try {
            // 1 判断
            while (number != 2){
                condition2.await();
            }

            // 2 干活
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3 通知
            number = 3;
            condition3.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try {
            // 1 判断
            while (number != 3){
                condition3.await();
            }

            // 2 干活
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3 通知
            number = 1;
            condition1.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {

        ShareResource shareResource = new ShareResource();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResource.print5();
            }
        }, "AA").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResource.print10();
            }
        }, "BB").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResource.print15();
            }
        }, "CC").start();
    }
}
