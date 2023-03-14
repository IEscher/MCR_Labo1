package ch.heig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ControlFrame {
    private static final ArrayList<String> captions = new ArrayList<>(Arrays.asList(
            "Démarrer",
            "Arrêter",
            "Réinitialiser",
            "Cadran romain",
            "Cadran arabe",
            "Numérique"
    ));

    private final List<TimerSubject> timerList;
    private final JFrame frame;

    /**
     * Constructor
     *
     * @param title       Title of the frame
     * @param timerAmount Number of timers to create
     */
    public ControlFrame(String title, int timerAmount) {
        frame = new JFrame(title);
        frame.setResizable(false);

        this.timerList = new LinkedList<>();

        for (int i = 0; i < timerAmount; i++) {
            TimerSubject timer = new TimerSubject();
            timerList.add(timer);
            frame.add(createLinePanel(timer));
        }

        // Buttons for all timers
        JPanel p = new JPanel();
        p.add(new JLabel("Tous les chronos"));
        for (int i = 3 ; i < 6 ; ++i) {
            JButton b = new JButton(captions.get(i));
            ActionListener al = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    switch (captions.indexOf(ae.getActionCommand())) {
                        case 3 -> new TimerFrame(timerList,
                                "cadran_chiffres_romains.jpg",
                                Color.BLACK, Color.GRAY, Color.YELLOW);
                        case 4 -> new TimerFrame(timerList,
                                "cadran_chiffres_arabes.jpg",
                                Color.BLACK, Color.BLUE, Color.RED);
                        case 5 -> new TimerFrame(timerList);
                        default -> System.err.println("Unknown action");
                    }
                }
            };
            b.addActionListener(al);
            p.add(b);
        }

        frame.add(p);
        frame.setLayout(new GridLayout(timerAmount+1, 0));
    }

    /**
     * Display the frame
     */
    public void show() {
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Create a panel with a label and 3 buttons
     *
     * @param ts TimerSubject to observe
     * @return JPanel with the label and the buttons
     */
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
                    default -> System.err.println("Unknown action");
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
