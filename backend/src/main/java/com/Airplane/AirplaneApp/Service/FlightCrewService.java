package com.Airplane.AirplaneApp.Service;

import com.Airplane.AirplaneApp.DTO.FlightCrewDTO;
import java.util.List;

public interface FlightCrewService {
    FlightCrewDTO createFlightCrew(FlightCrewDTO flightCrewDTO);
    List<FlightCrewDTO> getAllFlightCrews();
    FlightCrewDTO getFlightCrewById(Long id);
    FlightCrewDTO getFlightCrewByFlightId(Long flightId);
    FlightCrewDTO updateFlightCrew(Long id, FlightCrewDTO flightCrewDTO);
    void deleteFlightCrew(Long id);
}