package com.Airplane.AirplaneApp.Controller;

import com.Airplane.AirplaneApp.DTO.CrewTypeDTO;
import com.Airplane.AirplaneApp.Service.CrewTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crew-types")
public class CrewTypeController {

    @Autowired
    private CrewTypeService crewTypeService;

    // Create a new CrewType
    @PostMapping
    public ResponseEntity<CrewTypeDTO> createCrewType(@RequestBody CrewTypeDTO crewTypeDTO) {
        CrewTypeDTO newCrewType = crewTypeService.createCrewType(crewTypeDTO);
        return new ResponseEntity<>(newCrewType, HttpStatus.CREATED);
    }

    // Get all CrewTypes
    @GetMapping
    public ResponseEntity<List<CrewTypeDTO>> getAllCrewTypes() {
        List<CrewTypeDTO> crewTypes = crewTypeService.getAllCrewTypes();
        return new ResponseEntity<>(crewTypes, HttpStatus.OK);
    }

    // Get CrewType by ID
    @GetMapping("/{id}")
    public ResponseEntity<CrewTypeDTO> getCrewTypeById(@PathVariable Long id) {
        CrewTypeDTO crewType = crewTypeService.getCrewTypeById(id);
        return new ResponseEntity<>(crewType, HttpStatus.OK);
    }

    // Update CrewType
    @PutMapping("/{id}")
    public ResponseEntity<CrewTypeDTO> updateCrewType(
            @PathVariable Long id,
            @RequestBody CrewTypeDTO crewTypeDTO) {
        CrewTypeDTO updatedCrewType = crewTypeService.updateCrewType(id, crewTypeDTO);
        return new ResponseEntity<>(updatedCrewType, HttpStatus.OK);
    }

    // Delete CrewType
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrewType(@PathVariable Long id) {
        crewTypeService.deleteCrewType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}