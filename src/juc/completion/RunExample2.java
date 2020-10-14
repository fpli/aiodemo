package juc.completion;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Chaining CompletionStage(s) with runAsync()
 *
 *  Following methods of CompletionStage can be chained which accept runnable
 *      CompletionStage<Void> thenRun(Runnable action)
 *      CompletionStage<Void> thenRunAsync(Runnable action)
 *      CompletionStage<Void> thenRunAsync(Runnable action, Executor executor)
 */
public class RunExample2 {

    public static void main(String[] args) {
        CompletionStage<Void> cf = CompletableFuture.runAsync(() -> performTask("first stage"));

        cf = cf.thenRun(() -> performTask("second stage"));

        cf = cf.thenRunAsync(() -> performTask("third stage"));

        ((CompletableFuture) cf).join();//waits until task is completed
        System.out.println("main exiting");
    }

    private static void performTask(String stage) {
        System.out.println("---------");
        System.out.printf("stage: %s, time before task: %s, thread: %s%n", stage, LocalTime.now(), Thread.currentThread().getName());
        try {
            //simulating long task
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("stage: %s, time after task: %s, thread: %s%n", stage, LocalTime.now(), Thread.currentThread().getName());
    }
}
