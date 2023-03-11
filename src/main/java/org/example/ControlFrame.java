package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ControlFrame {
    private final ArrayList<String> captions = new ArrayList<>(Arrays.asList(
            "Démarrer",
            "Arrêter",
            "Réinitialiser",
            "Cadran romain",
            "Cadran arabe",
            "Numérique"
    ));

    //private List<TimerSubject> timerList;
    private JFrame frame;

    public ControlFrame(String title, int timerAmount) throws HeadlessException {
        frame = new JFrame(title);
        //this.timerList = new LinkedList<>();
        for (int i = 0; i < timerAmount; i++) {
            TimerSubject timer = new TimerSubject();
            //timerList.add(timer);
            frame.add(createLinePanel(timer));
        }
        frame.setLayout(new FlowLayout(FlowLayout.RIGHT));
    }

    public void show() {
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 200));
        frame.setVisible(true);
    }

    private JPanel createLinePanel(TimerSubject ts) {
        JPanel p = new JPanel();

        p.add(new JLabel(ts.getName()));

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (captions.indexOf(ae.getActionCommand())) {
                    case 0 -> ts.start();
                    case 1 -> ts.stop();
                    case 2 -> ts.reset();
                    case 3 -> new TimerFrame(ts,
                            "cadran_chiffres_romains.jpg",
                            Color.BLACK, Color.GRAY, Color.YELLOW);
                    case 4 -> new TimerFrame(ts,
                            "cadran_chiffres_arabes.jpg",
                            Color.BLACK, Color.BLUE, Color.RED);
                    case 5 -> new TimerFrame(ts);
                }
            }
        };

        for (String s : captions) {
            JButton b = new JButton(s);
            p.add(b);
            b.addActionListener(al);
        }

        p.setLayout(new FlowLayout(FlowLayout.RIGHT));

        return p;
    }
}
