package lock;

import java.util.concurrent.CountDownLatch;

/**
 * 下自习，班主任关门
 *  倒计数锁存器, 计数为0, 解锁阻塞线程
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <=6 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t 上完自习, 离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t 班主任最后关门");
    }
}
