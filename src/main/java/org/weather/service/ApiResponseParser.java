package org.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.weather.model.Forecasts;

import java.util.List;

public class ApiResponseParser {
    public ApiResponseParser() {
    }

    public List<Forecasts> parseWeather(String str, ObjectMapper objectMapper) throws JsonProcessingException {
        str = findForecast(str);
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
