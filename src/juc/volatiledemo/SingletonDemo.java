package juc.volatiledemo;

public class SingletonDemo {

    private static volatile SingletonDemo singleton = null;

    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName()+ "\t构造方法执行");
    }

    // DCL(Double Check Lock双端检锁机制)
    public static SingletonDemo getSingleton() {
        if (null == singleton){
            synchronized (SingletonDemo.class){
                if (null == singleton){
                    singleton = new SingletonDemo();
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                SingletonDemo.getSingleton();
            }, String.valueOf(i)).start();
        }
    }
}
