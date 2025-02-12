package com.Airplane.AirplaneApp.Service.impl;

import com.Airplane.AirplaneApp.DTO.CrewTypeDTO;
import com.Airplane.AirplaneApp.Entity.CrewType;
import com.Airplane.AirplaneApp.Mapper.CrewTypeMapper;
import com.Airplane.AirplaneApp.Repository.CrewTypeRepository;
import com.Airplane.AirplaneApp.Service.CrewTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrewTypeServiceImpl implements CrewTypeService {

    @Autowired
    private CrewTypeRepository crewTypeRepository;

    @Autowired
    private CrewTypeMapper crewTypeMapper;

    @Override
    public CrewTypeDTO createCrewType(CrewTypeDTO crewTypeDTO) {
        // Validate required fields
        validateCrewTypeDTO(crewTypeDTO);

        // Check if CrewType with the same code already exists
        if (crewTypeRepository.existsByCode(crewTypeDTO.getCode())) {
            throw new IllegalArgumentException("CrewType with code " + crewTypeDTO.getCode() + " already exists");
        }

        // Convert DTO to Entity and save
        CrewType crewType = crewTypeMapper.toEntity(crewTypeDTO);
        CrewType savedCrewType = crewTypeRepository.save(crewType);

        // Convert saved Entity back to DTO
        return crewTypeMapper.toDTO(savedCrewType);
    }

    @Override
    public List<CrewTypeDTO> getAllCrewTypes() {
        return crewTypeRepository.findAll().stream()
                .map(crewTypeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CrewTypeDTO getCrewTypeById(Long id) {
        CrewType crewType = crewTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CrewType not found with id: " + id));
        return crewTypeMapper.toDTO(crewType);
    }

    @Override
    public CrewTypeDTO updateCrewType(Long id, CrewTypeDTO crewTypeDTO) {
        // Validate required fields
        validateCrewTypeDTO(crewTypeDTO);

        // Find existing CrewType
        CrewType existingCrewType = crewTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CrewType not found with id: " + id));

        // Update existing CrewType with new values
        existingCrewType.setCode(crewTypeDTO.getCode());
        existingCrewType.setName(crewTypeDTO.getName());
        existingCrewType.setCategory(crewTypeDTO.getCategory());
        existingCrewType.setDescription(crewTypeDTO.getDescription());
        existingCrewType.setRequiredHoursOfRest(crewTypeDTO.getRequiredHoursOfRest());
        existingCrewType.setMaxFlightHoursPerMonth(crewTypeDTO.getMaxFlightHoursPerMonth());

        // Save and return updated CrewType as DTO
        CrewType updatedCrewType = crewTypeRepository.save(existingCrewType);
        return crewTypeMapper.toDTO(updatedCrewType);
    }

    @Override
    public void deleteCrewType(Long id) {
        // Check if CrewType exists
        CrewType existingCrewType = crewTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CrewType not found with id: " + id));

        // Delete CrewType
        crewTypeRepository.delete(existingCrewType);
    }

    // Validation method
    private void validateCrewTypeDTO(CrewTypeDTO crewTypeDTO) {
        if (crewTypeDTO == null) {
            throw new IllegalArgumentException("CrewType cannot be null");
        }
        if (crewTypeDTO.getCode() == null || crewTypeDTO.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Code is required");
        }
        if (crewTypeDTO.getName() == null || crewTypeDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (crewTypeDTO.getCategory() == null || crewTypeDTO.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Category is required");
        }
        if (crewTypeDTO.getRequiredHoursOfRest() == null || crewTypeDTO.getRequiredHoursOfRest() < 0) {
            throw new IllegalArgumentException("Required hours of rest must be a non-negative number");
        }
        if (crewTypeDTO.getMaxFlightHoursPerMonth() == null || crewTypeDTO.getMaxFlightHoursPerMonth() < 0) {
            throw new IllegalArgumentException("Max flight hours per month must be a non-negative number");
        }
    }
}
