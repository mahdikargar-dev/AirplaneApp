package com.Airplane.AirplaneApp.Service;

import com.Airplane.AirplaneApp.Entity.City;

import java.util.List;

public interface CityService {
    City addCity(City city);
    List<City> getAllCities();
}
