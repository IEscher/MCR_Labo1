// Authors: Fazlija Ylli, Escher Ian

package ch.heig;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class TimerFrame {
    private final JFrame frame;
    private static final int BASE_SIZE = 200;
    private static final int MARGIN = 20;
    private final List<TimerObserver> observers;
    private final List<JLabel> timerLabels;

    TimerFrame() {
        observers = new LinkedList<>();
        timerLabels = new LinkedList<>();
        frame = new JFrame();
        frame.setSize(new Dimension(BASE_SIZE + MARGIN, BASE_SIZE + MARGIN));
    }

    /**
     * Constructor used to display a single digital timer.
     *
     * @param subject Subject to observe
     */
    TimerFrame(TimerSubject subject) {
        this();
        observers.add(new TimerObserver(subject, this));
        addTimerToFrame(observers.get(0));
        this.show();
    }

    /**
     * Constructor used to display multiple digital timers
     *
     * @param subjects List of subjects to observe
     */
    TimerFrame(List<TimerSubject> subjects) {
        this();
        for (TimerSubject s : subjects) {
            observers.add(new TimerObserver(s, this));
        }

        for (TimerObserver o : observers) {
            addTimerToFrame(o);
        }

        this.show();
    }

    /**
     * Constructor used to display a single graphical timer.
     *
     * @param subject     Subject to observe
     * @param fileName    Path to the file to use as background
     * @param hourColor   Color of the hour hand
     * @param minuteColor Color of the minute hand
     * @param secondColor Color of the second hand
     */
    TimerFrame(TimerSubject subject, String fileName, Color hourColor, Color minuteColor, Color secondColor) {
        this();
        observers.add(new TimerObserver(subject, this));
        addGraphicalTimerToFrame(observers.get(0), fileName, hourColor, minuteColor, secondColor);
        this.show();
    }

    /**
     * Constructor used to display multiple graphical timers.
     *
     * @param subjects    List of subjects to observe
     * @param fileName    Path to the file to use as background
     * @param hourColor   Color of the hour hand
     * @param minuteColor Color of the minute hand
     * @param secondColor Color of the second hand
     */
    TimerFrame(List<TimerSubject> subjects, String fileName, Color hourColor, Color minuteColor, Color secondColor) {
        this();

        for (TimerSubject s : subjects) {
            observers.add(new TimerObserver(s, this));
        }

        for (TimerObserver o : observers) {
            addGraphicalTimerToFrame(o, fileName, hourColor, minuteColor, secondColor);
        }

        this.show();
    }

    /**
     * Redraw the frame with the new value.
     */
    public void reDraw() {
        // Redraw the graphical parts
        frame.revalidate();
        frame.repaint();

        // Update the labels
        if(!timerLabels.isEmpty()) {
            for(int i = 0 ; i < observers.size() ; ++i) {
                timerLabels.get(i).setText(getFormattedTime(observers.get(i)));
            }
        }
    }

    /**
     * Function containing the basic parameters to apply to all windows.
     */
    public void show() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        frame.pack();
        frame.revalidate();
        frame.setMinimumSize(new Dimension(BASE_SIZE+MARGIN, frame.getHeight()));
        frame.setVisible(true);
    }

    /**
     * Generates a JPanel with an image and hands and adds it to the frame.
     *
     * @param to          Observer to add
     * @param fileName    Path to the file to use as background
     * @param hourColor   Color of the hour hand
     * @param minuteColor Color of the minute hand
     * @param secondColor Color of the second hand
     */
    private void addGraphicalTimerToFrame(TimerObserver to, String fileName, Color hourColor, Color minuteColor, Color secondColor) {
        Image image = Toolkit.getDefaultToolkit().getImage(fileName)
                .getScaledInstance(BASE_SIZE, BASE_SIZE, Image.SCALE_SMOOTH);

        class DisplayGraphics extends JPanel {
            @Override
            protected void paintComponent(Graphics g) {
                final int middleDistance = BASE_SIZE / 2;
                g.drawImage(image, 0, 0, this);
                g.drawString(to.getName(), middleDistance - 30, middleDistance + 15);
                Graphics2D g2d = (Graphics2D) g;

                // draw the hour hand
                g2d.setStroke(new BasicStroke(3));
                g2d.setColor(hourColor);
                g2d.rotate(Math.toRadians((to.getHours() % 24) * 30.0), middleDistance, middleDistance);
                g2d.drawLine(middleDistance, middleDistance, middleDistance, middleDistance - 40);

                // draw the minute hand
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(minuteColor);
                g2d.rotate(Math.toRadians(to.getMinutes() * 6.0), middleDistance, middleDistance);
                g2d.drawLine(middleDistance, middleDistance, middleDistance, middleDistance - 60);

                // draw the second hand
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(secondColor);
                g2d.rotate(Math.toRadians(to.getSeconds() * 6.0), middleDistance, middleDistance);
                g2d.drawLine(middleDistance, middleDistance, middleDistance, middleDistance - 80);
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


    /**
     * Generates a JPanel for digital display and adds it to the frame.
     *
     * @param to Observer to add
     */
    private void addTimerToFrame(TimerObserver to) {
        JPanel p = new JPanel();
        JLabel label = new JLabel(getFormattedTime(to));
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setPreferredSize(new Dimension(BASE_SIZE, BASE_SIZE));
        p.add(Box.createHorizontalGlue());
        p.add(label);
        timerLabels.add(label);
        p.add(Box.createHorizontalGlue());
        frame.add(p);
    }

    /**
     * Return the formatted time as a String
     *
     * @param to Observer that will give its data
     * @return Formatted time as String
     */
    private String getFormattedTime(TimerObserver to) {
        return String.format("%s: %02dh %02dm %02ds",
                to.getName(),
                to.getHours(),
                to.getMinutes(),
                to.getSeconds());
    }
}
