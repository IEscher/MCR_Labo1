package org.example;

public class TimerObserver extends Observer{
    private int time;
    private Subject associatedSubject;

    TimerObserver(Subject subject) {
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
