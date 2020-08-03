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
            System.out.println(Thread.currentThread().getName() + "\t线程持有锁:" + lockA + "\t 尝试获取:" + lockB );
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + "\t线程持有锁:" + lockB);
            }
        }
    }
}

/**
 * 死锁 代码产生
 * 死锁定位分析
 * jps -l  : 查找java进程id
 * jstack pid : 根据进程id看详情
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
