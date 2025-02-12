package com.Airplane.AirplaneApp.Controller;

import com.Airplane.AirplaneApp.Entity.CrewMember;
import com.Airplane.AirplaneApp.Entity.FlightCrew;
import com.Airplane.AirplaneApp.Service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/crew")
@RequiredArgsConstructor
public class CrewController {
    private final CrewService crewService;

    @PostMapping
    public ResponseEntity<CrewMember> createCrewMember(@RequestBody CrewMember crewMember) {
        CrewMember savedCrew = crewService.createCrewMember(crewMember);
        return ResponseEntity.ok(savedCrew);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrewMember> getCrewMember(@PathVariable Long id) {
        return ResponseEntity.ok(crewService.getCrewMember(id));
    }

    @PostMapping("/assign/{flightId}")
    public ResponseEntity<FlightCrew> assignCrewToFlight(@PathVariable Long flightId, @RequestBody Set<CrewMember> crewMembers) {
        return ResponseEntity.ok(crewService.assignCrewToFlight(flightId, crewMembers));
    }

    @GetMapping("/available/{crewTypeCode}")
    public ResponseEntity<List<CrewMember>> getAvailableCrew(@PathVariable String crewTypeCode) {
        return ResponseEntity.ok(crewService.getAvailableCrew(crewTypeCode));
    }
}
