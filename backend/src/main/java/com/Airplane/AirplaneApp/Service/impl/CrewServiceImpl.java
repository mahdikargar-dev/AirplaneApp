package com.Airplane.AirplaneApp.Service.impl;

import com.Airplane.AirplaneApp.Entity.*;
import com.Airplane.AirplaneApp.Exception.ResourceNotFoundException;
import com.Airplane.AirplaneApp.Repository.*;
import com.Airplane.AirplaneApp.Service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CrewServiceImpl implements CrewService {
    private final CrewRepository crewRepository;
    private final CrewTypeRepository crewTypeRepository;
    private final FlightCrewRepository flightCrewRepository;

    @Override
    @Transactional
    public CrewMember createCrewMember(CrewMember crewMember) {
        validateCrewMember(crewMember);
        return crewRepository.save(crewMember);
    }

    @Override
    public CrewMember getCrewMember(Long id) {
        return crewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Crew member not found with id: " + id));
    }

    @Override
    @Transactional
    public FlightCrew assignCrewToFlight(Long flightId, Set<CrewMember> crewMembers) {
        validateCrewAssignment(crewMembers);

        FlightCrew flightCrew = new FlightCrew();
        Flight flight = new Flight();
        flight.setFlightId(flightId);
        flightCrew.setFlight(flight);
        flightCrew.setCrewMembers(crewMembers);
        flightCrew.setStatus("ASSIGNED");

        return flightCrewRepository.save(flightCrew);
    }

    @Override
    public List<CrewMember> getAvailableCrew(String crewTypeCode) {
        return crewRepository.findByCrewType_CodeAndStatus(crewTypeCode, "ACTIVE");
    }

    private void validateCrewMember(CrewMember crewMember) {
        // بررسی وجود Crew Type در دیتابیس
        crewTypeRepository.findById(crewMember.getCrewType().getCrewTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Crew type not found"));
    }

    private void validateCrewAssignment(Set<CrewMember> crewMembers) {
        // بررسی حداقل تعداد اعضای خدمه برای پرواز
        long flightDeckCrewCount = crewMembers.stream()
                .filter(cm -> cm.getCrewType().getCategory().equals("FLIGHT_DECK_CREW"))
                .count();

        long cabinCrewCount = crewMembers.stream()
                .filter(cm -> cm.getCrewType().getCategory().equals("CABIN_CREW"))
                .count();

        if (flightDeckCrewCount < 2) {
            throw new IllegalArgumentException("Minimum two flight deck crew members required");
        }

        if (cabinCrewCount < 3) {
            throw new IllegalArgumentException("Minimum three cabin crew members required");
        }
    }
}
