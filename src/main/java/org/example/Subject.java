package org.example;

import java.util.LinkedList;
import java.util.List;

public abstract class Subject {
    protected List<Observer> observerList;

    Subject() {
        observerList = new LinkedList<>();
    }

    public abstract void notifyObservers();

    public void attach(Observer observer) {
        observerList.add(observer);
    }

    public void detach(Observer observer) {
        observerList.remove(observer);
    }
}
