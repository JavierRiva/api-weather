package org.apiweather.controller;

import org.apiweather.dto.WeatherDataResponse;
import org.apiweather.exceptions.ApiWeatherException;
import org.apiweather.service.WeatherService;
import org.apiweather.testutils.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @Test
    void testGetWeatherDataByCityAndProvince_Success() throws ApiWeatherException {
        // Configuracion y preparacion de entorno
        String ciudad = "Rosario";
        String provincia = "Santa Fe";
        WeatherDataResponse response = TestData.getWeatherDataResponse();

        // Mockear servicio
        when(weatherService.getWeatherData(ciudad, provincia)).thenReturn(response);

        // Invocacion del metodo
        ResponseEntity<Object> responseEntity = weatherController.getWeatherDataByCityAndProvince(ciudad, provincia);

        // Validaciones
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    void testGetWeatherDataByCityAndProvince_ApiWeatherException() throws ApiWeatherException {
        // Configuracion y preparacion de entorno
        String ciudad = "Rosario";
        String provincia = "Santa Fe";
        String errorMessage = "Error en la consulta de datos meterológicos";

        // Mockear servicio para lanzar una excepción
        when(weatherService.getWeatherData(ciudad, provincia)).thenThrow(new ApiWeatherException(errorMessage));

        // Invocacion del metodo
        ResponseEntity<Object> responseEntity = weatherController.getWeatherDataByCityAndProvince(ciudad, provincia);

        // Validaciones
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }
}

