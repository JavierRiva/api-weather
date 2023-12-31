package org.apiweather.service;

import org.apiweather.database.converter.WeatherDataEntityToWeatherDataConverter;
import org.apiweather.database.repository.WeatherDataCrudRepository;
import org.apiweather.database.repository.WeatherDataEntity;
import org.apiweather.dto.WeatherDataResponse;
import org.apiweather.exceptions.ApiWeatherException;
import org.apiweather.model.CityData;
import org.apiweather.model.WeatherData;
import org.apiweather.testutils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class WeatherServiceImplTest {

    @Mock
    private WeatherDataCrudRepository weatherDataCrudRepository;

    @Mock
    private WeatherDataEntityToWeatherDataConverter weatherDataEntityToWeatherDataConverter;

    @MockBean
    @Autowired
    private RestTemplate restTemplate;

    private WeatherServiceImpl weatherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherService = new WeatherServiceImpl(weatherDataCrudRepository, weatherDataEntityToWeatherDataConverter, restTemplate);
        ReflectionTestUtils.setField(weatherService, "apiKey", "oKzieXH4a0WmQOtGWbBYB5LV7SsQPUsF");
        ReflectionTestUtils.setField(weatherService, "accuweatherBaseUrl", "https://api.accuweather.com/");
    }

    @Test
    void testGetWeatherData_Success() {
        // Configuracion y preparacion de entorno
        String ciudad = "Rosario";
        String provincia = "Santa Fe";
        WeatherDataResponse expectedResponse = TestData.getWeatherDataResponse();
        WeatherData weatherData = TestData.getWeatherData();
        WeatherDataEntity entity = TestData.getWeatherDataEntity();
        CityData cityData = TestData.getCityData();

        // Mockear metodo obtainCitiesFromAccuweather
        ResponseEntity<List<CityData>> citiesResponse = new ResponseEntity<>(
                Collections.singletonList(cityData), HttpStatus.OK);
        when(restTemplate.exchange(
                "https://api.accuweather.com/locations/v1/cities/AR/search?apikey=oKzieXH4a0WmQOtGWbBYB5LV7SsQPUsF&q=Rosario",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CityData>>() {
                }))
                .thenReturn(citiesResponse);

        // Mockear metodo obtainWeatherDataFromAccuweather
        ResponseEntity<List<WeatherData>> weatherResponse = new ResponseEntity<>(
                Collections.singletonList(weatherData), HttpStatus.OK);
        when(restTemplate.exchange(
                "https://api.accuweather.com/currentconditions/v1/11222?apikey=oKzieXH4a0WmQOtGWbBYB5LV7SsQPUsF",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WeatherData>>() {
                }))
                .thenReturn(weatherResponse);

        // Mockear weatherDataCrudRepository
        when(weatherDataCrudRepository.save(any(WeatherDataEntity.class))).thenReturn(entity);
        when(weatherDataCrudRepository.findById(any())).thenReturn(Optional.of(entity));

        // Mockear weatherDataEntityToWeatherDataConverter
        when(weatherDataEntityToWeatherDataConverter.convert(any(WeatherDataEntity.class))).thenReturn(expectedResponse);

        // Invocacion del metodo
        WeatherDataResponse actualResponse = weatherService.getWeatherData(ciudad, provincia);

        // Validaciones
        assertEquals(expectedResponse, actualResponse);
        verify(weatherDataCrudRepository, times(1)).save(any(WeatherDataEntity.class));
        verify(weatherDataCrudRepository, times(1)).findById(any());
        verify(weatherDataEntityToWeatherDataConverter, times(1)).convert(any(WeatherDataEntity.class));
    }

    @Test
    void testGetWeatherData_ApiWeatherException_CiudadNoEncontrada() {
        // Configuracion y preparacion de entorno
        String ciudad = "Rosario";
        String provincia = "Santa Fe";
        String expectedMessage = "No se encontraron resultados para la ciudad 'Rosario'. Verifique que el nombre sea correcto.";

        // Mockear metodo obtainCitiesFromAccuweather
        ResponseEntity<List<CityData>> citiesResponse = new ResponseEntity<>(
                Collections.emptyList(), HttpStatus.OK);
        when(restTemplate.exchange(
                "https://api.accuweather.com/locations/v1/cities/AR/search?apikey=oKzieXH4a0WmQOtGWbBYB5LV7SsQPUsF&q=Rosario",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CityData>>() {
                }))
                .thenReturn(citiesResponse);

        // Verificar que se lanza una ApiWeatherException cuando la lista de ciudades está vacía
        ApiWeatherException exception = assertThrows(ApiWeatherException.class,
                () -> weatherService.getWeatherData(ciudad, provincia));
        // Verificar el mensaje de la excepción
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testGetWeatherData_ApiWeatherException_ProvinciaNoEncontrada() {
        // Configuracion y preparacion de entorno
        String ciudad = "Rosario";
        String provincia = "Misiones";
        CityData cityData = TestData.getCityData();
        WeatherData weatherData = TestData.getWeatherData();
        String expectedMessage = "No se encontraron resultados para la ciudad en la provincia 'Misiones'. Verifique que el nombre sea correcto.";

        // Mockear metodo obtainCitiesFromAccuweather
        ResponseEntity<List<CityData>> citiesResponse = new ResponseEntity<>(
                Collections.singletonList(cityData), HttpStatus.OK);
        when(restTemplate.exchange(
                "https://api.accuweather.com/locations/v1/cities/AR/search?apikey=oKzieXH4a0WmQOtGWbBYB5LV7SsQPUsF&q=Rosario",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CityData>>() {
                }))
                .thenReturn(citiesResponse);

        // Mockear metodo obtainWeatherDataFromAccuweather
        ResponseEntity<List<WeatherData>> weatherResponse = new ResponseEntity<>(
                Collections.singletonList(weatherData), HttpStatus.OK);
        when(restTemplate.exchange(
                "https://api.accuweather.com/currentconditions/v1/11222?apikey=oKzieXH4a0WmQOtGWbBYB5LV7SsQPUsF",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WeatherData>>() {
                }))
                .thenReturn(weatherResponse);

        // Verificar que se lanza una ApiWeatherException cuando la ciudad no coincide con la provincia
        ApiWeatherException exception = assertThrows(ApiWeatherException.class,
                () -> weatherService.getWeatherData(ciudad, provincia));
        // Verificar el mensaje de la excepción
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testGetWeatherData_ApiWeatherException_DatosMetNoEncontrados() {
        // Configuracion y preparacion de entorno
        String ciudad = "Rosario";
        String provincia = "Santa Fe";
        CityData cityData = TestData.getCityData();
        String expectedMessage = "No se encontraron datos meteorológicos para la ciudad 'Rosario'. Verifique que el nombre sea correcto.";

        // Mockear metodo obtainCitiesFromAccuweather
        ResponseEntity<List<CityData>> citiesResponse = new ResponseEntity<>(
                Collections.singletonList(cityData), HttpStatus.OK);
        when(restTemplate.exchange(
                "https://api.accuweather.com/locations/v1/cities/AR/search?apikey=oKzieXH4a0WmQOtGWbBYB5LV7SsQPUsF&q=Rosario",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CityData>>() {
                }))
                .thenReturn(citiesResponse);

        // Mockear metodo obtainWeatherDataFromAccuweather
        ResponseEntity<List<WeatherData>> weatherResponse = new ResponseEntity<>(
                Collections.emptyList(), HttpStatus.OK);
        when(restTemplate.exchange(
                "https://api.accuweather.com/currentconditions/v1/11222?apikey=oKzieXH4a0WmQOtGWbBYB5LV7SsQPUsF",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WeatherData>>() {
                }))
                .thenReturn(weatherResponse);

        // Verificar que se lanza una ApiWeatherException cuando la lista de datos meteorologicos esta vacia
        ApiWeatherException exception = assertThrows(ApiWeatherException.class,
                () -> weatherService.getWeatherData(ciudad, provincia));
        // Verificar el mensaje de la excepción
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testGetWeatherData_Success2222() {
        // Configuracion y preparacion de entorno
        String ciudad = "Rosario";
        String provincia = "Santa Fe";
        WeatherData weatherData = TestData.getWeatherData();
        WeatherDataEntity entity = TestData.getWeatherDataEntity();
        CityData cityData = TestData.getCityData();
        String expectedMessage = "Error al intentar obtener información en la base de datos.";

        // Mockear metodo obtainCitiesFromAccuweather
        ResponseEntity<List<CityData>> citiesResponse = new ResponseEntity<>(
                Collections.singletonList(cityData), HttpStatus.OK);
        when(restTemplate.exchange(
                "https://api.accuweather.com/locations/v1/cities/AR/search?apikey=oKzieXH4a0WmQOtGWbBYB5LV7SsQPUsF&q=Rosario",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CityData>>() {
                }))
                .thenReturn(citiesResponse);

        // Mockear metodo obtainWeatherDataFromAccuweather
        ResponseEntity<List<WeatherData>> weatherResponse = new ResponseEntity<>(
                Collections.singletonList(weatherData), HttpStatus.OK);
        when(restTemplate.exchange(
                "https://api.accuweather.com/currentconditions/v1/11222?apikey=oKzieXH4a0WmQOtGWbBYB5LV7SsQPUsF",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WeatherData>>() {
                }))
                .thenReturn(weatherResponse);

        // Mockear weatherDataCrudRepository
        when(weatherDataCrudRepository.save(any(WeatherDataEntity.class))).thenReturn(entity);
        when(weatherDataCrudRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        // Verificar que se lanza una ApiWeatherException cuando no encuentra id del entity en la base de datos
        ApiWeatherException exception = assertThrows(ApiWeatherException.class,
                () -> weatherService.getWeatherData(ciudad, provincia));
        // Verificar el mensaje de la excepción
        assertEquals(expectedMessage, exception.getMessage());
    }
}
