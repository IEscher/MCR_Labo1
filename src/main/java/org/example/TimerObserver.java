package org.example;

import java.util.List;

public class TimerObserver implements IObserver {
    private int time;
    protected TimerSubject associatedSubject;

    TimerObserver(TimerSubject subject) {
        subject.attach(this);
        associatedSubject = subject;
    }

    TimerObserver(List<TimerSubject> subjects) {
        for(TimerSubject s : subjects) {
            s.attach(this);
        }
        associatedSubject = null;
    }

    public void close() {
        associatedSubject.detach(this);
    }

    public void update(int time) {
        this.time = time;
        System.out.println(associatedSubject.getName() + " has been notified of the time: " + time);
    }
}
