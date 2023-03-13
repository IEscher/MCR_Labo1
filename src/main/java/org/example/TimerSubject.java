package org.example;

import java.util.Timer;
import java.util.TimerTask;

public class TimerSubject extends Subject {
    private static int timerNumber = 0;
    private int time;
    private final String name;
    private Timer timer;
    boolean isRunning = false;

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

    public String getName() {
        return name;
    }

    public void notifyObservers() {
        for (IObserver observer : observerList) {
            observer.update(time);
        }
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
        notifyObservers();
    }

    public void start() {
        if(!isRunning) {
            isRunning = true;
            timer = new Timer();
            timer.schedule(new TimerUpdater(), 0, 100);
        }
    }

    public void stop() {
        isRunning = false;
        timer.cancel();
    }

    public void reset() {
        isRunning = false;
        timer.cancel();
        timer.purge();
        time = 0;
        notifyObservers();
    }
}
