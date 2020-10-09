package juc.completion;

import java.util.concurrent.CompletableFuture;

public class RunExample {

    public static void main(String[] args) {

        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            System.out.println("running, in thread: " + Thread.currentThread().getName());
        });
        cf.join();//waits until task is completed

        System.out.println("main exiting, thread: " + Thread.currentThread().getName());
    }
}
