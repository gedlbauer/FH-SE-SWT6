module gedlbauer.temperature.sensor {
    requires transitive gedlbauer.weather.model;
    requires org.slf4j;
    exports at.gedlbauer.sensors;
    provides at.gedlbauer.sensors.SensorProvider with at.gedlbauer.sensors.impl.NameBasedSensorProvider;
}