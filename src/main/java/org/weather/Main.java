package org.weather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.weather.model.Forecasts;
import org.weather.service.ApiResponseParser;
import org.weather.service.WeatherClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.*;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static Properties properties = new Properties();

    static void main() throws IOException, InterruptedException {
        FileInputStream fileInputStream = new FileInputStream("config.properties");
        properties.load(fileInputStream);
        WeatherClient weatherClient = WeatherClient.getInstance();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ApiResponseParser parser = new ApiResponseParser();

        showWeather(weatherClient, objectMapper, parser);
        long initialDelay = calculateDelay();
        long period = Long.parseLong(properties.getProperty("time.periodMillis"));

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                showWeather(weatherClient, objectMapper, parser);
            }
        }, initialDelay, period);

    }

    static void showWeather(WeatherClient weatherClient, ObjectMapper objectMapper, ApiResponseParser parser) {
        System.out.println("Прогноз погоды в Санкт-Петербурге");

        try {
            HttpResponse<String> response = weatherClient.getForecast();
            List<Forecasts> threeDaysWeather = parser.parseWeather(response.body(), objectMapper);
            for (Forecasts forecast : threeDaysWeather) {
                printForecast(forecast);
            }
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }

    private static void printForecast(Forecasts forecast) {
        System.out.println(forecast.date());
        System.out.println("Утром \n" + forecast.parts().morning().toString());
        System.out.println("Днём \n" + forecast.parts().day().toString());
        System.out.println("Вечером \n" + forecast.parts().evening().toString());
        System.out.println("Ночью \n" + forecast.parts().night().toString());
    }

    private static long calculateDelay() {
        ZoneId moscowZone = ZoneId.of("Europe/Moscow");
        ZonedDateTime now = ZonedDateTime.now(moscowZone);

        int hour = Integer.parseInt(properties.getProperty("time.notificationHour"));
        int minute = Integer.parseInt(properties.getProperty("time.notificationMinute"));
        int second = Integer.parseInt(properties.getProperty("time.notificationSecond"));

        ZonedDateTime target = now.with(LocalTime.of(hour, minute, second));

        if (now.isAfter(target)) {
            target = target.plusDays(1);
        }

        return Duration.between(now, target).toMillis();
    }

}
