package com.Airplane.AirplaneApp.Service;

import com.Airplane.AirplaneApp.DTO.AirportDTO;

import java.util.List;

public interface AirportService {
    AirportDTO addAirport(AirportDTO airport);

    List<AirportDTO> getAllAirports();

    AirportDTO getAirportById(Long id);

    AirportDTO updateAirport(AirportDTO airport);

    void deleteAirport(Long id);

    AirportDTO updateAirport(Long id, AirportDTO airport);

    void deleteBook(Long id);
}
