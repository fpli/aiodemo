package juc.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者基于阻塞队列版
 * volatile/CAS/AtomicInteger/BlockingQueue/线程交互/原子引用(AtomicReference)
 */
class MyResource {

    private volatile boolean flag = true; // 默认开启,进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingQueue;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void myProduce() throws Exception {
        String data;
        boolean retValue;
        while (flag){
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue){
                System.out.println(Thread.currentThread().getName() + "\t" + "插入队列" +data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t" + "插入队列" +data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "boss 叫停了, 表示flag为false, 生产动作结束");
    }

    public void myConsumer() throws Exception{
        String result;
        while (flag){
            result = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (result == null || result.equalsIgnoreCase("")){
                flag = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过2秒没有消费到数据,线程退出");
                return;
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 消费队列" + result + "成功");
            }

        }
    }

    public void stop() {
        flag = false;
    }

}

public class ProduceConsumerBasedOnBlockingQueue {

    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");
            try {
                myResource.myProduce();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Producer").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Consumer").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("5S 时间到, boss线程叫停,活动结束");
                myResource.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "boss").start();
    }
}
