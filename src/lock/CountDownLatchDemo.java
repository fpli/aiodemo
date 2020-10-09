package lock;

import java.util.concurrent.CountDownLatch;

/**
 *  mock up a shutdown light after students leave classroom
 *  CountDownLatch the count down to zero, then the blocking thread go on
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t after class and leave classroom");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();// block the caller thread, till count threads down to zero, then the caller thread go on
        System.out.println(Thread.currentThread().getName() + "\t the master of class shutdown the light");
    }
}
