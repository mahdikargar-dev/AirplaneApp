package com.Airplane.AirplaneApp.Service.impl;

import com.Airplane.AirplaneApp.DTO.AirportDTO;
import com.Airplane.AirplaneApp.Entity.Airport;
import com.Airplane.AirplaneApp.Exception.ResourceNotFoundException;
import com.Airplane.AirplaneApp.Mapper.AirportMapper;
import com.Airplane.AirplaneApp.Repository.AirportRepository;
import com.Airplane.AirplaneApp.Service.AirportService;
import org.springframework.transaction.annotation.Transactional; // Import updated
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    @Override
    @Transactional
    public AirportDTO addAirport(AirportDTO airportDTO) {
        validateAirportDTO(airportDTO);
        Airport airport = airportMapper.mapToAirportEntity(airportDTO);
        Airport savedAirport = airportRepository.save(airport);
        return airportMapper.mapAirportToAirportDTO(savedAirport);
    }

    @Override
    @Transactional(readOnly = true)  // Spring's readOnly attribute works now
    public List<AirportDTO> getAllAirports() {
        List<Airport> airports = airportRepository.findAll();
        return airports.stream()
                .map(airportMapper::mapAirportToAirportDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)  // Spring's readOnly attribute works now
    public AirportDTO getAirportById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + id));
        return airportMapper.mapAirportToAirportDTO(airport);
    }

    @Override
    @Transactional
    public AirportDTO updateAirport(AirportDTO airportDTO) {
        Airport airport = airportRepository.findById(airportDTO.getAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + airportDTO.getAirportId()));

        updateAirportEntityFromDTO(airport, airportDTO);
        Airport updatedAirport = airportRepository.save(airport);
        return airportMapper.mapAirportToAirportDTO(updatedAirport);
    }

    @Override
    @Transactional
    public void deleteAirport(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airport not found with id: " + id);
        }
        airportRepository.deleteById(id);
    }

    @Override
    public AirportDTO updateAirport(Long id, AirportDTO airport) {
        return null;
    }

    @Override
    public void deleteBook(Long id) {

    }

    private void updateAirportEntityFromDTO(Airport airport, AirportDTO airportDTO) {
        if (airportDTO.getCode() != null) {
            airport.setCode(airportDTO.getCode());
        }
        if (airportDTO.getName() != null) {
            airport.setName(airportDTO.getName());
        }
        if (airportDTO.getLocationCity() != null) {
            airport.setLocationCity(airportDTO.getLocationCity());
        }
        if (airportDTO.getLocationCountry() != null) {
            airport.setLocationCountry(airportDTO.getLocationCountry());
        }
    }

    private void validateAirportDTO(AirportDTO airportDTO) {
        if (airportDTO.getCode() == null || airportDTO.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Airport code cannot be empty");
        }
        if (airportDTO.getName() == null || airportDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Airport name cannot be empty");
        }
        if (airportDTO.getLocationCity() == null || airportDTO.getLocationCity().trim().isEmpty()) {
            throw new IllegalArgumentException("Location city cannot be empty");
        }
        if (airportDTO.getLocationCountry() == null || airportDTO.getLocationCountry().trim().isEmpty()) {
            throw new IllegalArgumentException("Location country cannot be empty");
        }
    }
}
