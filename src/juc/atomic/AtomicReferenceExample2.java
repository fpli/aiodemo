package juc.atomic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample2 {

    private static AtomicReference<BigDecimal> sum = new AtomicReference<>();

    public static void main(String[] args) throws InterruptedException {
        for (int k = 0; k < 5; k++) {
            sum.set(BigDecimal.ZERO);
            ExecutorService es = Executors.newFixedThreadPool(50);
            for (int i = 1; i <= 50; i++) {
                int finalI = i;
                es.execute(() -> {
                    sum.accumulateAndGet(new BigDecimal(1.5).pow(finalI),
                            (bg1, bg2) -> bg1.add(bg2));
                });
            }
            es.shutdown();
            es.awaitTermination(10, TimeUnit.MINUTES);
            System.out.println(sum.get().setScale(2, RoundingMode.CEILING));
        }
    }
}
