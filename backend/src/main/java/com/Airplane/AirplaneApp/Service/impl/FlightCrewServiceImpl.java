package com.Airplane.AirplaneApp.Service.impl;

import com.Airplane.AirplaneApp.DTO.FlightCrewDTO;
import com.Airplane.AirplaneApp.Entity.FlightCrew;
import com.Airplane.AirplaneApp.Mapper.FlightCrewMapper;
import com.Airplane.AirplaneApp.Repository.FlightCrewRepository;
import com.Airplane.AirplaneApp.Service.FlightCrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightCrewServiceImpl implements FlightCrewService {

    @Autowired
    private FlightCrewRepository flightCrewRepository;

    @Autowired
    private FlightCrewMapper flightCrewMapper;

    @Override
    @Transactional
    public FlightCrewDTO createFlightCrew(FlightCrewDTO flightCrewDTO) {
        validateFlightCrewDTO(flightCrewDTO);

        // Check if flight already has a crew assigned
        flightCrewRepository.findByFlight_FlightId(flightCrewDTO.getFlightId())
                .ifPresent(existingCrew -> {
                    throw new IllegalStateException("Flight already has a crew assigned");
                });

        FlightCrew flightCrew = flightCrewMapper.toEntity(flightCrewDTO);
        FlightCrew savedFlightCrew = flightCrewRepository.save(flightCrew);
        return flightCrewMapper.toDTO(savedFlightCrew);
    }

    @Override
    public List<FlightCrewDTO> getAllFlightCrews() {
        return flightCrewRepository.findAll().stream()
                .map(flightCrewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FlightCrewDTO getFlightCrewById(Long id) {
        FlightCrew flightCrew = flightCrewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight crew not found with id: " + id));
        return flightCrewMapper.toDTO(flightCrew);
    }

    @Override
    public FlightCrewDTO getFlightCrewByFlightId(Long flightId) {
        FlightCrew flightCrew = flightCrewRepository.findByFlight_FlightId(flightId)
                .orElseThrow(() -> new RuntimeException("Flight crew not found for flight id: " + flightId));
        return flightCrewMapper.toDTO(flightCrew);
    }

    @Override
    @Transactional
    public FlightCrewDTO updateFlightCrew(Long id, FlightCrewDTO flightCrewDTO) {
        validateFlightCrewDTO(flightCrewDTO);

        FlightCrew existingFlightCrew = flightCrewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight crew not found with id: " + id));

        // If flight ID is being changed, check if new flight already has a crew
        if (!existingFlightCrew.getFlight().getFlightId().equals(flightCrewDTO.getFlightId())) {
            flightCrewRepository.findByFlight_FlightId(flightCrewDTO.getFlightId())
                    .ifPresent(existingCrew -> {
                        throw new IllegalStateException("Target flight already has a crew assigned");
                    });
        }

        FlightCrew flightCrew = flightCrewMapper.toEntity(flightCrewDTO);
        flightCrew.setFlightCrewId(id);
        FlightCrew updatedFlightCrew = flightCrewRepository.save(flightCrew);
        return flightCrewMapper.toDTO(updatedFlightCrew);
    }

    @Override
    @Transactional
    public void deleteFlightCrew(Long id) {
        FlightCrew existingFlightCrew = flightCrewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight crew not found with id: " + id));
        flightCrewRepository.delete(existingFlightCrew);
    }

    private void validateFlightCrewDTO(FlightCrewDTO flightCrewDTO) {
        if (flightCrewDTO == null) {
            throw new IllegalArgumentException("Flight crew cannot be null");
        }

        if (flightCrewDTO.getFlightId() == null) {
            throw new IllegalArgumentException("Flight ID is required");
        }

        if (flightCrewDTO.getCrewMemberIds() == null || flightCrewDTO.getCrewMemberIds().isEmpty()) {
            throw new IllegalArgumentException("At least one crew member must be assigned");
        }

        if (flightCrewDTO.getStatus() == null || flightCrewDTO.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Status is required");
        }

        validateStatus(flightCrewDTO.getStatus());
    }

    private void validateStatus(String status) {
        if (!status.matches("ASSIGNED|CHECKED_IN|COMPLETED")) {
            throw new IllegalArgumentException("Invalid status. Must be one of: ASSIGNED, CHECKED_IN, COMPLETED");
        }
    }
}