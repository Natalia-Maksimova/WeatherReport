package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Forecasts {
    String date;
    Parts parts;
    int temp_avg;
    int feels_like;
    String condition;
    int wind_speed;

    @Override
    public String toString() {
        return "Температура: " + temp_avg + "\nОщущается как: "
                + feels_like + "\nПогода: " + condition + "\nСкорость ветра: " + wind_speed + "\n";
    }
}
