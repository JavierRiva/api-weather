package org.apiweather.testutils;

import org.apiweather.dto.TemperatureResponse;
import org.apiweather.dto.WeatherDataResponse;
import org.apiweather.model.TemperatureData;
import org.apiweather.model.TemperatureUnit;
import org.apiweather.model.WeatherData;

public class TestData {

    public static WeatherData getWeatherData() {
        WeatherData weatherData = new WeatherData();
        weatherData.setLocalObservationDateTime("2023-08-01T15:55:00-03:00");
        weatherData.setEpochTime(1);
        weatherData.setWeatherText("Sunny");
        weatherData.setWeatherIcon(1);
        weatherData.setHasPrecipitation(true);
        weatherData.setPrecipitationType("Rain");
        weatherData.setDayTime(true);
        weatherData.setTemperature(getTemperatureData());
        return weatherData;
    }

    public static TemperatureData getTemperatureData() {
        TemperatureData temperatureData = new TemperatureData();
        temperatureData.setMetric(getTemperatureUnit());
        temperatureData.setImperial(getTemperatureUnit());
        return temperatureData;
    }

    private static TemperatureUnit getTemperatureUnit() {
        TemperatureUnit temperatureUnit = new TemperatureUnit();
        temperatureUnit.setValue(10);
        temperatureUnit.setUnit("C");
        temperatureUnit.setUnitType(1);
        return temperatureUnit;
    }

    public static WeatherDataResponse getWeatherDataResponse() {
        return WeatherDataResponse.builder()
                .ciudad("Rosario")
                .provincia("Santa Fe")
                .fecha("2023-08-01")
                .hora("15:55:00")
                .husoHorario("-03:00")
                .estadoTiempo("Sunny")
                .hayPrecipitacion(true)
                .tipoPrecipitacion("Rain")
                .esHorarioDiurno(true)
                .temperaturaCelsius(getTemperatureResponse())
                .temperaturaFarenheit(getTemperatureResponse())
                .build();
    }

    private static TemperatureResponse getTemperatureResponse() {
        return new TemperatureResponse(10, "C");
    }
}
