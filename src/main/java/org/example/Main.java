package org.example;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    static void main() throws IOException, InterruptedException {
        showWeather();
        long initialDelay = calculateDelayToEightAM();
        long period = 86400000;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                showWeather();
            }
        }, initialDelay, period);

    }

    static void showWeather() {
        System.out.println("Прогноз погоды в Санкт-Петербурге");
        WeatherClient weatherClient = WeatherClient.getInstance();
        try {
            HttpResponse<String> response = weatherClient.getBroadcast();
            List<Forecasts> threeDaysWeather = ApiResponseParser.parseWeather(response.body());
            for (Forecasts forecast : threeDaysWeather) {
                System.out.println(forecast.getDate());
                System.out.println("Утром \n" + forecast.getParts().getMorning().toString());
                System.out.println("Днём \n" + forecast.getParts().getDay().toString());
                System.out.println("Вечером \n" + forecast.getParts().getEvening().toString());
                System.out.println("Ночью \n" + forecast.getParts().getNight().toString());
            }
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }

    private static long calculateDelayToEightAM() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = now.with(LocalTime.of(8, 0, 0)); // Сегодня в 08:00

        if (now.isAfter(target)) {
            target = target.plusDays(1);
        }

        return Duration.between(now, target).toMillis();
    }
}
