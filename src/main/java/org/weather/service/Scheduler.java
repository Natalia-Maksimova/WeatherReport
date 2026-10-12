package org.weather.service;

import org.weather.model.Forecasts;
import org.weather.ui.ConsoleWriter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    static final long period = 86400000;

    public static void scheduleNotification(Properties properties, ForecastProvider forecastProvider) {
        long initialDelay = calculateDelay(properties);


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                List<Forecasts> threeDaysWeather = null;
                try {
                    threeDaysWeather = forecastProvider.getThreeDaysForecast();
                } catch (IOException | InterruptedException e) {
                    System.out.println("Возникла ошибка на сервере");
                    throw new RuntimeException(e);
                }
                ConsoleWriter.showWeather(threeDaysWeather);
            }
        }, initialDelay, period);
    }

    private static long calculateDelay(Properties properties) {
        ZoneId moscowZone = ZoneId.of("Europe/Moscow");
        ZonedDateTime now = ZonedDateTime.now(moscowZone);
        int hour;
        int minute;

        try {
            hour = Integer.parseInt(properties.getProperty("time.notificationHour"));
        } catch (NumberFormatException e) {
            hour = 8;
        }

        try {
            minute = Integer.parseInt(properties.getProperty("time.notificationMinute"));
        } catch (NumberFormatException e) {
            minute = 0;
        }

        ZonedDateTime target = now.with(LocalTime.of(hour, minute, 0));

        if (now.isAfter(target)) {
            target = target.plusDays(1);
        }

        return Duration.between(now, target).toMillis();
    }
}
