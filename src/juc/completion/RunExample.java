package juc.completion;

import java.util.concurrent.CompletableFuture;

/**
 * Creating and Running tasks without returning result
 */
public class RunExample {

    public static void main(String[] args) {

        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            System.out.println("running, in thread: " + Thread.currentThread().getName());
        });
        cf.join();//It waits and returns the result value when completed. the join() method does not throw a checked exception

        System.out.println("main exiting, thread: " + Thread.currentThread().getName());
    }
}
