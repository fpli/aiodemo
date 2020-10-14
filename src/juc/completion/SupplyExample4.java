package juc.completion;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

/**
 * The methods which return CompletionStage<Void> or CompletableFuture<Void> can only be chained with a method which takes a Runnable.
 * The methods which return CompletionStage<U> can be chained with methods which takes Consumer<U> or Function<U,R>.
 */
public class SupplyExample4 {

    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL("https://www.example.com/");
                try (InputStream is = url.openStream()) {
                    return new String(is.readAllBytes());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).thenAccept(System.out::println)
          .thenRun(() -> System.out.println("Task finished"))
          .join();
    }
}
