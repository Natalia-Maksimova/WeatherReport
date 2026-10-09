package org.weather.service;

import org.weather.config.WeatherClientConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherClient {
    HttpClient client;
    private final String API_SECRET_KEY;
    private final String WEATHER_FORECAST_API_URL;
    private final URI uri;

    public WeatherClient(WeatherClientConfig config) {
        client = config.client;
        API_SECRET_KEY = config.API_SECRET_KEY;
        WEATHER_FORECAST_API_URL = config.WEATHER_FORECAST_API_URL;
        uri = buildForecastURI(59.9386, 30.3141, "ru_RU", 3);
    }

    public HttpResponse<String> getForecast() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-Yandex-Weather-Key", API_SECRET_KEY)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() > 200) {
                throw new RuntimeException("HTTP Error: " + response.statusCode());
            }

            return response;
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private URI buildForecastURI(double lat, double lon, String lang, int days) {

        var uri = URI.create(
                WEATHER_FORECAST_API_URL
                        + "/v2/forecast"
                        + "?lat=" + lat
                        + "&lon=" + lon
                        + "&lang=" + lang
                        + "&limit=" + days
        );

        return uri;
    }
}