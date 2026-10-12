package org.weather.config;

import java.net.http.HttpClient;
import java.util.Properties;

public class WeatherClientConfig {
    public String apiSecretKey;
    public String weatherForecastApiUrl;

    public WeatherClientConfig(Properties properties) {
        weatherForecastApiUrl = properties.getProperty("yandex.weather.host");
        apiSecretKey = System.getenv("API_SECRET_KEY");
    }
}
