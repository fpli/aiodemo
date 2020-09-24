package juc.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InitWithNoAtomicMarkableRefExample {

    private static class Test {
        private boolean initialized;
        private String data;

        public void setData(String data) {
            if (!initialized) {
                this.data = data;
                initialized = true;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            Test test = new Test();
            ExecutorService es = Executors.newFixedThreadPool(2);
            //writer thread
            es.execute(() -> {test.setData("test string");});
            //reader thread
            es.execute(() -> {
                String data = test.data;
                boolean initialized = test.initialized;
                if (!initialized && data != null) {
                    System.out.println("not initialized but data: " + data);
                }
            });
            es.shutdown();
            es.awaitTermination(10, TimeUnit.MINUTES);
        }
        System.out.println("finished");
    }
}

