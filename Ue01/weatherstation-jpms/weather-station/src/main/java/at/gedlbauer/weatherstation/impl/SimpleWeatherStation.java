package at.gedlbauer.weatherstation.impl;

import at.gedlbauer.sensors.SensorProvider;
import at.gedlbauer.sensors.SensorType;
import at.gedlbauer.weathermodel.Measurement;
import at.gedlbauer.weathermodel.MeasurementEvent;
import at.gedlbauer.weathermodel.Sensor;
import at.gedlbauer.weatherstation.WeatherStation;

import java.time.LocalDateTime;
import java.util.*;

public class SimpleWeatherStation implements WeatherStation {
    private static final int MAX_MEASUREMENTS = 1000;
    private Deque<Measurement> latestMeasurements = new ArrayDeque<>(MAX_MEASUREMENTS);
    private Sensor tempSensor;

    public SimpleWeatherStation() {
        var loader = ServiceLoader.load(SensorProvider.class);
        var provider = loader.findFirst();
        tempSensor = provider.orElseThrow().createSensor(SensorType.SIMPLE_TEMPERATURE).orElseThrow();
        tempSensor.addListener(this);
    }

    @Override
    public void measured(MeasurementEvent event) {
        synchronized (latestMeasurements) {
            if (latestMeasurements.size() > MAX_MEASUREMENTS) {
                latestMeasurements.removeLast();
            }
            latestMeasurements.addFirst(event.getMeasurement());
        }
    }

    @Override
    public List<Measurement> getMeasurements(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime))
            throw new IllegalArgumentException(String.format("entTime cannot be after startTime"));
        return latestMeasurements.stream()
                .filter(x -> x.getTimeOfMeasurement().isAfter(startTime) && x.getTimeOfMeasurement().isBefore(endTime))
                .toList();
    }

    @Override
    public double getAverageTemperature(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime))
            throw new IllegalArgumentException(String.format("entTime cannot be after startTime"));
        OptionalDouble result;
        synchronized (latestMeasurements) {
            result = latestMeasurements.stream()
                    .filter(x -> x.getTimeOfMeasurement().isAfter(startTime) && x.getTimeOfMeasurement().isBefore(endTime))
                    .mapToDouble(Measurement::getValue)
                    .average();
        }
        return result.orElse(0.0);
    }

    @Override
    public void close() throws Exception {
        tempSensor.removeListener(this);
        tempSensor.close();
    }
}
