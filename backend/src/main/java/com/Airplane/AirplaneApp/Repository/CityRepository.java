package com.Airplane.AirplaneApp.Repository;

import com.Airplane.AirplaneApp.Entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByName(String name);
    boolean existsByCode(String code);
}
