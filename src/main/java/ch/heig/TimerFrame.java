package ch.heig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TimerFrame {
    private final JFrame frame;
    private final int BASE_SIZE = 200;
    private final List<TimerObserver> observers;
    private final List<LayoutManager> layouts;
    private LayoutManager activeLayout;

    /**
     * Instructions de constructions communes à tous les constructeurs.
     */
    TimerFrame() {
        observers = new LinkedList<>();
        frame = new JFrame();
        layouts = new ArrayList<>();
        layouts.add(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        layouts.add(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
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

        this.showMultiple(observers);
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

        this.showMultiple(observers);
    }

    public void reDraw() {
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Fonction contenant les paramètres de bases à appliquer à toutes les fenêtres.
     */
    public void show() {
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(layouts.get(0));
        activeLayout = layouts.get(0);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Fonction appliquant les paramètres spécifiques à la fenêtre affichant tous les timers en même temps.
     *
     * @param observers Liste des sujets concernés par la fenêtre.
     */
    public void showMultiple(List<TimerObserver> observers) {
        final int COMPONENTS_SIZE = BASE_SIZE * observers.size();
        final int MARGIN = 50;
        final Dimension HOR_DEFAULT = new Dimension(COMPONENTS_SIZE + MARGIN, BASE_SIZE + MARGIN);
        final Dimension VER_DEFAULT = new Dimension(BASE_SIZE + MARGIN, COMPONENTS_SIZE + MARGIN);

        class ResizeListener extends ComponentAdapter {
            public void componentResized(ComponentEvent e) {
                Dimension d = frame.getSize();
                System.out.println(d);

                /*
                // Si on est en horizontal
                if (activeLayout.equals(layouts.get(0)) && (d.getWidth() < COMPONENTS_SIZE)) {
                    activeLayout = layouts.get(1);
                    frame.setLayout(activeLayout);
                    frame.setMaximumSize(HOR_DEFAULT);
                    frame.setSize(VER_DEFAULT);
                } else if (activeLayout.equals(layouts.get(1)) && (d.getHeight() < COMPONENTS_SIZE)) {
                    activeLayout = layouts.get(0);
                    frame.setLayout(activeLayout);
                    frame.setMaximumSize(VER_DEFAULT);
                    frame.setSize(HOR_DEFAULT);
                } */


                if (d.getHeight() >= COMPONENTS_SIZE && d.getWidth() >= COMPONENTS_SIZE) {
                    frame.setMinimumSize(new Dimension(BASE_SIZE + MARGIN, BASE_SIZE + MARGIN));
                } else if (d.getHeight() >= COMPONENTS_SIZE && d.getWidth() < COMPONENTS_SIZE) {
                    frame.setMinimumSize(new Dimension(BASE_SIZE + MARGIN, COMPONENTS_SIZE + MARGIN));
                } else {
                    frame.setMinimumSize(new Dimension(COMPONENTS_SIZE + MARGIN, BASE_SIZE + MARGIN));
                }

                if ((d.getHeight() >= COMPONENTS_SIZE) && (d.getWidth() < COMPONENTS_SIZE)) {
                    frame.setLayout(layouts.get(1));
                } else if (d.getWidth() >= COMPONENTS_SIZE && (d.getHeight() < COMPONENTS_SIZE)) {
                    frame.setLayout(layouts.get(0));
                }
                
            }
        }

        frame.setPreferredSize(HOR_DEFAULT);
        frame.setMaximumSize(HOR_DEFAULT);
        frame.setMinimumSize(new Dimension(BASE_SIZE + MARGIN,BASE_SIZE + MARGIN));
        frame.addComponentListener(new ResizeListener());
        //frame.setMinimumSize(new Dimension(COMPONENTS_SIZE, MIDDLE_DISTANCE));

        show();
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
                g.drawString(to.getName(), middleDistance - 30, middleDistance);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(3));

                // draw the hour hand
                g2d.setColor(hourColor);
                g2d.rotate(Math.toRadians(to.getHours() * 30), middleDistance, middleDistance);
                g2d.drawLine(middleDistance, middleDistance, middleDistance, middleDistance - 40);
                // draw the minute hand
                g2d.setColor(minuteColor);
                g2d.rotate(Math.toRadians(to.getMinutes() * 6), middleDistance, middleDistance);
                g2d.drawLine(middleDistance, middleDistance, middleDistance, middleDistance - 60);
                // draw the second hand
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
     *
     * @param to Observeur à ajouter
     */
    private void addTimerToFrame(TimerObserver to) {
        class DisplayNumeric extends JPanel {
            @Override
            protected void paintComponent(Graphics g) {
                String timeString = String.format("%s: %02dh %02dm %02ds",
                        to.getName(),
                        to.getHours(),
                        to.getMinutes(),
                        to.getSeconds());

                // Get the FontMetrics
                FontMetrics metrics = g.getFontMetrics(g.getFont());
                // Determine the X coordinate for the text
                int x = (BASE_SIZE - metrics.stringWidth(timeString)) / 2;
                // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
                int y = ((BASE_SIZE - metrics.getHeight()) / 2) + metrics.getAscent();
                // Draw the String
                g.drawString(timeString, x, y);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(BASE_SIZE, BASE_SIZE);
            }
        }

        JPanel p = new JPanel();
        p.add(new DisplayNumeric());
        frame.add(p);
    }
}
