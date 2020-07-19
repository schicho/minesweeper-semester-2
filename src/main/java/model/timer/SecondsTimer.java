package model.timer;

import java.util.TimerTask;

public class SecondsTimer extends TimerTask {
    public static int counter = 0;
    public static int pausedAt = 0;

    public static void pauseTimer() {
        pausedAt = counter;
    }

    public static void unpauseTimer() {
        counter = pausedAt;
        pausedAt = 0;
    }

    @Override
    public void run() {
        counter++;
    }
}
