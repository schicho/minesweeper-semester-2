package model.timer;

import observer_subject.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class SecondsTimer extends TimerTask implements Subject {
    public int counter = 0;
    public int pausedAt = 0;

    private List<Observer> observerList = new ArrayList<>();

    public void pauseTimer() {
        pausedAt = counter;
    }

    public void unpauseTimer() {
        counter = pausedAt;
        pausedAt = 0;
    }

    @Override
    public void run() {
        counter++;
        notifyObservers();
    }

    @Override
    public void attach(Observer o) {
        observerList.add(o);
    }

    @Override
    public void detach(Observer o) {
        observerList.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observerList) {
            o.update(this);
        }
    }
}
