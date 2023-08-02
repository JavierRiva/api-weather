package org.apiweather.service;

import lombok.RequiredArgsConstructor;
import org.apiweather.database.converter.WeatherDataEntityToWeatherDataConverter;
import org.apiweather.database.repository.WeatherDataCrudRepository;
import org.apiweather.database.repository.WeatherDataEntity;
import org.apiweather.dto.WeatherDataResponse;
import org.apiweather.exceptions.ApiWeatherException;
import org.apiweather.model.CityData;
import org.apiweather.model.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherDataCrudRepository weatherDataCrudRepository;
    private final WeatherDataEntityToWeatherDataConverter weatherDataEntityToWeatherDataConverter;
    private final RestTemplate restTemplate;

    @Value("${accuweather.api.key}")
    private String apiKey;
    @Value("${accuweather.url.base}")
    private String accuweatherBaseUrl;

    // Mensajes de error
    private static final String ERROR_CIUDAD_NO_ENCONTRADA = "No se encontraron resultados para la ciudad '%s'. " +
            "Verifique que el nombre sea correcto.";
    private static final String ERROR_PROVINCIA_NO_ENCONTRADA = "No se encontraron resultados para la ciudad en la " +
            "provincia '%s'. Verifique que el nombre sea correcto.";
    private static final String ERROR_DATOS_NO_ENCONTRADOS = "No se encontraron datos meteorológicos para la " +
            "ciudad '%s'. Verifique que el nombre sea correcto.";
    private static final String ERROR_DATA_BASE = "Error al intentar obtener información en la base de datos.";

    @Override
    public WeatherDataResponse getWeatherData(String ciudad, String provincia) {

        // Obtener la lista de ciudades desde Accuweather
        List<CityData> cities = obtainCitiesFromAccuweather(ciudad);
        // Obtener los datos meteorológicos desde Accuweather
        WeatherData weatherData = obtainWeatherDataFromAccuweather(cities, ciudad, provincia);
        // Convertir los datos en una entidad y guardarla en la base de datos
        WeatherDataEntity entityToSave = weatherData.toEntity(ciudad, provincia);
        weatherDataCrudRepository.save(entityToSave);
        // Obtener la entidad guardada y convertirla a response
        WeatherDataEntity entityFromDB = weatherDataCrudRepository
                .findById(entityToSave.getId())
                .orElseThrow(() -> new ApiWeatherException(ERROR_DATA_BASE));
        return weatherDataEntityToWeatherDataConverter.convert(entityFromDB);
    }

    // Método para obtener la lista de ciudades desde Accuweather
    private List<CityData> obtainCitiesFromAccuweather(String ciudad) {

        String urlForCitySearch = accuweatherBaseUrl + "locations/v1/cities/AR/search?apikey=" + apiKey + "&q=" + ciudad;
        ResponseEntity<List<CityData>> citiesResponse = restTemplate.exchange(
                urlForCitySearch,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CityData>>() {
                });
        List<CityData> cities = citiesResponse.getBody();
        validateNullOrEmptyList(cities, ciudad, ERROR_CIUDAD_NO_ENCONTRADA);
        return cities;
    }

    // Método para obtener los datos meteorológicos desde Accuweather
    private WeatherData obtainWeatherDataFromAccuweather(List<CityData> cities, String ciudad, String provincia) {

        String locationKey = getLocationKey(cities, provincia);
        String urlForCurrentConditions = accuweatherBaseUrl + "currentconditions/v1/" + locationKey + "?apikey=" + apiKey;
        ResponseEntity<List<WeatherData>> weatherResponse = restTemplate.exchange(
                urlForCurrentConditions,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WeatherData>>() {
                });

        validateNullOrEmptyList(weatherResponse.getBody(), ciudad, ERROR_DATOS_NO_ENCONTRADOS);
        return weatherResponse.getBody().get(0);
    }

    // Método para validar si la lista de ciudades o de datos meteorológicos de una ciudad está vacía o nula
    // Si esta vacía o nula, lanza una excepción ApiWeatherException
    private void validateNullOrEmptyList(List<?> cities, String value, String message) {
        if (cities == null || cities.isEmpty()) {
            throw new ApiWeatherException(String.format(message, value));
        }
    }

    // Método para obtener el locationKey de la ciudad en la provincia dada
    // Si no se encuentra la ciudad en la provincia indicada, lanza una excepción ApiWeatherException
    private String getLocationKey(List<CityData> cities, String provincia) {
        return cities.stream()
                .filter(cityData -> provincia.equalsIgnoreCase(cityData.getAdministrativeArea().getLocalizedName()))
                .map(CityData::getKey)
                .findFirst()
                .orElseThrow(() -> new ApiWeatherException(String.format(ERROR_PROVINCIA_NO_ENCONTRADA, provincia)));
    }
}
