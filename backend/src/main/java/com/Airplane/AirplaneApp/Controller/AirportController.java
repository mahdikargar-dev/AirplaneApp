package com.Airplane.AirplaneApp.Controller;

import com.Airplane.AirplaneApp.DTO.AirportDTO;
import com.Airplane.AirplaneApp.Service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/airport")
@AllArgsConstructor
@Tag(name = "Airport Management", description = "APIs for managing airports")
public class AirportController {

    private final AirportService airportService;

    @PostMapping("addAirport")
    @Operation(summary = "Add a new airport", description = "Create a new airport with the provided details")
    public ResponseEntity<AirportDTO> addAirport(@RequestBody AirportDTO airportDTO) {
        AirportDTO savedAirportDTO = airportService.addAirport(airportDTO);
        return new ResponseEntity<>(savedAirportDTO, HttpStatus.CREATED);
    }

    @GetMapping("listAll")
    @Operation(summary = "Get all airports", description = "Retrieve a list of all airports")
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        List<AirportDTO> allAirports = airportService.getAllAirports();
        return new ResponseEntity<>(allAirports, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get airport by ID", description = "Retrieve an airport by its ID")
    public ResponseEntity<AirportDTO> getAirportById(@PathVariable("id") Long airportId) {
        AirportDTO airportDTO = airportService.getAirportById(airportId);
        return new ResponseEntity<>(airportDTO, HttpStatus.OK);
    }

    @PutMapping("updateAirport/{id}")
    @Operation(summary = "Update an airport", description = "Update an existing airport with the provided details")
    public ResponseEntity<AirportDTO> updateAirport(@PathVariable("id") Long airportId, @RequestBody AirportDTO airportDTO) {
        airportDTO.setAirportId(airportId);
        AirportDTO updatedAirport = airportService.updateAirport(airportDTO);
        return new ResponseEntity<>(updatedAirport, HttpStatus.OK);
    }

    @DeleteMapping("deleteAirport/{id}")
    @Operation(summary = "Delete an airport", description = "Delete an airport by its ID")
    public ResponseEntity<Void> deleteAirport(@PathVariable("id") Long airportId) {
        airportService.deleteAirport(airportId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}