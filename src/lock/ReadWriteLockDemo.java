package lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Shared resource
 */
class MyCache {

    private volatile Map<String, Object> map = new HashMap<>();
    //private Lock lock = new ReentrantLock();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // writing operation
    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println("thread" + Thread.currentThread().getName() + "is writing:key=" + key);
            try {
                // pause 0.3 second
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println("thread" + Thread.currentThread().getName() + "has written:key=" + key);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // reading operation
    public Object get(String key){
        readWriteLock.readLock().lock();
        try {
            System.out.println("thread" + Thread.currentThread().getName() + "is reading:key=" + key);
            try {
                // pause 0.3 second
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object value = map.get(key);
            System.out.println("thread" + Thread.currentThread().getName() + "has read:value=" + value);
            return value;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

/**
 * read-write operations in a coincidence
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
