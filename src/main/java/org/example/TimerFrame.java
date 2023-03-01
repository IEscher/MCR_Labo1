package org.example;

import javax.swing.*;
import java.awt.*;

public class TimerFrame extends TimerObserver {

    private JFrame frame;
    private static final int SIZE = 200;

    TimerFrame(TimerSubject subject, String fileName, Color hourColor, Color minuteColor, Color secondColor) {
        super(subject);
        frame = new JFrame();
        Image image = Toolkit.getDefaultToolkit().getImage(fileName);

        class DisplayGraphics extends JPanel{
            @Override
            protected void paintComponent(Graphics g) {
                image.getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH);
                g.drawImage(image, 0, 0, null);
                g.drawString(associatedSubject.getName(), SIZE / 2 - 35, SIZE / 2);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(SIZE, SIZE);
            }
        }

        frame.add(new DisplayGraphics());
        this.show();
    }

    TimerFrame(TimerSubject subject) {
        super(subject);
        frame = new JFrame();
        this.show();
    }

    public void show() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(SIZE, SIZE));
        frame.pack();
        frame.setVisible(true);
    }
}
