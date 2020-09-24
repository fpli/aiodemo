package juc.thread.synchronizers.phaser;

import java.util.concurrent.Phaser;

public class PhaserTest4 {

    public static void main(String args[]) throws Exception {
        //
        final int count = 5;
        final int phaseToTerminate = 3;

        final Phaser phaser = new Phaser(count) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("====== " + phase + " ======" + Thread.currentThread().getName());
                return phase == phaseToTerminate || registeredParties == 0;
            }
        };

        //
        for (int i = 0; i < count; i++) {
            System.out.println("starting thread, id: " + i);
            final Thread thread = new Thread(new Task(i, phaser));
            thread.start();
        }

        //
//        phaser.register();
//        while (!phaser.isTerminated()) {
//            System.out.println(Thread.currentThread().getName());
//            phaser.arriveAndAwaitAdvance();
//        }
        awaitPhase(phaser, 1);
        System.out.println("done");
    }

    /**
     * The main thread terminates after the end of a specific phase
     *
     * @param phaser
     * @param phase
     */
    public static void awaitPhase(Phaser phaser, int phase) {
        int p = phaser.register(); // assumes caller not already registered
        while (p < phase) {
            if (phaser.isTerminated()) {
                break; // ... deal with unexpected termination
            } else {
                System.out.println(Thread.currentThread().getName());
                p = phaser.arriveAndAwaitAdvance();
            }
        }
        phaser.arriveAndDeregister();
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
            while (!phaser.isTerminated()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // NOP
                }
                System.out.println("in Task.run(), phase: " + phaser.getPhase() + ", id: " + this.id);
                phaser.arriveAndAwaitAdvance();
            }
        }
    }
}