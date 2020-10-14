package juc.completion;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Following is another example which applies composition of three stages
 */
public class ThenComposeExample2 {

    public static void main(String[] args) {
        System.out.println("-- Main CompletableFuture: finding a random number x --");
        CompletableFuture<Integer> firstCompletableFuture = CompletableFuture
                .supplyAsync(() -> {
                    int x = ThreadLocalRandom.current().nextInt(1, 5);
                    System.out.println("x: " + x);
                    return x;
                });

        CompletableFuture<Long> composedCompletableFuture =
                firstCompletableFuture.thenComposeAsync(ThenComposeExample2::getSecondCompletableFuture);
        CompletableFuture<Double> secondComposedCompletableFuture =
                composedCompletableFuture.thenComposeAsync(ThenComposeExample2::getThirdCompletableFuture);
        Double sqrt = secondComposedCompletableFuture.join();
        System.out.println(sqrt);
    }

    private static CompletableFuture<Long> getSecondCompletableFuture(Integer x) {
        System.out.println("-- 2nd CompletableFuture: finding sum of random numbers raised to the power x --");
        return CompletableFuture
                .supplyAsync(() -> {
                    return IntStream.range(1, 5)
                            .mapToLong(i -> ThreadLocalRandom.current().nextLong(5, 10))
                            .peek(System.out::print)
                            .map(y -> (long) Math.pow(y, x))
                            .peek(z -> System.out.println(" - "+z))
                            .sum();
                });
    }

    private static CompletableFuture<Double> getThirdCompletableFuture(Long sum) {
        System.out.printf("-- 3rd CompletableFuture: finding square root of sum: %s --%n", sum);
        return CompletableFuture.supplyAsync(() -> {
            return Math.sqrt(sum);
        });
    }
}
