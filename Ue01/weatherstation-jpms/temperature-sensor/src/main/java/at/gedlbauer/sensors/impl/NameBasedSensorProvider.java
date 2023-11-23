package at.gedlbauer.sensors.impl;

import at.gedlbauer.sensors.SensorProvider;
import at.gedlbauer.sensors.SensorType;
import at.gedlbauer.weathermodel.Sensor;

import java.util.Optional;

public class NameBasedSensorProvider implements SensorProvider {
    @Override
    public Optional<Sensor> createSensor(SensorType sensorType) {
        Sensor sensor = switch (sensorType) {
            case SIMPLE_TEMPERATURE -> new TemperatureSensor();
            default -> null;
        };
        return Optional.ofNullable(sensor);
    }
}
