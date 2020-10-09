package lock;

import java.util.concurrent.*;

public class MyThreadPoolExecutor {

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(0, 5, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 200; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\tdoing business" + (1 << 13));
                System.out.println("current thread do more things");
            });
        }
        executorService.shutdown();
    }
}
