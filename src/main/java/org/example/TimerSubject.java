package org.example;

import java.util.Timer;
import java.util.TimerTask;

public class TimerSubject extends Subject {
    private static int timerNumber = 0;
    private int time;
    private String name;
    private Timer timer;

    public TimerSubject() {
        name = "Chrono#" + ++timerNumber;
        time = 0;
        timer = new Timer();
    }

    class TimerUpdater extends TimerTask {
        @Override
        public void run() {
            setTime(time + 1);
        }
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
        timer.schedule(new TimerUpdater(), 0, 1000);
    }

    public void stop() {
        timer.cancel();
    }

    public void reset() {
        timer.cancel();
        time = 0;
    }
}
