package org.apiweather.database.converter;

import org.apiweather.database.repository.WeatherDataEntity;
import org.apiweather.dto.TemperatureResponse;
import org.apiweather.dto.WeatherDataResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WeatherDataEntityToWeatherDataConverter implements Converter<WeatherDataEntity, WeatherDataResponse> {

    @Override
    // Metodo que convierte la entity WeatherDataEntity en una respuesta WeatherDataResponse
    public WeatherDataResponse convert(WeatherDataEntity entity) {
        // Construye y retorna la respuesta WeatherDataResponse utilizando los datos de la entity
        return WeatherDataResponse.builder()
                .ciudad(entity.getCity()) // Obtiene el nombre de la ciudad desde la entity
                .provincia(entity.getProvince()) // Obtiene el nombre de la provincia desde la entity
                .fecha(entity.getLocalObservationDateTime().substring(0, 10)) // Obtiene la fecha desde la entity
                .hora(entity.getLocalObservationDateTime().substring(11, 19)) // Obtiene la hora desde la entity
                .husoHorario(entity.getLocalObservationDateTime().substring(19)) // Obtiene el huso horario desde la entity
                .estadoTiempo(entity.getWeatherText()) // Obtiene el estado del tiempo desde la entity
                .hayPrecipitacion(entity.isHasPrecipitation()) // Obtiene si hay precipitación desde la entity
                .tipoPrecipitacion(entity.getPrecipitationType()) // Obtiene el tipo de precipitación desde la entity
                .esHorarioDiurno(entity.isDayTime()) // Obtiene si es horario diurno desde la entity
                .temperaturaCelsius(getTemperature(entity.getTempMetricValue(), entity.getTempMetricUnit())) // Obtiene la temperatura en Celsius desde la entity
                .temperaturaFarenheit(getTemperature(entity.getTempImperialValue(), entity.getTempImperialUnit())) // Obtiene la temperatura en Fahrenheit desde la entity
                .build();
    }
    // Método privado para obtener la temperatura a partir de un value y un unit
    private TemperatureResponse getTemperature(double tempMetricValue, String tempMetricUnit) {
        return new TemperatureResponse(tempMetricValue, tempMetricUnit);
    }
}
