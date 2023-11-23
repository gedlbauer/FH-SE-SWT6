package swt6.nonmodular.beans;

import java.util.ArrayList;
import java.util.List;

public class SimpleTimer {
    private int noTicks = 1;
    private int tickInterval = 1000;
    private boolean running;
    private Thread tickerThread;
    private final List<TimerListener> listeners = new ArrayList<>();
    private boolean stopTimer = false;

    public SimpleTimer(int tickInterval, int noTicks) {
        this.noTicks = noTicks;
        this.tickInterval = tickInterval;
    }

    public void start() {
        if (isRunning()) {
            throw new IllegalStateException("Timer is already running!");
        }
        int interval = getTickInterval();
        int noTicks = getNoTicks();
        tickerThread = new Thread(() -> {
            int tickCount = 0;
            while (!stopTimer && tickCount < noTicks) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                }
                if (!stopTimer) {
                    fireTickEvent(new TimerEvent(SimpleTimer.this, ++tickCount, noTicks));
                }
            }
            tickerThread = null;
        });
        stopTimer = false;
        tickerThread.start();
    }

    private void fireTickEvent(TimerEvent evt) {
        listeners.forEach(listener -> listener.expired(evt));
    }

    public void stop() {
        stopTimer = true;
    }

    public void reset() {

    }

    public void addTimerListener(TimerListener listenerToAdd) {
        listeners.add(listenerToAdd);
    }

    public void removeTimerListener(TimerListener listenerToRemove) {
        listeners.remove(listenerToRemove);
    }

    public int getNoTicks() {
        return noTicks;
    }

    public void setNoTicks(int noTicks) {
        this.noTicks = noTicks;
    }

    public int getTickInterval() {
        return tickInterval;
    }

    public void setTickInterval(int tickInterval) {
        this.tickInterval = tickInterval;
    }

    public boolean isRunning() {
        return tickerThread != null;
    }
}
