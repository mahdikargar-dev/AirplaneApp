package com.Airplane.AirplaneApp.DTO;

import com.Airplane.AirplaneApp.Entity.FlightStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private Long flightId;
    private String flightNumber;
    private Long aircraftId;
    private Long departureAirportId;
    private Long arrivalAirportId;

    private AircraftDTO aircraft = new AircraftDTO(); // Initialize with empty object
    private AirportDTO departureAirport = new AirportDTO(); // Initialize with empty object
    private AirportDTO arrivalAirport = new AirportDTO(); // Initialize with empty object

    private LocalDateTime scheduledDepartureTime;
    private LocalDateTime scheduledArrivalTime;
    private FlightStatus status = FlightStatus.SCHEDULED; // Default value
    private double basePrice;
    private String gate;
    private String remarks;
}