package juc.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Supplier;

public class NoAtomicStampedRefExample {

    private static class Test {
        private static final int STATE_UNINITIALIZED = -1;
        private static final int STATE_INITIALIZING_IN_PROGRESS = 0;
        private static final int STATE_INITIALIZED = 1;
        private Supplier<String> dataService = getDataService();
        private int state = STATE_UNINITIALIZED;
        private String data;

        public void init() {
            if (state == STATE_UNINITIALIZED) {
                state = STATE_INITIALIZING_IN_PROGRESS;
                data = dataService.get();
                state = STATE_INITIALIZED;
            }
        }

        private static Supplier<String> getDataService() {
            return () -> {
                //sleep a little to simulate the service
                LockSupport.parkNanos(1000);
                return "test string";
            };
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 50; i++) {//repeat process multiple times
            Test test = new Test();
            ExecutorService es = Executors.newFixedThreadPool(2);

            //reader thread
            es.execute(() -> {
                while (true) {
                    int state = test.state;
                    boolean dataNotNull = test.data != null;
                    //print unexpected
                    if (state != Test.STATE_INITIALIZED && dataNotNull) {
                        System.out.printf("state: %s, data: %s%n", state, test.data);
                    }
                    //break on expected initialization
                    if (state == Test.STATE_INITIALIZED && dataNotNull) {
                        break;
                    }
                    LockSupport.parkNanos(1);
                }
            });
            //writer thread
            es.execute(test::init);

            es.shutdown();
            es.awaitTermination(10, TimeUnit.MINUTES);
        }
        System.out.println("finished");
    }
}

