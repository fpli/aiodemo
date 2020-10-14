package juc.completion;

import java.util.concurrent.CompletableFuture;

/**
 * handle() vs whenComplete()
 * The above methods, accept BiConsumer, whereas CompletionStage.handle(....) methods accept BiFunction.
 * That means handle() methods are allowed to return a result (in case of exception a recovering result) thus they can handle the exception.
 * On the other hand, whenComplete() methods cannot return a results. So they are used as merely callbacks that do not interfere in the processing pipeline of CompletionStages.
 *
 * If there's an unhandled exception coming from the stages before 'whenComplete' stage then that exception is passed through as it is.
 * In other words if the upstream CompletionStage completes exceptionally, the CompletionStage returned by whenComplete() also completes exceptionally.
 */
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
