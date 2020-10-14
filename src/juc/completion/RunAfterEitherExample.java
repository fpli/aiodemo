package juc.completion;

import java.util.concurrent.CompletableFuture;

public class RunAfterEitherExample {

    public static void main(String[] args) {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            System.out.println("Running 'this' completable");
        });
        CompletableFuture<Void> resultantCf = cf.runAfterEither(getOtherCompletable(), () -> {
            System.out.println("Running after either of the two");
        });
        resultantCf.join();
    }

    private static CompletableFuture<Void> getOtherCompletable() {
        return CompletableFuture.runAsync(() -> {
            ThreadSleep(15);// it will be canceled because it is slower than another stage.
            System.out.println("Running other completable");
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
