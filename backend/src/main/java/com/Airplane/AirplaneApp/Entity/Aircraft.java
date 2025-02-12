package com.Airplane.AirplaneApp.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "Aircraft")
@Data
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aircraftId;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @Column(nullable = false)
    private Integer yearOfManufacture;

    @Column(nullable = false)
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aircraft aircraft = (Aircraft) o;
        return Objects.equals(aircraftId, aircraft.aircraftId) &&
                Objects.equals(registrationNumber, aircraft.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aircraftId, registrationNumber);
    }
}
