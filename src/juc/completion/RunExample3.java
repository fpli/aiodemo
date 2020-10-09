package juc.completion;


import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Rather than relying on default Executor we can also supply our own executor by using overloaded method runAsync(Runnable runnable, Executor executor)
 */
public class RunExample3 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletableFuture.runAsync(() -> performTask("first stage"), executor)
                .thenRun(() -> performTask("second stage"))
                .thenRunAsync(() -> performTask("third stage"), executor)
                .join();//waits until task is completed
        System.out.println("main exiting");
        executor.shutdown();
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
