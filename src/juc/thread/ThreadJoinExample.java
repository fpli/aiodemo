package juc.thread;

public class ThreadJoinExample {

    public static void main (String[] args) throws InterruptedException {
        Task task1 = new Task();
        Thread thread1 = new Thread(task1);
        thread1.start();
        thread1.join();//here the main thread will wait until thread1 finishes.
        System.out.println("after join");

        Task task2 = new Task();
        Thread thread2 = new Thread(task2);
        thread2.start();
        thread2.join();
        System.out.println("after thread2.join()");
    }

    private static class Task implements Runnable {

        @Override
        public void run () {
            int c = 0;
            String threadName = Thread.currentThread().getName();

            System.out.println(threadName + " started.");
            for (int i = 0; i < 1000; i++) {
                c+=i;
            }

            System.out.println(threadName + " ended.");
        }
    }
}
