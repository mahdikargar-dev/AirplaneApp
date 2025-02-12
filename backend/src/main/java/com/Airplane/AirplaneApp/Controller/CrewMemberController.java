package com.Airplane.AirplaneApp.Controller;

import com.Airplane.AirplaneApp.DTO.CrewMemberDTO;
import com.Airplane.AirplaneApp.Service.CrewMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crew-members")
public class CrewMemberController {

    @Autowired
    private CrewMemberService crewMemberService;

    // Create a new CrewMember
    @PostMapping
    public ResponseEntity<CrewMemberDTO> createCrewMember(@RequestBody CrewMemberDTO crewMemberDTO) {
        CrewMemberDTO newCrewMember = crewMemberService.createCrewMember(crewMemberDTO);
        return new ResponseEntity<>(newCrewMember, HttpStatus.CREATED);
    }

    // Get all CrewMembers
    @GetMapping
    public ResponseEntity<List<CrewMemberDTO>> getAllCrewMembers() {
        List<CrewMemberDTO> crewMembers = crewMemberService.getAllCrewMembers();
        return new ResponseEntity<>(crewMembers, HttpStatus.OK);
    }

    // Get CrewMember by ID
    @GetMapping("/{id}")
    public ResponseEntity<CrewMemberDTO> getCrewMemberById(@PathVariable Long id) {
        CrewMemberDTO crewMember = crewMemberService.getCrewMemberById(id);
        return new ResponseEntity<>(crewMember, HttpStatus.OK);
    }

    // Update CrewMember
    @PutMapping("/{id}")
    public ResponseEntity<CrewMemberDTO> updateCrewMember(
            @PathVariable Long id,
            @RequestBody CrewMemberDTO crewMemberDTO) {
        CrewMemberDTO updatedCrewMember = crewMemberService.updateCrewMember(id, crewMemberDTO);
        return new ResponseEntity<>(updatedCrewMember, HttpStatus.OK);
    }

    // Delete CrewMember
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrewMember(@PathVariable Long id) {
        crewMemberService.deleteCrewMember(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}