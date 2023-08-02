package org.apiweather.testutils;

import org.apiweather.database.repository.WeatherDataEntity;
import org.apiweather.dto.TemperatureResponse;
import org.apiweather.dto.WeatherDataResponse;
import org.apiweather.model.*;

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

    public static TemperatureUnit getTemperatureUnit() {
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

    public static TemperatureResponse getTemperatureResponse() {
        return new TemperatureResponse(10, "C");
    }

    public static WeatherDataEntity getWeatherDataEntity() {
        return WeatherDataEntity.builder()
                .city("Rosario")
                .province("Santa Fe")
                .localObservationDateTime("2023-08-01T15:55:00-03:00")
                .epochTime(1)
                .weatherText("Sunny")
                .weatherIcon(1)
                .hasPrecipitation(true)
                .precipitationType("Rain")
                .isDayTime(true)
                .tempMetricValue(10)
                .tempMetricUnit("C")
                .tempImperialValue(10)
                .tempImperialUnit("C")
                .build();
    }

    public static CityData getCityData() {
        CityData cityData = new CityData();
        cityData.setVersion(1);
        cityData.setKey("11222");
        cityData.setType("City");
        cityData.setRank(1);
        cityData.setLocalizedName("Rosario");
        cityData.setEnglishName("Rosario");
        cityData.setPrimaryPostalCode("");
        cityData.setRegion(getRegionalDataRegion());
        cityData.setCountry(getRegionalDataCountry());
        cityData.setAdministrativeArea(getAdministrativeArea());
        return cityData;
    }

    public static RegionalData getRegionalDataRegion() {
        RegionalData regionalDataRegion = new RegionalData();
        regionalDataRegion.setId("SAM");
        regionalDataRegion.setLocalizedName("South America");
        regionalDataRegion.setEnglishName("South America");
        return regionalDataRegion;
    }

    public static RegionalData getRegionalDataCountry() {
        RegionalData regionalDataRegion = new RegionalData();
        regionalDataRegion.setId("AR");
        regionalDataRegion.setLocalizedName("Argentina");
        regionalDataRegion.setEnglishName("Argentina");
        return regionalDataRegion;
    }

    public static AdministrativeData getAdministrativeArea() {
        AdministrativeData administrativeArea = new AdministrativeData();
        administrativeArea.setId("S");
        administrativeArea.setLocalizedName("Santa Fe");
        administrativeArea.setEnglishName("Santa Fe");
        administrativeArea.setLevel(1);
        administrativeArea.setLocalizedType("Province");
        administrativeArea.setEnglishType("Province");
        administrativeArea.setCountryID("AR");
        return administrativeArea;
    }
}
