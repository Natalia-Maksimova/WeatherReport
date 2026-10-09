package org.weather.model;

public record DayParts(DayForecast day,
                       DayForecast morning,
                       DayForecast evening,
                       DayForecast night) {

}
