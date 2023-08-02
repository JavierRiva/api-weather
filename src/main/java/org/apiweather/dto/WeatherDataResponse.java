package org.apiweather.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class WeatherDataResponse {

    private String ciudad;
    private String provincia;
    private String fecha;
    private String hora;
    private String husoHorario;
    private String estadoTiempo;
    private boolean hayPrecipitacion;
    private String tipoPrecipitacion;
    private boolean esHorarioDiurno;
    private TemperatureResponse temperaturaCelsius;
    private TemperatureResponse temperaturaFarenheit;
}
