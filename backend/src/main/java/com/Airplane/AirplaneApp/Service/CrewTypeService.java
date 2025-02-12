package com.Airplane.AirplaneApp.Service;

import com.Airplane.AirplaneApp.DTO.CrewTypeDTO;

import java.util.List;

public interface CrewTypeService {
    CrewTypeDTO createCrewType(CrewTypeDTO crewTypeDTO);
    List<CrewTypeDTO> getAllCrewTypes();
    CrewTypeDTO getCrewTypeById(Long id);
    CrewTypeDTO updateCrewType(Long id, CrewTypeDTO crewTypeDTO);
    void deleteCrewType(Long id);
}