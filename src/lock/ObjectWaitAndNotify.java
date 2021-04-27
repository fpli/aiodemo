package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ObjectWaitAndNotify {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.park();
            System.out.println("await...");
        }, "t1");

        t1.start();


        new Thread(()->{
            LockSupport.unpark(t1);
            System.out.println("signal...");
        }, "t2").start();

        Integer o1 = Integer.valueOf(1);
        Integer o2 = Integer.valueOf(1);
        System.out.println(o1 == o2);
        o1 = new Integer(1);
        o2 = new Integer(1);
        System.out.println(o1 == o2);

    }
}
