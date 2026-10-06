package org.weather.model;

public record Forecasts(
        String date,
        DayParts parts
) {
}
