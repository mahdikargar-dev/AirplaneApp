package com.Airplane.AirplaneApp.Service;

import com.Airplane.AirplaneApp.Entity.CrewMember;
import com.Airplane.AirplaneApp.Entity.FlightCrew;

import java.util.List;
import java.util.Set;

public interface CrewService {
    CrewMember createCrewMember(CrewMember crewMember);
    CrewMember getCrewMember(Long id);
    FlightCrew assignCrewToFlight(Long flightId, Set<CrewMember> crewMembers);
    List<CrewMember> getAvailableCrew(String crewTypeCode);
}
