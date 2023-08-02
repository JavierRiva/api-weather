package org.apiweather.controller;

import lombok.RequiredArgsConstructor;
import org.apiweather.dto.WeatherDataResponse;
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

    /**
     * Endpoint para obtener los datos meteorológicos actuales por ciudad y provincia.
     *
     * @param ciudad    Nombre de la ciudad para obtener los datos meteorológicos.
     * @param provincia Nombre de la provincia a la que pertenece la ciudad.
     * @return Respuesta HTTP que contiene los datos meteorológicos actuales en formato ResponseEntity.
     */
    @GetMapping("/current-conditions")
    public ResponseEntity<Object> getWeatherDataByCityAndProvince(
            @RequestParam(value = "ciudad") String ciudad,
            @RequestParam(value = "provincia") String provincia) {
        try {
            // Llama al servicio WeatherService para obtener los datos meteorológicos por ciudad y provincia
            WeatherDataResponse weatherDataResponse = weatherService.getWeatherData(ciudad, provincia);
            // Responde con una respuesta HTTP exitosa (código 200) y los datos meteorológicos en el body
            return ResponseEntity.ok(weatherDataResponse);
        } catch (ApiWeatherException e) {
            // Si ocurre una excepción ApiWeatherException en el servicio, responde con un error HTTP (código 400)
            // y el mensaje de error en el body.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
