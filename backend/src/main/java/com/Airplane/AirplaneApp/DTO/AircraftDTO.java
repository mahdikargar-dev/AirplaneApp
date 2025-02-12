package com.Airplane.AirplaneApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AircraftDTO {
    private Long aircraftId;
    private String model;
    private String manufacturer;
    private Integer capacity;
    private String registrationNumber;
    private Integer yearOfManufacture;
    private String status;
    private AircraftDTO aircraft;

    public AircraftDTO(Long aircraftId, String model, String manufacturer, Integer capacity, String registrationNumber, Integer yearOfManufacture, String status) {
    }
}
