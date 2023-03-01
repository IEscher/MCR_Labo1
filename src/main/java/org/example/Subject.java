package org.example;

import java.util.LinkedList;
import java.util.List;

public abstract class Subject {
    protected List<IObserver> observerList;

    Subject() {
        observerList = new LinkedList<>();
    }

    public abstract void notifyObservers();

    public void attach(IObserver observer) {
        observerList.add(observer);
    }

    public void detach(IObserver observer) {
        observerList.remove(observer);
    }
}
