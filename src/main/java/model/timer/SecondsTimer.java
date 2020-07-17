package model.timer;

import java.util.TimerTask;

public class SecondsTimer extends TimerTask {
    public static int counter = 0;
    public static int pausedAt = 0;

    private static boolean paused = false;

    public static void pauseTimer(){
        paused = true;
        pausedAt = counter;
    }

    public static void unpauseTimer(){
        paused = false;
        counter = pausedAt;
        pausedAt = 0;
    }

    @Override
    public void run() {
        if(!paused){
            counter++;
        }
    }
}
