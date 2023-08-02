package org.apiweather.database.converter;

import org.apiweather.database.repository.WeatherDataEntity;
import org.apiweather.dto.TemperatureResponse;
import org.apiweather.dto.WeatherDataResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WeatherDataEntityToWeatherDataConverter implements Converter<WeatherDataEntity, WeatherDataResponse> {

    @Override
    public WeatherDataResponse convert(WeatherDataEntity entity) {
        return WeatherDataResponse.builder()
                .ciudad(entity.getCity())
                .provincia(entity.getProvince())
                .fecha(entity.getLocalObservationDateTime().substring(0, 10))
                .hora(entity.getLocalObservationDateTime().substring(11, 19))
                .husoHorario(entity.getLocalObservationDateTime().substring(19))
                .estadoTiempo(entity.getWeatherText())
                .hayPrecipitacion(entity.isHasPrecipitation())
                .tipoPrecipitacion(entity.getPrecipitationType())
                .esHorarioDiurno(entity.isDayTime())
                .temperaturaCelsius(getTemperature(entity.getTempMetricValue(), entity.getTempMetricUnit()))
                .temperaturaFarenheit(getTemperature(entity.getTempImperialValue(), entity.getTempImperialUnit()))
                .build();
    }

    private TemperatureResponse getTemperature(double tempMetricValue, String tempMetricUnit) {
        return new TemperatureResponse(tempMetricValue, tempMetricUnit);
    }
}
