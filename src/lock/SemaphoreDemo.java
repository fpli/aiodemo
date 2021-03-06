package lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *  three carports, six cars
 *  Semaphore
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);// three carports

        for (int i = 1; i <= 6 ; i++) {// six cars
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t got a carport");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "\t stop 3 minutes, and leaves");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
