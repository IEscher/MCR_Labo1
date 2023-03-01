package org.example;

import javax.swing.*;
import java.awt.*;

public class TimerFrame extends TimerObserver {

    private JFrame frame;

    TimerFrame(Subject subject) {
        super(subject);
        frame = new JFrame();
    }

    public void show() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(200, 200));
        frame.pack();
        frame.setVisible(true);
    }
}
