package ch.heig;

public class TimerObserver implements IObserver {
    private int time;
    protected TimerSubject associatedSubject;
    protected TimerFrame associatedFrame;

    /**
     * Create a new observer
     *
     * @param subject the subject to observe
     * @param frame   the frame to update
     */
    TimerObserver(TimerSubject subject, TimerFrame frame) {
        subject.attach(this);
        associatedSubject = subject;
        associatedFrame = frame;
        time = subject.getTime();
    }

    /**
     * Close the observer
     */
    public void close() {
        associatedSubject.detach(this);
    }

    /**
     * Update the observer
     *
     * @param time the new time given to the observer
     */
    public void update(int time) {
        this.time = time;
        this.associatedFrame.reDraw();
    }

    /**
     * Get the name of the subject timer
     *
     * @return the name of the subject timer
     */
    public String getName() {
        return associatedSubject.getName();
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
     * Get only the seconds of the time
     *
     * @return the seconds of the time, between 0 and 59
     */
    public int getSeconds() {
        return this.getTime() % 60;
    }

    /**
     * Get only the minutes of the time
     *
     * @return the minutes of the time, between 0 and 59
     */
    public int getMinutes() {
        return (this.getTime() / 60) % 60;
    }

    /**
     * Get the time in hours
     *
     * @return the time in hours
     */
    public int getHours() {
        return (this.getTime() / 3600);
    }
}
