package org.example;

public class TimerSubject extends Subject {

    private int time;

    public void notifyObservers() {
        for (Observer observer : observerList) {
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

    }

    public void stop() {

    }

    public void reset() {

    }
}
