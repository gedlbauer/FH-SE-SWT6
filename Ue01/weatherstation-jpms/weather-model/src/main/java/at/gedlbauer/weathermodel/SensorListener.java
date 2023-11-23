package at.gedlbauer.weathermodel;

import java.util.EventListener;

public interface SensorListener extends EventListener {
    void measured(MeasurementEvent event);
}
