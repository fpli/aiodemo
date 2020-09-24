package juc.thread.synchronizers.phaser;

import java.util.concurrent.Phaser;

public class PhaserTest2 {

    public static void main(String args[]) throws Exception {
        //
        final Phaser phaser = new Phaser(1);
        for (int i = 0; i < 5; i++) {
            phaser.register();
            System.out.println("starting thread, id: " + i);
            final Thread thread = new Thread(new Task(i, phaser));
            thread.start();
        }

        //
        System.out.println("Press ENTER to continue");
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //reader.readLine();
        phaser.arriveAndDeregister();// no block
        System.out.println(Thread.currentThread().getName() + "--->>>");
    }

    public static class Task implements Runnable {
        //
        private final int id;
        private final Phaser phaser;

        public Task(int id, Phaser phaser) {
            this.id = id;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();// arrive and block current thread
            System.out.println("in Task.run(), phase: " + phaser.getPhase() + ", id: " + this.id);
        }
    }
}
