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
        // Arrange
        String ciudad = "Rosario";
        String provincia = "Santa Fe";
        WeatherDataResponse response = TestData.getWeatherDataResponse();

        // Mockear el comportamiento del servicio
        when(weatherService.getWeatherData(ciudad, provincia)).thenReturn(response);

        // Act
        ResponseEntity<Object> responseEntity = weatherController.getWeatherDataByCityAndProvince(ciudad, provincia);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    void testGetWeatherDataByCityAndProvince_ApiWeatherException() throws ApiWeatherException {
        // Arrange
        String ciudad = "Rosario";
        String provincia = "Santa Fe";
        String errorMessage = "Error en la consulta de datos meterológicos";

        // Mockear el comportamiento del servicio para lanzar una excepción
        when(weatherService.getWeatherData(ciudad, provincia)).thenThrow(new ApiWeatherException(errorMessage));

        // Act
        ResponseEntity<Object> responseEntity = weatherController.getWeatherDataByCityAndProvince(ciudad, provincia);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }
}

