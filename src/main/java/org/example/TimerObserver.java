package org.example;

public class TimerObserver extends Observer{
    private int time;
    private String name;
    private Subject associatedSubject;

    TimerObserver(Subject subject) {
        this.name = "Default"; // TODO
        subject.attach(this);
        associatedSubject = subject;
    }

    public void close() {
        associatedSubject.detach(this);
    }

    public void update(int time) {
        this.time = time;
        System.out.println(name + " has been notified of the time: " + time);
    }
}
