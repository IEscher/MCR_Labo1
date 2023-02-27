package org.example;

import javax.swing.*;
import java.awt.HeadlessException;
import java.util.LinkedList;
import java.util.List;

public class TimerFrame extends JFrame {
    private List<TimerSubject> timerList;


    public TimerFrame(String title, int timerAmount) throws HeadlessException {
        super(title);
        this.timerList = new LinkedList<>();
        for (int i = 0; i < timerAmount; i++) {
            TimerSubject timer = new TimerSubject();
            timerList.add(timer);
        }
    }
}
