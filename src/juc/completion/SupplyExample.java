package juc.completion;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Following methods of CompletableFuture can be used to start tasks which return a result
 *      public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier);
 *      public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
 */
public class SupplyExample {

    public static void main(String[] args) {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> ThreadLocalRandom.current().nextInt(1, 10));
        Integer integer = cf.join();//similar to cf.get()
        System.out.println(integer);
    }
}
