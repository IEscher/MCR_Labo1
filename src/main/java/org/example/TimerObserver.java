package org.example;

public class TimerObserver implements IObserver {
    private int time;
    protected TimerSubject associatedSubject;

    TimerObserver(TimerSubject subject) {
        subject.attach(this);
        associatedSubject = subject;
    }

    public void close() {
        associatedSubject.detach(this);
    }

    public void update(int time) {
        this.time = time;
        System.out.println("It has been notified of the time: " + time);
    }
}
