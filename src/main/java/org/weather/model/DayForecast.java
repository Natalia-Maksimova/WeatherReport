package org.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DayForecast(
        @JsonProperty("temp_avg")
        int tempAvg,
        @JsonProperty("feels_like")
        int feelsLike,
        String condition,
        @JsonProperty("wind_speed")
        int windSpeed
) {

    @Override
    public String toString() {
        return "Температура: " + tempAvg + "\nОщущается как: "
                + feelsLike + "\nПогода: " + condition + "\nСкорость ветра: " + windSpeed + "\n";
    }
}