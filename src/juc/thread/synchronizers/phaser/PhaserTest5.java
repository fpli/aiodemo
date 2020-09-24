package juc.thread.synchronizers.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserTest5 {

    //
    private static final int TASKS_PER_PHASER = 4;

    public static void main(String[] args) {
        //
        final int phaseToTerminate = 3;

        final Phaser parent = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("====== " + phase + " ======");
                return phase == phaseToTerminate || registeredParties == 0;
            }
        };

        //
        final Task[] tasks = new Task[10];
        build(tasks, 0, tasks.length, parent);
        for (int i = 0; i < tasks.length; i++) {
            System.out.println("starting thread, id: " + i);
            final Thread thread = new Thread(tasks[i]);
            thread.start();
        }
    }

    public static void build(Task[] tasks, int low, int high, Phaser parent) {
        if (high - low > TASKS_PER_PHASER) {
            for (int i = low; i < high; i += TASKS_PER_PHASER) {
                int j = Math.min(i + TASKS_PER_PHASER, high);
                build(tasks, i, j, new Phaser(parent));
            }
        } else {
            for (int i = low; i < high; ++i) {
                tasks[i] = new Task(i, parent);
            }
        }
    }

    public static class Task implements Runnable {
        //
        private final int id;
        private final Phaser phaser;

        public Task(int id, Phaser phaser) {
            this.id = id;
            this.phaser = phaser;
            this.phaser.register();
        }

        @Override
        public void run() {
            while (!phaser.isTerminated()) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    // NOP
                }
                System.out.println("in Task.run(), phase: " + phaser.getPhase()    + ", id: " + this.id);
                phaser.arriveAndAwaitAdvance();
            }
        }
    }

}
