//package org.apiweather.database.repository;
//
//import lombok.RequiredArgsConstructor;
//import org.apiweather.database.converter.WeatherDataEntityToWeatherDataConverter;
//import org.apiweather.dto.WeatherDataResponse;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class WeatherDataRepositoryImpl implements WeatherDataRepository {
//
//    private final WeatherDataEntityToWeatherDataConverter weatherDataEntityToWeatherDataConverter;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public WeatherDataResponse findById(String a) {
//        String query = "SELECT wd FROM WeatherDataEntity wd WHERE wd.id = '" + a + "'";
//        List<WeatherDataEntity> resultList = entityManager.createQuery(query, WeatherDataEntity.class)
//                .getResultList();
//        if (!resultList.isEmpty()) {
//            return weatherDataEntityToWeatherDataConverter.convert(resultList.get(0));
////            return mapEntityToWeatherData(resultList.get(0));
//        }
//        return null;
//    }
//
////    private WeatherData mapEntityToWeatherData(WeatherDataEntity weatherDataEntity) {
////        WeatherData weatherData = new WeatherData();
////        weatherData.setLocalObservationDateTime(weatherDataEntity.getLocalObservationDateTime());
////        weatherData.setEpochTime(weatherDataEntity.getEpochTime());
////        weatherData.setEnglishName(weatherDataEntity.getEnglishName());
////        weatherData.setLevel(weatherDataEntity.getLevel());
////        weatherData.setLocalizedType(weatherDataEntity.getLocalizedType());
////        weatherData.setEnglishType(weatherDataEntity.getEnglishType());
////        weatherData.setCountryID(weatherDataEntity.getCountryID());
////        return weatherData;
//        /*
//
//    @JsonProperty("LocalObservationDateTime")
//    private String localObservationDateTime;
//    @JsonProperty("EpochTime")
//    private long epochTime;
//    @JsonProperty("WeatherText")
//    private String weatherText;
//    @JsonProperty("WeatherIcon")
//    private int weatherIcon;
//    @JsonProperty("HasPrecipitation")
//    private boolean hasPrecipitation;
//    @JsonProperty("PrecipitationType")
//    private String precipitationType;
//    @JsonProperty("IsDayTime")
//    private boolean isDayTime;
//    @JsonProperty("Temperature")
//    private TemperatureData temperature;
//         */
////    }
//}

// todo - borrar
