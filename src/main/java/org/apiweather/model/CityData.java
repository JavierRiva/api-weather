package org.apiweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityData {

    @JsonProperty("Version")
    private int version;
    @JsonProperty("Key")
    private String key;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Rank")
    private int rank;
    @JsonProperty("LocalizedName")
    private String localizedName;
    @JsonProperty("EnglishName")
    private String englishName;
    @JsonProperty("PrimaryPostalCode")
    private String primaryPostalCode;
    @JsonProperty("Region")
    private RegionalData region;
    @JsonProperty("Country")
    private RegionalData country;
    @JsonProperty("AdministrativeArea")
    private AdministrativeData administrativeArea;
    @JsonProperty("TimeZone")
    private TimeZoneData timeZone;
}
