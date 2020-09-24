package juc.threadlocal;

public class MyObject {

    private String name;

    public MyObject(String name) {
        this.name = name;
    }

    public void showName() {
        System.out.printf("MyObject name: %s, thread: %s%n", name, Thread.currentThread().getName());
    }
}
