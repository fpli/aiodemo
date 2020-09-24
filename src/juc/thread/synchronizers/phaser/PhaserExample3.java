package juc.thread.synchronizers.phaser;

import java.time.LocalTime;
import java.util.concurrent.Phaser;

public class PhaserExample3 {

    private static final Phaser phaser = new Phaser(1);//registered with one party

    public static void main(String[] args) throws InterruptedException {
        print("before running task in main method");
        startTask();
        startTask();
        startTask();
        //doing some work
        Thread.sleep(10000);
        print("deRegistering main thread");
        phaser.arriveAndDeregister();//unregistering one party
    }

    private static void startTask() throws InterruptedException {
        Thread.sleep(300);
        new Thread(PhaserExample3::taskRun).start();
    }

    private static void taskRun() {
        print("before registering");
        phaser.register();//registering more
        try {
            //doing some work
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("before arrive");
        phaser.arriveAndAwaitAdvance();//current thread will wait wait for others to arrive
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