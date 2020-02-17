package juc.volatiledemo;

public class SingletonDemo {
    // DCL机制不一定线程安全，原因是有指令重排的存在，加入volatile可以禁止指令重排
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
                    // new SingletonDemo(); 分成3步完成 1 分配对象内存空间 2 初始化对象 3 设置intance指向刚分配的内存地址，此时instance != null
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
