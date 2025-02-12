package com.Airplane.AirplaneApp.Service.impl;

import com.Airplane.AirplaneApp.DTO.FlightDTO;
import com.Airplane.AirplaneApp.Entity.Flight;
import com.Airplane.AirplaneApp.Entity.FlightStatus;
import com.Airplane.AirplaneApp.Entity.Seat;
import com.Airplane.AirplaneApp.Exception.ResourceNotFoundException;
import com.Airplane.AirplaneApp.Mapper.FlightMapper;
import com.Airplane.AirplaneApp.Repository.AircraftRepository;
import com.Airplane.AirplaneApp.Repository.AirportRepository;
import com.Airplane.AirplaneApp.Repository.FlightRepository;
import com.Airplane.AirplaneApp.Repository.SeatRepository;
import com.Airplane.AirplaneApp.Service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;
    private final FlightMapper flightMapper;
    private final SeatRepository seatRepository;

    @Override
    @Transactional
    public FlightDTO createFlight(FlightDTO flightDTO) {
        validateFlightDTO(flightDTO);

        Flight flight = flightMapper.toEntity(flightDTO);

        // Set relationships with full entity validation
        flight.setAircraft(aircraftRepository.findById(flightDTO.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with id: " + flightDTO.getAircraftId())));

        flight.setDepartureAirport(airportRepository.findById(flightDTO.getDepartureAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Departure airport not found with id: " + flightDTO.getDepartureAirportId())));

        flight.setArrivalAirport(airportRepository.findById(flightDTO.getArrivalAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Arrival airport not found with id: " + flightDTO.getArrivalAirportId())));

        Flight savedFlight = flightRepository.save(flight);

        // Generate and save seats for the flight
        generateSeats(savedFlight);

        return flightMapper.toDTO(savedFlight);
    }

    private void generateSeats(Flight flight) {
        int capacity = flight.getAircraft().getCapacity();
        System.out.println("Generating seats for flight: " + flight.getFlightNumber() + ", Capacity: " + capacity);

        String[] columns = {"A", "B", "C", "D", "E", "F"};

        List<Seat> seats = new ArrayList<>();

        for (int i = 0; i < capacity; i++) {
            Seat seat = new Seat();
            seat.setFlight(flight);

            // Calculate row and column
            int row = (i / columns.length) + 1;
            int col = i % columns.length;

            seat.setSeatNumber(row + columns[col]);
            seat.setRowNumber(row);
            seat.setColumnLetter(columns[col]);

            // Set position
            if (col == 0 || col == columns.length - 1) {
                seat.setPosition("WINDOW");
            } else if (col == 1 || col == columns.length - 2) {
                seat.setPosition("MIDDLE");
            } else {
                seat.setPosition("AISLE");
            }

            seat.setSeatType("ECONOMY");
            seat.setAvailable(true);
            seat.setBooked(false);

            seats.add(seat);
        }

        seatRepository.saveAll(seats);
    }
    // Rest of the existing methods remain the same...
    @Override
    @Transactional(readOnly = true)
    public FlightDTO getFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        return flightMapper.toDTO(flight);
    }

    @Override
    @Transactional(readOnly = true)
    public FlightDTO getFlightByNumber(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with number: " + flightNumber));
        return flightMapper.toDTO(flight);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightDTO> searchFlights(String departureCity, String arrivalCity) {
        return flightRepository.findByDepartureAirport_LocationCityAndArrivalAirport_LocationCity(departureCity, arrivalCity)
                .stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<String> getOriginCities() {
        return flightRepository.findDistinctDepartureCities();
    }

    @Override
    public List<String> getDestinationCities(String originCode) {
        return flightRepository.findDistinctArrivalCitiesByDepartureCode(originCode);
    }


    @Override
    public List<FlightDTO> getFlightSchedule(LocalDateTime start, LocalDateTime end) {
        return List.of();
    }

    @Override
    public FlightDTO updateFlightStatus(Long flightId, FlightStatus status) {
        return null;
    }

    private void validateFlightDTO(FlightDTO flightDTO) {
        if (flightDTO == null) {
            throw new IllegalArgumentException("Flight data cannot be null");
        }

        if (!flightDTO.getFlightNumber().matches("^[A-Za-z0-9-]*$")) {
            throw new IllegalArgumentException("Invalid flight number format");
        }

        if (flightRepository.findByFlightNumber(flightDTO.getFlightNumber()).isPresent()) {
            throw new IllegalStateException("Flight number already exists");
        }

        if (flightDTO.getScheduledArrivalTime().isBefore(flightDTO.getScheduledDepartureTime())) {
            throw new IllegalArgumentException("Arrival time cannot be before departure time");
        }

        if (flightDTO.getScheduledDepartureTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Departure time cannot be in the past");
        }

        if (flightDTO.getDepartureAirportId().equals(flightDTO.getArrivalAirportId())) {
            throw new IllegalArgumentException("Departure and arrival airports cannot be the same");
        }
    }
}