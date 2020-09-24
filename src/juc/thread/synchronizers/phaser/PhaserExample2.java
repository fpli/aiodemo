package juc.thread.synchronizers.phaser;

import java.time.LocalTime;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicBoolean;

public class PhaserExample2 {

    private static final Phaser phaser = new Phaser();
    private static AtomicBoolean unRegisteredFlag = new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {
        startTask(0);
        startTask(1000);
        startTask(2000);
    }

    private static void startTask(long initialDelay) throws InterruptedException {
        Thread.sleep(initialDelay);
        new Thread(PhaserExample2::taskRun).start();
    }

    private static void taskRun() {
        phaser.register();//registering this thread
        print("after registering");
        for (int i = 1; i <= 2; i++) {
            try {
                //doing some work
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print("before arrive " + i);
            phaser.arriveAndAwaitAdvance();//current thread will wait for others to arrive
            print("after arrive " + i);
            if(!unRegisteredFlag.get()){
                unRegisteredFlag.set(true);
                print("UnRegistering");
                phaser.arriveAndDeregister();// unregistering the party
                break;
            }
        }
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