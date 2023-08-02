package org.apiweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemperatureData {

    @JsonProperty("Metric")
    private TemperatureUnit metric;
    @JsonProperty("Imperial")
    private TemperatureUnit imperial;
}
