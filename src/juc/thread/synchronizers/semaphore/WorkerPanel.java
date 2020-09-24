package juc.thread.synchronizers.semaphore;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class WorkerPanel extends JComponent {
    private static final int PANEL_SIZE = 60;
    private int angle = 0;
    private boolean active;

    public WorkerPanel() {
        setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
    }

    public void work() {
        active = true;
        int cycle = ThreadLocalRandom.current().nextInt(100, 200);
        for (int i = 0; i < cycle; i++) {
            angle += 5;
            if (angle >= 360) {
                angle = 0;
            }
            repaint();
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
            }
        }
        active = false;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(active ? Color.BLUE : Color.LIGHT_GRAY);
        g.drawArc(0, 0, PANEL_SIZE, PANEL_SIZE, 0, 360);
        g.fillArc(0, 0, PANEL_SIZE, PANEL_SIZE, angle, 30);
    }
}

