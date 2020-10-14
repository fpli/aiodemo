package juc.completion;

import java.util.concurrent.CompletableFuture;

/**
 * CompletionStage.runAfterBoth() methods
 * These methods simply use a Runnable (instead of a BiFunction or BiConsumer) which is executed after both stages complete normally.
 */
public class RunAfterBothExample {

    public static void main(String[] args) {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(
                () -> System.out.println("In first stage"));
        CompletableFuture<Void> otherCf = CompletableFuture.runAsync(
                () -> System.out.println("In second stage"));
        cf.runAfterBoth(otherCf, () -> System.out.println("after both stages"));
        cf.join();
    }
}
