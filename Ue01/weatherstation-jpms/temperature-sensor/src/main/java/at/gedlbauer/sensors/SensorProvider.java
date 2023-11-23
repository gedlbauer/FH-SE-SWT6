package at.gedlbauer.sensors;

import at.gedlbauer.weathermodel.Sensor;

import java.util.Optional;

public interface SensorProvider {
    Optional<Sensor> createSensor(SensorType sensorType);
}
