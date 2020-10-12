package juc.completion;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class ThenComposeExample {

    public static void main(String[] args) {
        CompletableFuture<Integer> rndNumCompletableFuture = CompletableFuture .supplyAsync(() -> {
            int x = ThreadLocalRandom.current().nextInt(1, 5);
            System.out.println("Main stage generated number: " + x);
            return x;
        });

        CompletableFuture<BigDecimal> composedCompletableFuture = rndNumCompletableFuture.thenCompose(ThenComposeExample::getPowerCompletableFuture);
        BigDecimal power = composedCompletableFuture.join();
        System.out.println("power: " + power);
    }

    //the other function to be used as method reference.
    private static CompletableFuture<BigDecimal> getPowerCompletableFuture(Integer x) {
        CompletableFuture<BigDecimal> completableFuture = CompletableFuture.supplyAsync(() -> {
            int y = ThreadLocalRandom.current().nextInt(5, 10);
            System.out.println("other stage generated number: " + y);
            return new BigDecimal(y).pow(x);
        });
        return completableFuture;
    }
}
