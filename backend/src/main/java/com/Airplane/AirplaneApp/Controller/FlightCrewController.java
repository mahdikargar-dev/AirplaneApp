package com.Airplane.AirplaneApp.Controller;

import com.Airplane.AirplaneApp.DTO.FlightCrewDTO;
import com.Airplane.AirplaneApp.Service.FlightCrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight-crews")
public class FlightCrewController {

    @Autowired
    private FlightCrewService flightCrewService;

    @PostMapping
    public ResponseEntity<FlightCrewDTO> createFlightCrew(@RequestBody FlightCrewDTO flightCrewDTO) {
        FlightCrewDTO newFlightCrew = flightCrewService.createFlightCrew(flightCrewDTO);
        return new ResponseEntity<>(newFlightCrew, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FlightCrewDTO>> getAllFlightCrews() {
        List<FlightCrewDTO> flightCrews = flightCrewService.getAllFlightCrews();
        return new ResponseEntity<>(flightCrews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightCrewDTO> getFlightCrewById(@PathVariable Long id) {
        FlightCrewDTO flightCrew = flightCrewService.getFlightCrewById(id);
        return new ResponseEntity<>(flightCrew, HttpStatus.OK);
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<FlightCrewDTO> getFlightCrewByFlightId(@PathVariable Long flightId) {
        FlightCrewDTO flightCrew = flightCrewService.getFlightCrewByFlightId(flightId);
        return new ResponseEntity<>(flightCrew, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightCrewDTO> updateFlightCrew(
            @PathVariable Long id,
            @RequestBody FlightCrewDTO flightCrewDTO) {
        FlightCrewDTO updatedFlightCrew = flightCrewService.updateFlightCrew(id, flightCrewDTO);
        return new ResponseEntity<>(updatedFlightCrew, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlightCrew(@PathVariable Long id) {
        flightCrewService.deleteFlightCrew(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}