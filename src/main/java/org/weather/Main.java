package org.weather;

import org.weather.config.ApiResponceParserConfig;
import org.weather.config.WeatherClientConfig;
import org.weather.service.ApiResponseParser;
import org.weather.service.ForecastProvider;
import org.weather.service.Scheduler;
import org.weather.service.WeatherClient;
import org.weather.ui.ConsoleWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    static void main() throws IOException, InterruptedException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/main/java/org/weather/config.properties");
        properties.load(fileInputStream);

        WeatherClientConfig weatherClientConfig = new WeatherClientConfig(properties);
        WeatherClient weatherClient = new WeatherClient(weatherClientConfig);

        ApiResponceParserConfig apiResponceParserConfig = new ApiResponceParserConfig();
        ApiResponseParser parser = new ApiResponseParser(apiResponceParserConfig);

        ForecastProvider forecastProvider = new ForecastProvider(parser, weatherClient);

        try {
            ConsoleWriter.showWeather(forecastProvider.getThreeDaysForecast());
        } catch (Exception e) {
            System.out.println("Возникла ошибка на сервере");
            throw new RuntimeException(e);
        }

        Scheduler.scheduleNotification(properties, forecastProvider);
    }
}
