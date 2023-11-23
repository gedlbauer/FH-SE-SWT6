module gedlbauer.weather.client {
    requires gedlbauer.weather.station;
    requires gedlbauer.weather.model;
    requires org.slf4j;
    uses at.gedlbauer.weatherstation.WeatherStationProvider;
    exports at.gedlbauer.weatherclient;
}