package at.gedlbauer.weathermodel;

public interface Sensor extends AutoCloseable {
    void addListener(SensorListener listener);
    void removeListener(SensorListener listener);
}
