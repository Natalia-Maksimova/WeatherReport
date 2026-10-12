package org.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.weather.config.ApiResponseParserConfig;
import org.weather.model.Forecasts;

import java.net.http.HttpResponse;
import java.util.List;

public class ApiResponseParser {
    ObjectMapper objectMapper;

    public ApiResponseParser(ApiResponseParserConfig config) {
        objectMapper = config.objectMapper;
    }

    public List<Forecasts> parseWeather(HttpResponse<String> response) throws JsonProcessingException {
        String str = findForecast(response.body());
        return objectMapper.readValue(str, new TypeReference<>() {
        });
    }

    private String findForecast(String str) {
        int opening = str.indexOf("[");
        int ending = str.lastIndexOf("]");

        if (opening != -1) {
            str = str.substring(opening, ending + 1);
        }
        return str;
    }
}
