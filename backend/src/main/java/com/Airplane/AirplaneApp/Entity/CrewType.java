package com.Airplane.AirplaneApp.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "crew_type")
@Data
public class CrewType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long crewTypeId;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    private String description;

    @Column(nullable = false)
    private Integer requiredHoursOfRest;

    @Column(nullable = false)
    private Integer maxFlightHoursPerMonth;
}

