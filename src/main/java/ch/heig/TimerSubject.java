// Authors: Fazlija Ylli, Escher Ian

package ch.heig;

import java.util.Timer;
import java.util.TimerTask;

public class TimerSubject extends Subject {
    private static int timerNumber = 0;
    private int time;
    private final String name;
    private Timer timer;
    private boolean isRunning = false;

    public TimerSubject() {
        name = "Chrono#" + ++timerNumber;
        time = 0;
    }

    class TimerUpdater extends TimerTask {
        @Override
        public void run() {
            setTime(time + 1);
        }
    }

    /**
     * Get the name of the timer
     *
     * @return the name of the timer
     */
    public String getName() {
        return name;
    }

    /**
     * Notify all observers that have been attached to the subject
     */
    public void notifyObservers() {
        for (IObserver observer : observerList) {
            observer.update(time);
        }
    }

    /**
     * Get the time of the subject
     *
     * @return the time of the subject
     */
    public int getTime() {
        return time;
    }

    /**
     * Set the time of the subject
     *
     * @param time the time to set
     */
    public void setTime(int time) {
        this.time = time;
        notifyObservers();
    }

    /**
     * Start the timer
     */
    public void start() {
        if (!isRunning) {
            isRunning = true;
            timer = new Timer();
            timer.schedule(new TimerUpdater(), 0, 1000);
        }
    }

    /**
     * Stop the timer
     */
    public void stop() {
        isRunning = false;
        timer.cancel();
    }

    /**
     * Reset the timer
     */
    public void reset() {
        isRunning = false;
        timer.cancel();
        timer.purge();
        time = 0;
        notifyObservers();
    }
}
