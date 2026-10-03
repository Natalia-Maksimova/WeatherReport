package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ApiResponseParser {
    private ApiResponseParser() {
    }

    static ObjectMapper objectMapper = new ObjectMapper();

    public static List<Forecasts> parseWeather(String str) throws JsonProcessingException {
        int opening = str.indexOf("[");
        int ending = str.lastIndexOf("]");

        if (opening != -1) {
            str = str.substring(opening, ending + 1);
        }

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(str, new TypeReference<>() {});
    }
}
