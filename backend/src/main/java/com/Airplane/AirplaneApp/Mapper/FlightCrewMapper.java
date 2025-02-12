package com.Airplane.AirplaneApp.Mapper;

import com.Airplane.AirplaneApp.DTO.FlightCrewDTO;
import com.Airplane.AirplaneApp.Entity.FlightCrew;
import com.Airplane.AirplaneApp.Entity.Flight;
import com.Airplane.AirplaneApp.Entity.CrewMember;
import com.Airplane.AirplaneApp.Repository.FlightRepository;
import com.Airplane.AirplaneApp.Repository.CrewMemberRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {})
public abstract class FlightCrewMapper {

    @Autowired
    protected FlightRepository flightRepository;

    @Autowired
    protected CrewMemberRepository crewMemberRepository;

    @Mapping(target = "flightId", source = "flight.flightId")
    @Mapping(target = "crewMemberIds", expression = "java(mapCrewMembersToIds(flightCrew.getCrewMembers()))")
    public abstract FlightCrewDTO toDTO(FlightCrew flightCrew);

    @Mapping(target = "flight", expression = "java(mapIdToFlight(flightCrewDTO.getFlightId()))")
    @Mapping(target = "crewMembers", expression = "java(mapIdsToCrewMembers(flightCrewDTO.getCrewMemberIds()))")
    public abstract FlightCrew toEntity(FlightCrewDTO flightCrewDTO);

    protected Flight mapIdToFlight(Long flightId) {
        if (flightId == null) {
            return null;
        }
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + flightId));
    }

    protected Set<Long> mapCrewMembersToIds(Set<CrewMember> crewMembers) {
        if (crewMembers == null) {
            return null;
        }
        return crewMembers.stream()
                .map(CrewMember::getCrewMemberId)
                .collect(Collectors.toSet());
    }

    protected Set<CrewMember> mapIdsToCrewMembers(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream()
                .map(id -> crewMemberRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Crew member not found with id: " + id)))
                .collect(Collectors.toSet());
    }
}