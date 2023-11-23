package at.gedlbauer.weatherclient;

import at.gedlbauer.weatherstation.WeatherStation;
import at.gedlbauer.weatherstation.WeatherStationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ServiceLoader;

public class WeatherClient {
    private static final Logger logger = LoggerFactory.getLogger(WeatherClient.class);

    private static WeatherStation getWeatherStation() {
        ServiceLoader<WeatherStationProvider> loader = ServiceLoader.load(WeatherStationProvider.class);
        WeatherStationProvider provider = loader.findFirst().orElseThrow();
        return provider.getWeatherStation();
    }

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try (var station = getWeatherStation()) {
            boolean stop = false;
            while (!stop) {
                String input = promptFor(in, "Enter command (list <Seconds to look back>, average <Seconds to look back>), quit");
                if (input.equals("quit")) {
                    stop = true;
                } else {
                    var cmd = input.split(" ");
                    if (cmd.length != 2) {
                        logger.warn("Command malformed - enter exactly 2 params!");
                        continue;
                    }
                    try {
                        int seconds = Integer.parseInt(cmd[1]);
                        LocalDateTime now;
                        switch (cmd[0]) {
                            case "list":
                                now = LocalDateTime.now();
                                station.getMeasurements(now.minusSeconds(seconds), now).forEach(x -> logger.info(x.toString()));
                                break;
                            case "average":
                                now = LocalDateTime.now();
                                logger.info("Average: " + station.getAverageTemperature(now.minusSeconds(seconds), now) + "°C");
                                break;
                            default:
                                logger.warn("Invalid command!");
                        }
                    } catch (NumberFormatException ex) {
                        logger.warn(cmd[1] + "is not a number!");
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    static String promptFor(BufferedReader in, String p) {
        System.out.print(p + "> ");
        System.out.flush();
        try {
            return in.readLine();
        } catch (Exception e) {
            return promptFor(in, p);
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
