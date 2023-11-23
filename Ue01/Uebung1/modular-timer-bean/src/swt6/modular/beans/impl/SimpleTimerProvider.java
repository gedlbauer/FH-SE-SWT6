package swt6.modular.beans.impl;

import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerProvider;

public class SimpleTimerProvider implements TimerProvider {
    @Override
    public double timerResolution() {
        return 1/1000;
    }

    @Override
    public Timer createTimer(int interval, int noTicks) {
        return new SimpleTimer(interval, noTicks);
    }
}
