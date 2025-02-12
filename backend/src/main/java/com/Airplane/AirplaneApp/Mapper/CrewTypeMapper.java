package com.Airplane.AirplaneApp.Mapper;

import com.Airplane.AirplaneApp.DTO.CrewTypeDTO;
import com.Airplane.AirplaneApp.Entity.CrewType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CrewTypeMapper {
    CrewTypeMapper INSTANCE = Mappers.getMapper(CrewTypeMapper.class);

    CrewTypeDTO toDTO(CrewType crewType);
    CrewType toEntity(CrewTypeDTO crewTypeDTO);
}