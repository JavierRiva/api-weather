package org.apiweather.service;

import org.apiweather.dto.WeatherDataResponse;

public interface WeatherService {

    WeatherDataResponse getWeatherData(String ciudad, String provincia);
}
