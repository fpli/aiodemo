package lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/***
 * CyclicBarrier
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, ()->{
            System.out.println("call master");
        });

        for (int i = 1; i <= 7 ; i++) {
            final int tempInt = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\tcollect " + tempInt+ " item");
                try {
                    cyclicBarrier.await(); // block current thread, till parties threads arrive the barrier, then go on
                    System.out.println(Thread.currentThread().getName() + "go on");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
