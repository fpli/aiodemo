package lock;

import java.util.concurrent.TimeUnit;

class DeadLockShareResource implements Runnable{

    private final String lockA;
    private final String lockB;

    public DeadLockShareResource(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() + "\thold:" + lockA + "\t try to hold:" + lockB );
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + "\thold:" + lockB);
            }
        }
    }
}

/**
 * deadlock demo
 * analyze deadlock
 * jps -l  : find a java's id
 * jstack pid : view details of given pid
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        DeadLockShareResource deadLockShareResourceAB = new DeadLockShareResource(lockA, lockB);
        DeadLockShareResource deadLockShareResourceBA = new DeadLockShareResource(lockB, lockA);
        new Thread(deadLockShareResourceAB, "AAA" ).start();
        new Thread(deadLockShareResourceBA, "BBB" ).start();
    }
}
