package juc.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NoAtomicReferenceExample {

    private static Double sum;

    public static void main(String[] args) throws InterruptedException {
        for (int k = 0; k < 5; k++) {
            sum=0d;
            ExecutorService es = Executors.newFixedThreadPool(50);
            for (int i = 1; i <= 50; i++) {
                int finalI = i;
                es.execute(() -> {
                    sum+=Math.pow(1.5, finalI);
                });
            }
            es.shutdown();
            es.awaitTermination(10, TimeUnit.MINUTES);
            System.out.println(sum);
        }
    }
}
