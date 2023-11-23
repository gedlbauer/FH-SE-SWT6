package at.gedlbauer.sensors.impl;

import at.gedlbauer.weathermodel.Measurement;
import at.gedlbauer.weathermodel.MeasurementEvent;
import at.gedlbauer.weathermodel.Sensor;
import at.gedlbauer.weathermodel.SensorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class TemperatureSensor implements Sensor {
    private final Logger logger = LoggerFactory.getLogger(TemperatureSensor.class);
    private List<SensorListener> listeners = new ArrayList<>();
    private Timer timer = new Timer();

    private static final double MIN_TMP = -30.0;
    private static final double MAX_TMP = 40.0;
    private static final String UNIT = "°C";

    public TemperatureSensor() {
        start();
    }

    private void start() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (listeners){
                    listeners.forEach(listener -> listener.measured(new MeasurementEvent(TemperatureSensor.this, new Measurement(ThreadLocalRandom.current().nextDouble(MIN_TMP, MAX_TMP), UNIT, LocalDateTime.now()))));
                }
            }
        }, 0, 1000);
    }

    @Override
    public void addListener(SensorListener listener) {
        synchronized (listeners) {
            logger.debug("adding listener to temperature sensor...");
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(SensorListener listener) {
        synchronized (listeners) {
            logger.debug("removing listener from temperature sensor...");
            listeners.remove(listener);
        }
    }

    @Override
    public void close() throws Exception {
        timer.cancel();
    }
}
