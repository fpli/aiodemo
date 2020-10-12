package juc.completion;

import java.util.concurrent.CompletableFuture;

public class Example1WhenComplete {

    public static void main(String[] args) {
        runTasks(2);
        runTasks(0);
    }

    private static void runTasks(int i) {
        System.out.printf("-- input: %s --%n", i);
        CompletableFuture
                .supplyAsync(() -> {
                    return 16 / i;
                })
                .whenComplete((input, exception) -> {
                    if (exception != null) {
                        System.out.println("exception occurs");
                        System.err.println(exception);
                    } else {
                        System.out.println("no exception, got result: " + input);
                    }
                })
                .thenApply(input -> input * 3)
                .thenAccept(System.out::println);

    }
}
