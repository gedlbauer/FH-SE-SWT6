package at.gedlbauer.weathermodel;

import java.util.EventObject;

public class MeasurementEvent extends EventObject {
    private Measurement measurement;

    public MeasurementEvent(Object source, Measurement measurement) {
        super(source);
        this.measurement = measurement;
    }

    public Measurement getMeasurement() {
        return measurement;
    }
}
