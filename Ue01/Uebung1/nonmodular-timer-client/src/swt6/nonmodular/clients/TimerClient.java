package swt6.nonmodular.clients;

import swt6.nonmodular.beans.SimpleTimer;
import swt6.nonmodular.beans.TimerEvent;
import swt6.nonmodular.beans.TimerListener;

public class TimerClient {
    public static void main(String[] args) {
        SimpleTimer timer = new SimpleTimer(100, 10);
        timer.addTimerListener(evt -> {
            System.out.printf("Elapsed %d of %d times\n", evt.getTickCount(), evt.getNoTicks());
        });
        timer.start();
        sleep(500);
        timer.stop();
        sleep(100);
        timer.start();

    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
