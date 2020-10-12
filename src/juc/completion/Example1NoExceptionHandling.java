package juc.completion;

import java.util.concurrent.CompletableFuture;

public class Example1NoExceptionHandling {

    public static void main(String[] args) throws Exception {
        System.out.println("-- running CompletableFuture --");
        CompletableFuture<Void> completableFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("running task");
                    return 1 / 0;
                })
                .thenApply(input -> {
                    System.out.println("multiplying by 2");
                    return input * 2;
                })
                .thenAccept(System.out::println);

        Thread.sleep(3000);//let the stages complete
        System.out.println("-- checking exceptions --");
        boolean b = completableFuture.isCompletedExceptionally();
        System.out.println("completedExceptionally: " + b);
        System.out.println("-- calling join --");
        completableFuture.join();
    }

}
