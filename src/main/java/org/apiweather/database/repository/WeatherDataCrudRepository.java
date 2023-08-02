package org.apiweather.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataCrudRepository extends CrudRepository<WeatherDataEntity, Long> {
}
