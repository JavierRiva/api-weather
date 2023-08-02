package org.apiweather;

import org.apiweather.database.converter.WeatherDataEntityToWeatherDataConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = {"org.apiweather", "org.apiweather.database.converter", "org.apiweather.database.repository"})
public class ApiWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiWeatherApplication.class, args);
    }

    @Bean
    public WeatherDataEntityToWeatherDataConverter customWeatherDataConverter() {
        return new WeatherDataEntityToWeatherDataConverter();
    }
}
