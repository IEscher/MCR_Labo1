package ch.heig;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class TimerFrame {
    private final JFrame frame;
    private final int BASE_SIZE = 200;
    private final int MARGIN = 80;
    private final List<TimerObserver> observers;

    /**
     * Instructions de constructions communes à tous les constructeurs.
     */
    TimerFrame() {
        observers = new LinkedList<>();
        frame = new JFrame();
        frame.setSize(new Dimension(BASE_SIZE + MARGIN, BASE_SIZE + MARGIN));
    }

    /**
     * Constructeur utilisé pour afficher un chronomètre singulier numérique.
     *
     * @param subject Sujet à obeserver
     */
    TimerFrame(TimerSubject subject) {
        this();
        observers.add(new TimerObserver(subject, this));
        addTimerToFrame(observers.get(0));
        this.show();
    }

    /**
     * Constructeur utilisé pour afficher plusieurs chronomètres numériques
     *
     * @param subjects Liste des sujets à observer
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
     * Constructeur utilisé pour afficher un chronomètre graphique.
     *
     * @param subject     Sujet à observer
     * @param fileName    Chemin du fichier à utiliser comme arrière-plan (200x200)
     * @param hourColor   Couleur de l'aiguille des heures
     * @param minuteColor Couleur de l'aiguille des minutes
     * @param secondColor Couleurs de l'aiguille des secondes.
     */
    TimerFrame(TimerSubject subject, String fileName, Color hourColor, Color minuteColor, Color secondColor) {
        this();
        observers.add(new TimerObserver(subject, this));
        addGraphicalTimerToFrame(observers.get(0), fileName, hourColor, minuteColor, secondColor);
        this.show();
    }

    /**
     * Constructeur utilisé pour afficher plusieurs chronomètres graphique.
     *
     * @param subjects    Liste des sujets à observer
     * @param fileName    Chemin du fichier à utiliser comme arrière-plan (200x200)
     * @param hourColor   Couleur de l'aiguille des heures
     * @param minuteColor Couleur de l'aiguille des minutes
     * @param secondColor Couleurs de l'aiguille des secondes.
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

    public void reDraw() {
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Fonction contenant les paramètres de bases à appliquer à toutes les fenêtres.
     */
    public void show() {
        frame.setMinimumSize(new Dimension(BASE_SIZE + MARGIN, BASE_SIZE + MARGIN));
        frame.setPreferredSize(new Dimension(BASE_SIZE * observers.size() + MARGIN, BASE_SIZE + MARGIN));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.revalidate();
        frame.setVisible(true);
    }

    /**
     * Génère un JPanel avec une image et des aiguilles et l'ajoute à la frame.
     *
     * @param to          Observeur à ajouter
     * @param fileName    Chemin du fichier à utiliser comme arrière-plan (200x200)
     * @param hourColor   Couleur de l'aiguille des heures
     * @param minuteColor Couleur de l'aiguille des minutes
     * @param secondColor Couleurs de l'aiguille des secondes.
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
                g2d.rotate(Math.toRadians(to.getHours() * 30), middleDistance, middleDistance);
                g2d.drawLine(middleDistance, middleDistance, middleDistance, middleDistance - 40);

                // draw the minute hand
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(minuteColor);
                g2d.rotate(Math.toRadians(to.getMinutes() * 6), middleDistance, middleDistance);
                g2d.drawLine(middleDistance, middleDistance, middleDistance, middleDistance - 60);

                // draw the second hand
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(secondColor);
                g2d.rotate(Math.toRadians(to.getSeconds() * 6), middleDistance, middleDistance);
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
     * Génère un JPanel pour l'affichage numérique et l'ajoute à la frame.
     * @param to Observeur à ajouter
     */
    private void addTimerToFrame(TimerObserver to) {
        JPanel p = new JPanel();
        String timeString = String.format("%s: %02dh %02dm %02ds",
                to.getName(),
                to.getHours(),
                to.getMinutes(),
                to.getSeconds());
        JLabel label = new JLabel(timeString);
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setPreferredSize(new Dimension(BASE_SIZE + MARGIN, BASE_SIZE));
        p.add(Box.createHorizontalGlue());
        p.add(label);
        p.add(Box.createHorizontalGlue());
        frame.add(p);
    }
}
