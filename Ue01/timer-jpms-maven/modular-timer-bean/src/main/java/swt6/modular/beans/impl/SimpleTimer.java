package swt6.modular.beans.impl;

import swt6.modular.beans.TimerEvent;
import swt6.modular.beans.TimerListener;

import java.util.ArrayList;
import java.util.List;

public class SimpleTimer implements swt6.modular.beans.Timer {
    private int noTicks = 1;
    private int tickInterval = 1000;
    private Thread tickerThread;
    private final List<TimerListener> listeners = new ArrayList<>();
    private boolean stopTimer = false;

    public SimpleTimer(int tickInterval, int noTicks) {
        this.noTicks = noTicks;
        this.tickInterval = tickInterval;
    }

    @Override
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

    @Override
    public void stop() {
        stopTimer = true;
    }

    @Override
    public void addTimerListener(TimerListener listenerToAdd) {
        listeners.add(listenerToAdd);
    }

    @Override
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

    @Override
    public boolean isRunning() {
        return tickerThread != null;
    }
}
