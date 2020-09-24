package juc.atomic;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DeleteWithNoAtomicMarkableRefExample {

    private static class DataInfo {
        private BigDecimal data = BigDecimal.TEN;
        private boolean deleted = false;

        void delete() {
            //after physically deleting from backend mark it deleted
            data = null;
            deleted = true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            DataInfo dataInfo = new DataInfo();
            ExecutorService es = Executors.newFixedThreadPool(2);
            //deleter
            es.execute(() -> {
                dataInfo.delete();
            });
            //reader
            es.execute(() -> {
                boolean deleted = dataInfo.deleted;
                BigDecimal data = dataInfo.data;
                if (!deleted && data == null) {//if 'not deleted' then data should not be null
                    System.out.printf("deleted: %s DataInfo.data=%s%n", deleted, data);
                }
            });
            es.shutdown();
            es.awaitTermination(10, TimeUnit.MINUTES);
        }
        System.out.println("finished");
    }
}
