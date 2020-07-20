package model.timer;

import observer_subject.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class SecondsTimer extends TimerTask implements Subject {
    public int counter = 0;
    private boolean isPaused = false;

    private List<Observer> observerList = new ArrayList<>();

    public void pauseTimer() {
        isPaused = true;
    }

    public void unpauseTimer() {
        isPaused = false;
    }

    @Override
    public void run() {
        if (!isPaused) {
            counter++;
            notifyObservers();
        }
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
