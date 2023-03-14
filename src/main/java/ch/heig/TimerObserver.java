package ch.heig;

public class TimerObserver implements IObserver {
    private int time;
    protected TimerSubject associatedSubject;
    protected TimerFrame associatedFrame;

    TimerObserver(TimerSubject subject, TimerFrame frame) {
        subject.attach(this);
        associatedSubject = subject;
        associatedFrame = frame;
        time = subject.getTime();
        associatedFrame.reDraw();
    }

    public void close() {
        associatedSubject.detach(this);
    }

    public void update(int time) {
        this.time = time;
        this.associatedFrame.reDraw();
        System.out.println(associatedSubject.getName() + " has been notified of the time: " + time);
    }

    public String getName() {
        return associatedSubject.getName();
    }

    public int getTime() {
        return time;
    }

    public int getSeconds() {
        return this.getTime() % 60;
    }

    public int getMinutes() {
        return (this.getTime() / 60) % 60;
    }

    public int getHours() {
        return (this.getTime() / 3600) % 24;
    }
}
