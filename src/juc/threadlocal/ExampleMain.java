package juc.threadlocal;

import java.util.Arrays;

public class ExampleMain {

    public static void main(String[] args) {

        String[] names = {"one", "two", "three"};

        Arrays.stream(names).forEach(ExampleMain::createThread);
    }

    private static void createThread(final String name) {
        new Thread(() -> {
            MyThreadLocal.createMyObject(name);

            for (int i = 0; i < 5; i++) {
                MyThreadLocal.getMyObject().showName();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
