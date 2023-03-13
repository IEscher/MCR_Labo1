package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class TimerFrame extends TimerObserver {
    private final JFrame frame;
    private final int BASE_SIZE = 200;

    /**
     * Constructeur utilisé pour afficher un chonromètre singulier numérique.
     *
     * @param subject Sujet à obeserver
     */
    TimerFrame(TimerSubject subject) {
        super(subject);
        frame = new JFrame();
        addTimerToFrame(subject);
        this.show();
    }

    /**
     * Constructeur utilisé pour afficher plusieurs chronomètres numériques
     *
     * @param subjects Liste des sujets à observer
     */
    TimerFrame(List<TimerSubject> subjects) {
        super(subjects);
        frame = new JFrame();
        for (TimerSubject s : subjects) {
            addTimerToFrame(s);
        }
        this.showMultiple(subjects);
    }

    /**
     * Constructeur utilisé pour afficher un choronomètre graphique.
     *
     * @param subject     Sujet à observer
     * @param fileName    Chemin du fichier à utiliser comme arrière-plan (200x200)
     * @param hourColor   Couleur de l'aiguille des heures
     * @param minuteColor Couleur de l'aiguille des minutes
     * @param secondColor Couleurs de l'aiguilles des secondes.
     */
    TimerFrame(TimerSubject subject, String fileName, Color hourColor, Color minuteColor, Color secondColor) {
        super(subject);
        frame = new JFrame();
        addGraphicalTimerToFrame(associatedSubject, fileName, hourColor, minuteColor, secondColor);
        this.show();
    }

    /**
     * Constructeur utilisé pour afficher plusieurs choronomètres graphique.
     *
     * @param subjects    Liste des sujets à observer
     * @param fileName    Chemin du fichier à utiliser comme arrière-plan (200x200)
     * @param hourColor   Couleur de l'aiguille des heures
     * @param minuteColor Couleur de l'aiguille des minutes
     * @param secondColor Couleurs de l'aiguilles des secondes.
     */
    TimerFrame(List<TimerSubject> subjects, String fileName, Color hourColor, Color minuteColor, Color secondColor) {
        super(subjects);
        frame = new JFrame();

        for (TimerSubject s : subjects) {
            addGraphicalTimerToFrame(s, fileName, hourColor, minuteColor, secondColor);
        }

        this.showMultiple(subjects);
    }

    @Override
    public void update(int time) {
        super.update(time);
//        frame.setTitle(associatedSubject.getName() + " - " + time);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Fonction contenant les paramètres de bases à appliquer à toutes les fenêtres.
     */
    public void show() {
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Fonction appliquant les paramètres spécifiques à la fenêtre affichant tous les timers en même temps.
     *
     * @param subjects Liste des sujets concernés par la fenêtre.
     */
    public void showMultiple(List<TimerSubject> subjects) {
        class ResizeListener extends ComponentAdapter {
            // TODO : Trouver une manière plus clean d'espacer les horloges. J'ai du rajouter des + 50 partout

            /* Peut-être une solution serait de mettre tous les panels dans un nouveau panel et ensuite
            gérer la taille minimum de ce panel final au lieu de la taille de la fenêtre. ¯\_(ツ)_/¯*/

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

                if ((d.getHeight() >= COMPONENTS_SIZE + 50) && (d.getWidth() < COMPONENTS_SIZE + 50)) {
                    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
                } else if (d.getWidth() >= COMPONENTS_SIZE + 50 && (d.getHeight() < COMPONENTS_SIZE + 50)) {
                    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
                }
            }
        }

        frame.addComponentListener(new ResizeListener());
        frame.setPreferredSize(new Dimension(BASE_SIZE * subjects.size() + 50, BASE_SIZE + 50));
        frame.setMinimumSize(new Dimension(BASE_SIZE * subjects.size() + 50, BASE_SIZE + 50));

        show();
    }

    /**
     * Genère un JPanel avec une image et des aiguilles et l'ajoute à la frame.
     *
     * @param ts          Sujet que l'on doit observer.
     * @param fileName    Chemin du fichier à utiliser comme arrière-plan (200x200)
     * @param hourColor   Couleur de l'aiguille des heures
     * @param minuteColor Couleur de l'aiguille des minutes
     * @param secondColor Couleurs de l'aiguilles des secondes.
     */
    private void addGraphicalTimerToFrame(TimerSubject ts, String fileName, Color hourColor, Color minuteColor, Color secondColor) {
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



    /**
     * Génère un JPanel pour l'affichage numérique et l'ajoute à la frame.
     *
     * @param ts Sujet que l'on doit observer.
     */
    private void addTimerToFrame(TimerSubject ts) {
        // TODO : Implémenter ceci (Ajoute le compteur d'heures, minutes et secondes dans un JPanel qui est ensuite ajouté à la frame.
        // Similaire à addGraphicalTimerToFrame mais sans l'image quoi.
        class DisplayNumeric extends JPanel {
            @Override
            protected void paintComponent(Graphics g) {
                String timeString = String.format("%s: %02dh %02dm %02ds",
                        ts.getName(),
                        getHours(),
                        getMinutes(),
                        getSeconds());

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

    public int getSeconds() {
        return associatedSubject.getTime() % 60;
    }

    public int getMinutes() {
        return (associatedSubject.getTime() / 60) % 60;
    }

    public int getHours() {
        return (associatedSubject.getTime() / 3600) % 24;
    }
}
