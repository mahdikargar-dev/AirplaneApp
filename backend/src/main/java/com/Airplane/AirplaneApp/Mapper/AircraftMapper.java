package com.Airplane.AirplaneApp.Mapper;

import com.Airplane.AirplaneApp.DTO.AircraftDTO;
import com.Airplane.AirplaneApp.Entity.Aircraft;

public class AircraftMapper {
    public static AircraftDTO mapToAircraftDTO(Aircraft aircraft) {
        AircraftDTO aircraftDTO = new AircraftDTO();
        aircraftDTO.setAircraftId(aircraft.getAircraftId());
        aircraftDTO.setModel(aircraft.getModel());
        aircraftDTO.setManufacturer(aircraft.getManufacturer());
        aircraftDTO.setCapacity(aircraft.getCapacity());
        aircraftDTO.setRegistrationNumber(aircraft.getRegistrationNumber());
        aircraftDTO.setYearOfManufacture(aircraft.getYearOfManufacture());
        aircraftDTO.setStatus(aircraft.getStatus());
        return aircraftDTO;
    }

    public static Aircraft mapToAircraftEntity(AircraftDTO aircraftDTO) {
        Aircraft aircraft = new Aircraft();
        aircraft.setAircraftId(aircraftDTO.getAircraftId());
        aircraft.setModel(aircraftDTO.getModel());
        aircraft.setManufacturer(aircraftDTO.getManufacturer());
        aircraft.setCapacity(aircraftDTO.getCapacity());
        aircraft.setRegistrationNumber(aircraftDTO.getRegistrationNumber());
        aircraft.setYearOfManufacture(aircraftDTO.getYearOfManufacture());
        aircraft.setStatus(aircraftDTO.getStatus());
        return aircraft;
    }
}
