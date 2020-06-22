package lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 资源类
 */
class MyCache {

    private volatile Map<String, Object> map = new HashMap<>();
    //private Lock lock = new ReentrantLock();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 写操作
    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println("线程" + Thread.currentThread().getName() + "正在写入:key=" + key);
            try {
                // 暂停一会线程
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println("线程" + Thread.currentThread().getName() + "写入完成:key=" + key);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // 读操作
    public Object get(String key){
        readWriteLock.readLock().lock();
        try {
            System.out.println("线程" + Thread.currentThread().getName() + "正在读取:key=" + key);
            try {
                // 暂停一会线程
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object value = map.get(key);
            System.out.println("线程" + Thread.currentThread().getName() + "读取完成:value=" + value);
            return value;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

/**
 * 多个线程同时读取一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是
 * 如果有一个线程想去写共享资源来，就不应该再有其他线程对该资源进行读或写
 * 小总结:
 *  读-读能共存
 *  读-写不能共存
 *  写-写不能共存
 *
 *  写操作: 原子 + 独占, 整个过程必须是一个完整的统一体,中间不许被分割,不许被打断
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 1; i <= 5 ; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.put(tempInt + "", tempInt + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5 ; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }
    }
}
