package juc.atomic;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class DeleteAtomicMarkableRefExample {

    private static class DataInfo {
        BigDecimal bd = BigDecimal.TEN;
        //obj reference with delete flag (initially false)
        AtomicMarkableReference<BigDecimal> numDeleteRef = new AtomicMarkableReference<>(bd, false);

        void delete() {
            //after physically deleting from backend mark it deleted
            numDeleteRef.compareAndSet(bd, null, false, true);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {//repeat various times to see thread overlapping effects
            DataInfo dataInfo = new DataInfo();
            ExecutorService es = Executors.newFixedThreadPool(2);
            //deleter
            es.execute(() -> {
                dataInfo.delete();
            });
            es.execute(() -> {
                boolean[] marked = new boolean[1];
                BigDecimal data = dataInfo.numDeleteRef.get(marked);
                if (!marked[0] && data == null) {//if 'not deleted' then data should not be null
                    System.out.printf("deleted: %s data=%s%n", marked[0], data);
                }
            });
            es.shutdown();
            es.awaitTermination(10, TimeUnit.MINUTES);
        }
        System.out.println("finished");
    }
}

