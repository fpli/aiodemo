package juc.thread.synchronizers.countdownlatch;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    private static final int LATCH_COUNT = 4;

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(LATCH_COUNT);
        JFrame frame = createFrame();
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));

        for (int i = 1; i <= LATCH_COUNT; i++) {
            ProgressThread progressThread = new ProgressThread(latch, i * 10);
            frame.add(progressThread.getProgressComponent());
            progressThread.start();
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JFrame createFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("CountDownLatch Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(ProgressThread.PROGRESS_WIDTH, 170));
        return frame;
    }
}
