package juc.threadlocal;

public class MyThreadLocal {

    private static final ThreadLocal<MyObject> threadLocal = new ThreadLocal<>();

    public static MyObject createMyObject(String name) {
        MyObject myObject = new MyObject(name);
        threadLocal.set(myObject);
        return myObject;
    }

    public static MyObject getMyObject() {
        return threadLocal.get();
    }
}
