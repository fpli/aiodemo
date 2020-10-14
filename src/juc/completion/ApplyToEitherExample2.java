package juc.completion;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

/**
 * In above example both 'this' and 'other' stages executed simultaneously but only one result was used.
 * If any of them is slower to compute then that one is canceled. Let's put delay in one of them to confirm that:
 */
public class ApplyToEitherExample2 {

    public static void main(String[] args) {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
            int i = ThreadLocalRandom.current().nextInt(1, 10);
            System.out.println("value to be return from 'this' completable: " + i);
            ThreadSleep(100);// this stage will be canceled, because it is slower than other stage.
            System.out.println("it will not to execute, mind it.");
            return i;
        });
        CompletableFuture<Double> resultantCf = cf.applyToEither(getOtherCompletable(), a -> {
            System.out.println("Selected value: " + a);
            return Math.sqrt(a);
        });
        Double d = resultantCf.join();
        System.out.println(d);
    }

    private static CompletableFuture<Integer> getOtherCompletable() {
        return CompletableFuture.supplyAsync(() -> {
            int i = ThreadLocalRandom.current().nextInt(1, 10);
            System.out.println("value to be return from 'other' completable: " + i);
            return i;
        });
    }

    private static void ThreadSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
