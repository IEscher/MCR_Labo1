// Authors: Fazlija Ylli, Escher Ian

package ch.heig;

public interface IObserver {
    /**
     * Update the observer
     *
     * @param time the new time given to the observer
     */
    void update(int time);
}
