package at.gedlbauer.weatherstation.impl;

import at.gedlbauer.weatherstation.WeatherStation;
import at.gedlbauer.weatherstation.WeatherStationProvider;

public class DefaultWeatherStationProvider implements WeatherStationProvider {
    @Override
    public WeatherStation getWeatherStation() {
        return new SimpleWeatherStation();
    }
}
