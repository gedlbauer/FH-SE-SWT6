package swt6.modular.clients;

import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerFactory;
import swt6.modular.beans.TimerProvider;

import java.util.Comparator;
import java.util.ServiceLoader;

public class TimerClient {
    public static void main(String[] args) {
//        testTimer();
        testTimerProvider();
    }

    private static Timer getBestTimer(int interval, int noTicks) {
        ServiceLoader<TimerProvider> loader = ServiceLoader.load(TimerProvider.class);
        TimerProvider minProvider = loader.stream().min(Comparator.comparingDouble(o -> o.get().timerResolution())).get().get();
//        double minResolution = Double.MAX_VALUE;
//        TimerProvider minProvider = null;
//        for (TimerProvider provider : loader) {
//            if (provider.timerResolution() < minResolution) {
//                minResolution = provider.timerResolution();
//                minProvider = provider;
//            }
//        }
        return minProvider == null ? null : minProvider.createTimer(interval, noTicks);
    }

    private static void testTimerProvider() {
        Timer timer = getBestTimer(100, 10);
        if (timer == null) {
            return;
        }
        timer.addTimerListener(evt -> {
            System.out.printf("Elapsed %d of %d times\n", evt.getTickCount(), evt.getNoTicks());
        });
        timer.start();
        sleep(500);
        timer.stop();
        sleep(100);
        timer.start();
    }

    private static void testTimer() {
        Timer timer = TimerFactory.createTimer(100, 10);
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
