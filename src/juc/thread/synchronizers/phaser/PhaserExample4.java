package juc.thread.synchronizers.phaser;

import java.time.LocalTime;
import java.util.concurrent.Phaser;

public class PhaserExample4 {

    private static final Phaser phaser = new Phaser() {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            print(String.format("On Advance phase=%s, registered=%s", phase, registeredParties));
            return true;//super.onAdvance(phase, registeredParties);
        }
    };

    public static void main(String[] args) throws InterruptedException {
        print("before running task in main method");
        startTask();
        startTask();
        startTask();
    }

    private static void startTask() throws InterruptedException {
        Thread.sleep(300);
        new Thread(PhaserExample4::taskRun).start();
    }

    private static void taskRun() {
        print("before registering");
        phaser.register();//registering this thread
        try {
            //doing some work
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("before arrive");
        phaser.arrive();//current thread will not  wait
        print("after arrive");
    }

    private static void print(String msg) {
        System.out.printf("%-20s: %10s, t=%s, registered=%s, arrived=%s, unarrived=%s phase=%s%n",
                msg,
                Thread.currentThread().getName(),
                LocalTime.now(),
                phaser.getRegisteredParties(),
                phaser.getArrivedParties(),
                phaser.getUnarrivedParties(),
                phaser.getPhase()
        );
    }
}