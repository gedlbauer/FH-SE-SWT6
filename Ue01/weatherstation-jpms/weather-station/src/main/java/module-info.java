module gedlbauer.weather.station {
    requires gedlbauer.temperature.sensor;
    uses at.gedlbauer.sensors.SensorProvider;
    exports at.gedlbauer.weatherstation;
    provides at.gedlbauer.weatherstation.WeatherStationProvider with at.gedlbauer.weatherstation.impl.DefaultWeatherStationProvider;
}