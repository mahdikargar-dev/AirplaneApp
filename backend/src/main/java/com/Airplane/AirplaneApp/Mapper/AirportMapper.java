package com.Airplane.AirplaneApp.Mapper;

import com.Airplane.AirplaneApp.DTO.AirportDTO;
import com.Airplane.AirplaneApp.Entity.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportMapper {

    public AirportDTO mapAirportToAirportDTO(Airport airport) {
        AirportDTO airportDTO = new AirportDTO();
        airportDTO.setAirportId(airport.getAirportId());
        airportDTO.setCode(airport.getCode());
        airportDTO.setName(airport.getName());
        airportDTO.setLocationCity(airport.getLocationCity());
        airportDTO.setLocationCountry(airport.getLocationCountry());
        return airportDTO;
    }

    public static Airport mapToAirportEntity(AirportDTO airportDTO) {
        Airport airport = new Airport();
        airport.setAirportId(airportDTO.getAirportId());
        airport.setCode(airportDTO.getCode());
        airport.setName(airportDTO.getName());
        airport.setLocationCity(airportDTO.getLocationCity());
        airport.setLocationCountry(airportDTO.getLocationCountry());
        return airport;
    }
}

