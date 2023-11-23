package at.gedlbauer.weathermodel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Measurement {
    double value;
    String unit;
    LocalDateTime timeOfMeasurement;

    public Measurement(double value, String unit, LocalDateTime timeOfMeasurement) {
        this.value = value;
        this.unit = unit;
        this.timeOfMeasurement = timeOfMeasurement;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDateTime getTimeOfMeasurement() {
        return timeOfMeasurement;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Measurement:");
        sb.append(value).append("").append(unit).append(" (").append(timeOfMeasurement.format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm:ss")));
        sb.append(')');
        return sb.toString();
    }
}
