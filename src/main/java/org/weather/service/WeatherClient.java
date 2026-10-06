package org.weather.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

import static org.weather.Main.properties;

public class WeatherClient {
    HttpClient client;

    private WeatherClient() {
        client = HttpClient.newHttpClient();
    }

    private static final WeatherClient INSTANCE = new WeatherClient();

    public static WeatherClient getInstance() {
        return INSTANCE;
    }

    public HttpResponse<String> getForecast() throws IOException, InterruptedException {
        URI uri = buildForecastURI(WEATHER_FORECAST_API_URL, 59.9386, 30.3141, "ru_RU", 3);
        String API_SECRET_KEY = System.getenv("API_SECRET_KEY");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-Yandex-Weather-Key", API_SECRET_KEY)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new RuntimeException("HTTP Error: " + response.statusCode());
            }

            return response;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static final String WEATHER_FORECAST_API_URL = properties.getProperty("yandex.weather.host");

    public URI buildForecastURI(String WEATHER_API_URL, double lat, double lon, String lang, int days) {

        var uri = URI.create(
                WEATHER_API_URL
                        + "?lat=" + lat // может быть прикол с форматом, кстати
                        + "&lon=" + lon
                        + "&lang=" + lang
                        + "&limit=" + days
        );

        return uri;
    }
}