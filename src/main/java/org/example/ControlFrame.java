package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class ControlFrame {
    private List<TimerSubject> timerList;
    private JFrame frame;

    public ControlFrame(String title, int timerAmount) throws HeadlessException {
        frame = new JFrame(title);
        this.timerList = new LinkedList<>();
        for (int i = 0; i < timerAmount; i++) {
            TimerSubject timer = new TimerSubject();
            timerList.add(timer);
        }
    }

    public void show() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 200));
        frame.pack();
        frame.setVisible(true);
    }
}
