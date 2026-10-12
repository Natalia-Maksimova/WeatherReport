package org.weather;

import org.weather.config.ApiResponseParserConfig;
import org.weather.config.WeatherClientConfig;
import org.weather.service.ApiResponseParser;
import org.weather.service.ForecastProvider;
import org.weather.service.Scheduler;
import org.weather.service.WeatherClient;
import org.weather.ui.ConsoleWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Properties;

public class Main {

    static void main() throws IOException, InterruptedException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/main/java/org/weather/config.properties");
        properties.load(fileInputStream);

        HttpClient client = HttpClient.newHttpClient();

        WeatherClientConfig weatherClientConfig = new WeatherClientConfig(properties);
        WeatherClient weatherClient = new WeatherClient(weatherClientConfig, client);

        ApiResponseParserConfig apiResponseParserConfig = new ApiResponseParserConfig();
        ApiResponseParser parser = new ApiResponseParser(apiResponseParserConfig);

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
