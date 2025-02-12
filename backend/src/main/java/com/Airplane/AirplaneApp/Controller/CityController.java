package com.Airplane.AirplaneApp.Controller;

import com.Airplane.AirplaneApp.Entity.City;
import com.Airplane.AirplaneApp.Service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @PostMapping
    public ResponseEntity<City> addCity(@RequestBody City city) {
        City savedCity = cityService.addCity(city);
        return ResponseEntity.ok(savedCity);
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }
}
