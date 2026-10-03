package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Parts {
    Forecasts day;
    Forecasts morning;
    Forecasts evening;
    Forecasts night;
}
