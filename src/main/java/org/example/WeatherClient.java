package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherClient {
    HttpClient client;

    private WeatherClient() {
        client = HttpClient.newHttpClient();
    }

    private static final WeatherClient INSTANCE = new WeatherClient();

    public static WeatherClient getInstance() {
        return INSTANCE;
    }

    public HttpResponse<String> getBroadcast() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.yandex.ru/v2/forecast?lat=59.9386&lon=30.3141&lang=ru_RU&limit=3"))
                .header("X-Yandex-Weather-Key", "52e680bd-d669-4bda-9ca4-cac9f1e5754a")
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}