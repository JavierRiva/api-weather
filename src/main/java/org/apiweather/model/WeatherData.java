package org.apiweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apiweather.database.repository.WeatherDataEntity;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {

    @JsonProperty("LocalObservationDateTime")
    private String localObservationDateTime;
    @JsonProperty("EpochTime")
    private long epochTime;
    @JsonProperty("WeatherText")
    private String weatherText;
    @JsonProperty("WeatherIcon")
    private int weatherIcon;
    @JsonProperty("HasPrecipitation")
    private boolean hasPrecipitation;
    @JsonProperty("PrecipitationType")
    private String precipitationType;
    @JsonProperty("IsDayTime")
    private boolean isDayTime;
    @JsonProperty("Temperature")
    private TemperatureData temperature;

    public WeatherDataEntity toEntity(String ciudad, String provincia) {
        return WeatherDataEntity.builder()
                .city(ciudad)
                .province(provincia)
                .localObservationDateTime(localObservationDateTime)
                .epochTime(epochTime)
                .weatherText(weatherText)
                .weatherIcon(weatherIcon)
                .hasPrecipitation(hasPrecipitation)
                .precipitationType(precipitationType)
                .isDayTime(isDayTime)
                .tempMetricValue(temperature.getMetric().getValue())
                .tempMetricUnit(temperature.getMetric().getUnit())
                .tempImperialValue(temperature.getImperial().getValue())
                .tempImperialUnit(temperature.getImperial().getUnit())
                .build();
    }
}
