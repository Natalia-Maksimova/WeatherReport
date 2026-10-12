package org.weather.service;

import org.weather.model.Forecasts;

import java.io.IOException;
import java.util.List;

public class ForecastProvider {
    private final ApiResponseParser parser;
    private final WeatherClient weatherClient;

    public ForecastProvider(ApiResponseParser parser, WeatherClient weatherClient) {
        this.parser = parser;
        this.weatherClient = weatherClient;
    }

    public List<Forecasts> getThreeDaysForecast() throws IOException, InterruptedException {
        return parser.parseWeather(weatherClient.getForecast());
    }
}