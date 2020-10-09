package juc.completion;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Chaining CompletionStage(s) with supplyAsync()
 * Since CompletableFuture.supplyAsync() returns CompletableFuture<T>,
 * only those methods defined in CompletionStage can be chained which require a function with argument of type T and optionally produce a result (of any type) :
 *      <U> CompletionStage<U> thenApply(Function<? super T, ? extends U> fn)
 *      <U> CompletionStage<U> thenApplyAsync(Function<? super T,? extends U> fn)
 *      <U> CompletionStage<U> thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
 *
 *          CompletionStage<Void> thenAccept(Consumer<? super T> action)
 *          CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action)
 *          CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor)
 */
public class SupplyExample2 {

    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> ThreadLocalRandom.current().nextInt(1, 10))
                .thenApply(Math::sqrt)
                .thenAccept(System.out::println)
                .join();
    }
}
