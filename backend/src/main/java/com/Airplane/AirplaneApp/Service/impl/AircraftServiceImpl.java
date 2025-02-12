package com.Airplane.AirplaneApp.Service.impl;

import com.Airplane.AirplaneApp.DTO.AircraftDTO;
import com.Airplane.AirplaneApp.Entity.Aircraft;
import com.Airplane.AirplaneApp.Repository.AircraftRepository;
import com.Airplane.AirplaneApp.Service.AircraftService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;

    @Override
    @Transactional
    public AircraftDTO addAircraft(AircraftDTO aircraftDTO) {
        Aircraft aircraft = new Aircraft();
        aircraft.setModel(aircraftDTO.getModel());
        aircraft.setManufacturer(aircraftDTO.getManufacturer());
        aircraft.setCapacity(aircraftDTO.getCapacity());
        aircraft.setRegistrationNumber(aircraftDTO.getRegistrationNumber());
        aircraft.setYearOfManufacture(aircraftDTO.getYearOfManufacture());
        aircraft.setStatus("ACTIVE");

        aircraft = aircraftRepository.save(aircraft);
        return mapToDTO(aircraft);
    }

    @Override
    public AircraftDTO updateAircraft(AircraftDTO aircraftDTO) {
        Aircraft aircraft = aircraftRepository.findById(aircraftDTO.getAircraftId())
                .orElseThrow(() -> new RuntimeException("Aircraft not found with id: " + aircraftDTO.getAircraftId()));

        aircraft.setModel(aircraftDTO.getModel());
        aircraft.setManufacturer(aircraftDTO.getManufacturer());
        aircraft.setCapacity(aircraftDTO.getCapacity());
        aircraft.setRegistrationNumber(aircraftDTO.getRegistrationNumber());
        aircraft.setYearOfManufacture(aircraftDTO.getYearOfManufacture());
        aircraft.setStatus(aircraftDTO.getStatus());

        aircraft = aircraftRepository.save(aircraft);
        return mapToDTO(aircraft);
    }

    @Override
    public List<AircraftDTO> getAllAircraft() {
        return aircraftRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AircraftDTO getAircraftById(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found with id: " + id));
        return mapToDTO(aircraft);
    }

    @Override
    public List<AircraftDTO> getAvailableAircraft() {
        return aircraftRepository.findByStatus("ACTIVE").stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAircraft(Long id) {
        aircraftRepository.deleteById(id);
    }

    private AircraftDTO mapToDTO(Aircraft aircraft) {
        return new AircraftDTO(
                aircraft.getAircraftId(),
                aircraft.getModel(),
                aircraft.getManufacturer(),
                aircraft.getCapacity(),
                aircraft.getRegistrationNumber(),
                aircraft.getYearOfManufacture(),
                aircraft.getStatus()
        );
    }
}
