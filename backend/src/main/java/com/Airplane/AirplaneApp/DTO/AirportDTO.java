package com.Airplane.AirplaneApp.DTO;

import lombok.Data;

@Data
public class AirportDTO {
    private Long airportId;
    private String code; // Changed to camelCase
    private String name; // Changed to camelCase
    private String locationCity; // Changed to camelCase
    private String locationCountry; // Changed to camelCase
}

