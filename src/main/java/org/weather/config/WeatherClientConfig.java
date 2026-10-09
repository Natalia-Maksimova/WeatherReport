package org.weather.config;

import java.net.http.HttpClient;
import java.util.Properties;

public class WeatherClientConfig {
    public String API_SECRET_KEY;
    public String WEATHER_FORECAST_API_URL;
    public HttpClient client;

    public WeatherClientConfig(Properties properties) {
        WEATHER_FORECAST_API_URL = properties.getProperty("yandex.weather.host");
        client = HttpClient.newHttpClient();
        API_SECRET_KEY = System.getenv("API_SECRET_KEY");
    }
}
