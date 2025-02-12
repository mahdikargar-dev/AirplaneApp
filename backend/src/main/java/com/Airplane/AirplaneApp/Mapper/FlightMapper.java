package com.Airplane.AirplaneApp.Mapper;

import com.Airplane.AirplaneApp.DTO.AircraftDTO;
import com.Airplane.AirplaneApp.DTO.AirportDTO;
import com.Airplane.AirplaneApp.DTO.FlightDTO;
import com.Airplane.AirplaneApp.Entity.Flight;
import com.Airplane.AirplaneApp.Entity.FlightStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FlightMapper {
    public FlightDTO toDTO(Flight flight) {
        if (flight == null) return null;

        FlightDTO dto = new FlightDTO();
        dto.setFlightId(flight.getFlightId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setScheduledDepartureTime(flight.getScheduledDepartureTime());
        dto.setScheduledArrivalTime(flight.getScheduledArrivalTime());
        dto.setStatus(flight.getStatus() != null ? flight.getStatus() : FlightStatus.SCHEDULED);
        dto.setBasePrice(flight.getBasePrice().doubleValue()); // Convert BigDecimal to double
        dto.setGate(flight.getGate() != null ? flight.getGate() : "");
        dto.setRemarks(flight.getRemarks() != null ? flight.getRemarks() : "");

        if (flight.getAircraft() != null) {
            AircraftDTO aircraftDTO = new AircraftDTO();
            aircraftDTO.setAircraftId(flight.getAircraft().getAircraftId());
            aircraftDTO.setCapacity(flight.getAircraft().getCapacity());
            aircraftDTO.setModel(flight.getAircraft().getModel() != null ? flight.getAircraft().getModel() : "");
            aircraftDTO.setManufacturer(flight.getAircraft().getManufacturer() != null ? flight.getAircraft().getManufacturer() : "");
            aircraftDTO.setRegistrationNumber(flight.getAircraft().getRegistrationNumber() != null ? flight.getAircraft().getRegistrationNumber() : "");
            aircraftDTO.setYearOfManufacture(flight.getAircraft().getYearOfManufacture());
            aircraftDTO.setStatus(flight.getAircraft().getStatus() != null ? flight.getAircraft().getStatus() : "ACTIVE");
            dto.setAircraft(aircraftDTO);
            dto.setAircraftId(flight.getAircraft().getAircraftId());
        }

        if (flight.getDepartureAirport() != null) {
            AirportDTO departureAirportDTO = new AirportDTO();
            departureAirportDTO.setAirportId(flight.getDepartureAirport().getAirportId());
            departureAirportDTO.setCode(flight.getDepartureAirport().getCode());
            departureAirportDTO.setName(flight.getDepartureAirport().getName());
            departureAirportDTO.setLocationCity(flight.getDepartureAirport().getLocationCity() != null ?
                    flight.getDepartureAirport().getLocationCity() : "");
            departureAirportDTO.setLocationCountry(flight.getDepartureAirport().getLocationCountry() != null ?
                    flight.getDepartureAirport().getLocationCountry() : "");
            dto.setDepartureAirport(departureAirportDTO);
            dto.setDepartureAirportId(flight.getDepartureAirport().getAirportId());
        }

        if (flight.getArrivalAirport() != null) {
            AirportDTO arrivalAirportDTO = new AirportDTO();
            arrivalAirportDTO.setAirportId(flight.getArrivalAirport().getAirportId());
            arrivalAirportDTO.setCode(flight.getArrivalAirport().getCode());
            arrivalAirportDTO.setName(flight.getArrivalAirport().getName());
            arrivalAirportDTO.setLocationCity(flight.getArrivalAirport().getLocationCity() != null ?
                    flight.getArrivalAirport().getLocationCity() : "");
            arrivalAirportDTO.setLocationCountry(flight.getArrivalAirport().getLocationCountry() != null ?
                    flight.getArrivalAirport().getLocationCountry() : "");
            dto.setArrivalAirport(arrivalAirportDTO);
            dto.setArrivalAirportId(flight.getArrivalAirport().getAirportId());
        }

        return dto;
    }

    public Flight toEntity(FlightDTO dto) {
        if (dto == null) return null;

        Flight flight = new Flight();
        flight.setFlightId(dto.getFlightId());
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setScheduledDepartureTime(dto.getScheduledDepartureTime());
        flight.setScheduledArrivalTime(dto.getScheduledArrivalTime());
        flight.setStatus(dto.getStatus() != null ? dto.getStatus() : FlightStatus.SCHEDULED);
        flight.setBasePrice(BigDecimal.valueOf(dto.getBasePrice())); // Convert double to BigDecimal
        flight.setGate(dto.getGate());
        flight.setRemarks(dto.getRemarks());

        // Note: Aircraft, DepartureAirport, and ArrivalAirport are set in the service layer
        // because they require loading the full entities from their respective repositories

        return flight;
    }
}