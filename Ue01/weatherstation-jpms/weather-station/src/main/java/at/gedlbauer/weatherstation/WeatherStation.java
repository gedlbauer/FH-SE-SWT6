package at.gedlbauer.weatherstation;

import at.gedlbauer.weathermodel.Measurement;
import at.gedlbauer.weathermodel.SensorListener;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherStation extends SensorListener, AutoCloseable {
    List<Measurement> getMeasurements(LocalDateTime startTime, LocalDateTime endTime);
    double getAverageTemperature(LocalDateTime startTime, LocalDateTime endTime);
}
