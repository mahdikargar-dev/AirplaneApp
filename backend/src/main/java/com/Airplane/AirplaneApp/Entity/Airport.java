package com.Airplane.AirplaneApp.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "Airport")
@Data
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportId;

    @Column(nullable = false)
    private String code; // Changed to camelCase

    @Column(nullable = false)
    private String name; // Changed to camelCase

    @Column(nullable = false)
    private String locationCity; // Changed to camelCase

    @Column(nullable = false)
    private String locationCountry; // Changed to camelCase

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(airportId, airport.airportId) &&
                Objects.equals(code, airport.code) &&
                Objects.equals(name, airport.name) &&
                Objects.equals(locationCity, airport.locationCity) &&
                Objects.equals(locationCountry, airport.locationCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airportId, code, name, locationCity, locationCountry);
    }
}
