package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * while + cas
 */
public class SpinLockDemo {
    //
    AtomicReference<Thread> threadAtomicReference = new AtomicReference<>();

    //
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t come in ...");
        while (!threadAtomicReference.compareAndSet(null, thread)){
            System.out.println("current thread set failed, continue next loop");
        }
    }
    //
    public void myUnLock(){
        Thread thread = Thread.currentThread();
        threadAtomicReference.compareAndSet(thread, null);
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.myLock();
            //
            System.out.println(Thread.currentThread().getName()+"\t do something start");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            spinLockDemo.myUnLock();
            System.out.println(Thread.currentThread().getName()+"\t do something end");
        }, "AA").start();

        new Thread(()->{
            spinLockDemo.myLock();
            System.out.println(Thread.currentThread().getName()+"\t do something start");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            spinLockDemo.myUnLock();
            System.out.println(Thread.currentThread().getName()+"\t do something end");
        }, "BB").start();

        // ps -mp 6365 -o THREAD,tid,time
        // jstack 6365|grep 18e4 -A60
       //  view java process
    }
}
