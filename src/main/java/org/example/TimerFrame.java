package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class TimerFrame extends TimerObserver {

    private final JFrame frame;
    private final int BASE_SIZE = 200;

    TimerFrame(TimerSubject subject) {
        super(subject);
        frame = new JFrame();
        this.show();
    }

    TimerFrame(List<TimerSubject> subjects) {
        super(subjects);
        frame = new JFrame();
        this.show();
    }

    TimerFrame(TimerSubject subject, String fileName, Color hourColor, Color minuteColor, Color secondColor) {
        super(subject);
        frame = new JFrame();
        addTimerToFrame(associatedSubject, fileName, hourColor, minuteColor, secondColor);
        this.show();
    }

    TimerFrame(List<TimerSubject> subjects, String fileName, Color hourColor, Color minuteColor, Color secondColor) {
        super(subjects);
        frame = new JFrame();
        for(TimerSubject s : subjects) {
            addTimerToFrame(s, fileName, hourColor, minuteColor, secondColor);
        }
        class ResizeListener extends ComponentAdapter {
            public void componentResized(ComponentEvent e) {
                Dimension d = frame.getSize();
                final int COMPONENTS_SIZE = BASE_SIZE * subjects.size();
                if (d.getHeight() >= COMPONENTS_SIZE + 50 && d.getWidth() >= COMPONENTS_SIZE + 50) {
                    frame.setMinimumSize(new Dimension(BASE_SIZE + 50, BASE_SIZE + 50));
                } else if (d.getHeight() >= COMPONENTS_SIZE + 50 && d.getWidth() < COMPONENTS_SIZE + 50) {
                    frame.setMinimumSize(new Dimension(BASE_SIZE + 50, COMPONENTS_SIZE + 50));
                } else {
                    frame.setMinimumSize(new Dimension(COMPONENTS_SIZE + 50, BASE_SIZE + 50));
                }

                if ((d.getHeight() >=COMPONENTS_SIZE + 50) && (d.getWidth() < COMPONENTS_SIZE + 50)) {
                    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
                } else if (d.getWidth() >= COMPONENTS_SIZE + 50 &&(d.getHeight() < COMPONENTS_SIZE + 50)) {
                    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
                }
            }
        }

        frame.addComponentListener(new ResizeListener());
        frame.setPreferredSize(new Dimension(BASE_SIZE * subjects.size() + 50,BASE_SIZE + 50));
        frame.setMinimumSize(new Dimension(BASE_SIZE * subjects.size() + 50, BASE_SIZE + 50));
        this.show();
    }

    public void show() {
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addTimerToFrame(TimerSubject ts, String fileName, Color hourColor, Color minuteColor, Color secondColor) {
        Image image = Toolkit.getDefaultToolkit().getImage(fileName)
                .getScaledInstance(BASE_SIZE, BASE_SIZE, Image.SCALE_SMOOTH);

        class DisplayGraphics extends JPanel {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this);
                g.drawString(ts.getName(), BASE_SIZE / 2 - 30, BASE_SIZE / 2);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(BASE_SIZE, BASE_SIZE);
            }
        }

        JPanel p = new JPanel();
        p.add(new DisplayGraphics());
        frame.add(p);
    }
}
