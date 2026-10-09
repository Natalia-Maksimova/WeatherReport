package org.weather.ui;

import org.weather.model.Forecasts;

import java.util.List;

public class ConsoleWriter {
    public static void showWeather(List<Forecasts> threeDaysWeather) {
        System.out.println("Прогноз погоды в Санкт-Петербурге");

        for (Forecasts forecast : threeDaysWeather) {
            printForecast(forecast);
        }
    }

    private static void printForecast(Forecasts forecast) {
        System.out.println(forecast.date());
        System.out.println("Утром \n" + forecast.parts().morning().toString());
        System.out.println("Днём \n" + forecast.parts().day().toString());
        System.out.println("Вечером \n" + forecast.parts().evening().toString());
        System.out.println("Ночью \n" + forecast.parts().night().toString());
    }
}
