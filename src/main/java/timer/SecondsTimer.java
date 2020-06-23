package timer;

import java.util.TimerTask;

public class SecondsTimer extends TimerTask {
    public static int counter = 0;

    @Override
    public void run() {
        counter++;
    }
}
