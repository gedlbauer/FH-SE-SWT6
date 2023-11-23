package swt6.modular.beans;

public interface Timer {
    void start();

    void stop();

    void addTimerListener(TimerListener listenerToAdd);

    void removeTimerListener(TimerListener listenerToRemove);

    boolean isRunning();
}
