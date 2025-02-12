package com.Airplane.AirplaneApp.Controller;

import com.Airplane.AirplaneApp.DTO.BookingDTO;
import com.Airplane.AirplaneApp.Exception.ResourceNotFoundException;
import com.Airplane.AirplaneApp.Service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/flights/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @PostMapping
    public ResponseEntity<?> bookTicket(@RequestBody BookingDTO bookingDTO) {
        try {
            
            logger.info("Received booking request for flight: " + bookingDTO.getFlightNumber());
            BookingDTO result = bookingService.bookTicket(bookingDTO);
            logger.info("Successfully created booking with ID: " + result.getBookingId());
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid booking request: " + e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            logger.error("Resource not found: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            logger.error("Booking failed: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error creating booking: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingDetails(@PathVariable Long bookingId) {
        try {
            logger.info("Fetching booking details for ID: " + bookingId);
            BookingDTO booking = bookingService.getBookingDetails(bookingId);
            return ResponseEntity.ok(booking);
        } catch (ResourceNotFoundException e) {
            logger.error("Booking not found: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error fetching booking: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

}