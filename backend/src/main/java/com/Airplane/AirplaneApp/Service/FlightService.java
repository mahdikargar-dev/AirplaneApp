    package com.Airplane.AirplaneApp.Service;

    import com.Airplane.AirplaneApp.DTO.FlightDTO;
    import com.Airplane.AirplaneApp.Entity.FlightStatus;

    import java.time.LocalDateTime;
    import java.util.List;

    public interface FlightService {
        FlightDTO createFlight(FlightDTO flightDTO);
        FlightDTO getFlight(Long id);
        FlightDTO getFlightByNumber(String flightNumber);
        List<FlightDTO> searchFlights(String departureCity, String arrivalCity);

        List<String> getOriginCities();
        List<String> getDestinationCities(String originCode);
        List<FlightDTO> getFlightSchedule(LocalDateTime start, LocalDateTime end);
        FlightDTO updateFlightStatus(Long flightId, FlightStatus status);
    }