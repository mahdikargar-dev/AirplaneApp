package com.Airplane.AirplaneApp.Mapper;

import com.Airplane.AirplaneApp.DTO.CrewMemberDTO;
import com.Airplane.AirplaneApp.Entity.CrewMember;
import com.Airplane.AirplaneApp.Entity.CrewType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.Airplane.AirplaneApp.Repository.CrewTypeRepository;

@Mapper(componentModel = "spring")
public abstract class CrewMemberMapper {
    @Autowired
    protected CrewTypeRepository crewTypeRepository;

    @Mapping(target = "crewTypeId", source = "crewType.crewTypeId")
    public abstract CrewMemberDTO toDTO(CrewMember crewMember);

    @Mapping(target = "crewType", source = "crewTypeId", qualifiedByName = "mapCrewType")
    @Mapping(target = "flightCrews", ignore = true)
    public abstract CrewMember toEntity(CrewMemberDTO crewMemberDTO);

    @Named("mapCrewType")
    public CrewType mapCrewType(Long crewTypeId) {
        return crewTypeRepository.findById(crewTypeId)
                .orElseThrow(() -> new RuntimeException("CrewType not found with id: " + crewTypeId));
    }
}