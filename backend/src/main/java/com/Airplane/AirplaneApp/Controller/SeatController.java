package com.Airplane.AirplaneApp.Controller;

import com.Airplane.AirplaneApp.DTO.SeatDTO;
import com.Airplane.AirplaneApp.Exception.ConcurrentBookingException;
import com.Airplane.AirplaneApp.Service.SeatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @PostMapping("/book")
    @Transactional
    public ResponseEntity<SeatDTO> bookSeat(@RequestBody Map<String, Object> payload) {
        if (!payload.containsKey("flightId") || !payload.containsKey("seatNumber")) {
            throw new IllegalArgumentException("Required fields flightId and seatNumber must be provided");
        }
        Long flightId = Long.valueOf(payload.get("flightId").toString());
        String seatNumber = payload.get("seatNumber").toString();

        SeatDTO bookedSeat = seatService.createAndBookSeat(flightId, seatNumber);
        return ResponseEntity.ok(bookedSeat);
    }

    @GetMapping("/reserved/{flightId}")
    public ResponseEntity<List<SeatDTO>> getReservedSeats(@PathVariable Long flightId) {
        List<SeatDTO> reservedSeats = seatService.getSeatsByFlight(flightId)
                .stream()
                .filter(seat -> Boolean.TRUE.equals(seat.getBooked()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservedSeats);
    }

    @PostMapping("/create")
    public ResponseEntity<SeatDTO> createSeat(@RequestBody SeatDTO seatDTO) {
        SeatDTO createdSeat = seatService.createSeat(seatDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSeat);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatDTO> getSeat(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.getSeat(id));
    }

    @PostMapping("/release/{id}")
    public ResponseEntity<Void> releaseSeat(@PathVariable Long id) throws ConcurrentBookingException {
        seatService.releaseSeat(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<SeatDTO>> getSeatsByFlight(@PathVariable Long flightId) {
        return ResponseEntity.ok(seatService.getSeatsByFlight(flightId));
    }
}
