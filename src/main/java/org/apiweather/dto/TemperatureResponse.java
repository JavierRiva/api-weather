package org.apiweather.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class TemperatureResponse {

    private double valorTemperatura;
    private String unidadTemperatura;
}
