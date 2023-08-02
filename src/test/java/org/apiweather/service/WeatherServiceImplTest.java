package org.apiweather.service;

import org.apiweather.database.converter.WeatherDataEntityToWeatherDataConverter;
import org.apiweather.database.repository.WeatherDataCrudRepository;
import org.apiweather.database.repository.WeatherDataEntity;
import org.apiweather.dto.WeatherDataResponse;
import org.apiweather.model.CityData;
import org.apiweather.model.WeatherData;
import org.apiweather.testutils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WeatherServiceImplTest {

    @Mock
    private WeatherDataCrudRepository weatherDataCrudRepository;

    @Mock
    private WeatherDataEntityToWeatherDataConverter weatherDataEntityToWeatherDataConverter;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(weatherService, "apiKey", "your_api_key");
        ReflectionTestUtils.setField(weatherService, "accuweatherBaseUrl", "https://api.accuweather.com/");
    }

    @Test
    void testGetWeatherData() {
        // Arrange
        String ciudad = "Rosario";
        String provincia = "Santa Fe";
        WeatherDataResponse expectedResponse = TestData.getWeatherDataResponse();
        WeatherData weatherData = TestData.getWeatherData();
        WeatherDataEntity entity = new WeatherDataEntity(/* Your WeatherDataEntity object here */);

        // Mocking the obtainCitiesFromAccuweather method
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.exchange(
                anyString(),
                any(),
                any(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(
                new ResponseEntity<>(Collections.singletonList(new CityData()), HttpStatus.OK),
                new ResponseEntity<>(Collections.singletonList(weatherData), HttpStatus.OK));

        // Mocking the weatherDataCrudRepository
        when(weatherDataCrudRepository.save(any(WeatherDataEntity.class))).thenReturn(entity);
        when(weatherDataCrudRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        // Mocking the weatherDataEntityToWeatherDataConverter
        when(weatherDataEntityToWeatherDataConverter.convert(any(WeatherDataEntity.class))).thenReturn(expectedResponse);

        // Act
        WeatherDataResponse actualResponse = weatherService.getWeatherData(ciudad, provincia);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(weatherDataCrudRepository, times(1)).save(any(WeatherDataEntity.class));
        verify(weatherDataCrudRepository, times(1)).findById(anyLong());
        verify(weatherDataEntityToWeatherDataConverter, times(1)).convert(any(WeatherDataEntity.class));
    }
}
