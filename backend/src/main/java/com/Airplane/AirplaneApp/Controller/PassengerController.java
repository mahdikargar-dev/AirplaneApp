package com.Airplane.AirplaneApp.Controller;

import com.Airplane.AirplaneApp.DTO.PassengerDTO;
import com.Airplane.AirplaneApp.Service.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/passengers")
@AllArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerDTO> addPassenger(@RequestBody PassengerDTO passengerDTO) {
        PassengerDTO savedPassenger = passengerService.addPassenger(passengerDTO);
        return new ResponseEntity<>(savedPassenger, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        List<PassengerDTO> passengers = passengerService.getAllPassengers();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable("id") Long passengerId) {
        PassengerDTO passengerDTO = passengerService.getPassengerById(passengerId);
        return new ResponseEntity<>(passengerDTO, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(@PathVariable("id") Long passengerId,
                                                        @RequestBody PassengerDTO passengerDTO) {
        passengerDTO.setPassengerId(passengerId);
        PassengerDTO updatedPassenger = passengerService.updatePassenger(passengerDTO);
        return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePassenger(@PathVariable("id") Long passengerId) {
        passengerService.deletePassenger(passengerId);
        return new ResponseEntity<>("Passenger successfully deleted", HttpStatus.OK);
    }
}