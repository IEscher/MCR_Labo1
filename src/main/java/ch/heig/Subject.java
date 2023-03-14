// Authors: Fazlija Ylli, Escher Ian

package ch.heig;

import java.util.LinkedList;
import java.util.List;

public abstract class Subject {
    protected List<IObserver> observerList;

    Subject() {
        observerList = new LinkedList<>();
    }

    /**
     * Notify all observers that have been attached to the subject
     */
    public abstract void notifyObservers();

    /**
     * Attach an observer to the subject
     *
     * @param observer the observer to attach
     */
    public void attach(IObserver observer) {
        observerList.add(observer);
    }

    /**
     * Detach an observer from the subject
     *
     * @param observer the observer to detach
     */
    public void detach(IObserver observer) {
        observerList.remove(observer);
    }
}
