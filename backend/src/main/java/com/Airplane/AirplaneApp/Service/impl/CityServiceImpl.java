package com.Airplane.AirplaneApp.Service.impl;

import com.Airplane.AirplaneApp.Entity.City;
import com.Airplane.AirplaneApp.Repository.CityRepository;
import com.Airplane.AirplaneApp.Service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    @Transactional
    public City addCity(City city) {
        if (cityRepository.existsByName(city.getName())) {
            throw new IllegalArgumentException("City with this name already exists");
        }

        if (cityRepository.existsByCode(city.getCode())) {
            throw new IllegalArgumentException("City with this code already exists");
        }
        return cityRepository.save(city);
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}
