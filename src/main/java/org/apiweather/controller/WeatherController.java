package org.apiweather.controller;

import lombok.RequiredArgsConstructor;
import org.apiweather.exceptions.ApiWeatherException;
import org.apiweather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/current-conditions")
    public ResponseEntity<Object> getWeatherDataByCityAndProvince(
            @RequestParam(value = "ciudad") String ciudad,
            @RequestParam(value = "provincia") String provincia) {
        try {
            return ResponseEntity.ok(weatherService.getWeatherData(ciudad, provincia));
        } catch (ApiWeatherException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
