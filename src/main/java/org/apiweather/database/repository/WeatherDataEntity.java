package org.apiweather.database.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
public class WeatherDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String province;
    private String localObservationDateTime;
    private long epochTime;
    private String weatherText;
    private int weatherIcon;
    private boolean hasPrecipitation;
    private String precipitationType;
    private boolean isDayTime;
    private double tempMetricValue;
    private String tempMetricUnit;
    private double tempImperialValue;
    private String tempImperialUnit;

    public WeatherDataEntity() {
        // Constructor vacío para la persistencia
    }
}
