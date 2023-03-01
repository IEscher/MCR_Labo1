package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class ControlFrame {
    private List<TimerSubject> timerList;
    private List<JPanel> panels;
    private JFrame frame;

    public ControlFrame(String title, int timerAmount) throws HeadlessException {
        frame = new JFrame(title);
        this.timerList = new LinkedList<>();
        for (int i = 0; i < timerAmount; i++) {
            TimerSubject timer = new TimerSubject();
            timerList.add(timer);
            frame.add(createLinePanel(timerList.get(i)));
        }
    }

    public void show() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 200));
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel createLinePanel(TimerSubject ts) {
        JPanel p = new JPanel();

        p.add(new JTextArea(ts.getName()));

        JButton b0 = new JButton("Démarrer");
        JButton b1 = new JButton("Arrêter");
        JButton b2 = new JButton("Réinitialiser");
        JButton b3 = new JButton("Cadran romain");
        JButton b4 = new JButton("Cadran arabe");
        JButton b5 = new JButton("Numérique");

        p.add(b0);
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        p.add(b5);

        p.setLayout(new FlowLayout(FlowLayout.RIGHT));
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                TimerFrame tf = new TimerFrame(ts);
                tf.show();
            }
        });

        return p;
    }
}
