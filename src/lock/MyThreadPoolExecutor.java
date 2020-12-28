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
        // submit runnable still wait when get
        Future<?> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("gfg");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        try {
            future.get();
            System.out.println("1212");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Future<Integer> future1 = executorService.submit(() -> {
            if (ThreadLocalRandom.current().nextBoolean()){
                throw new Exception("");
            }
            return 0;
        });
        try {
            System.out.println(future1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
