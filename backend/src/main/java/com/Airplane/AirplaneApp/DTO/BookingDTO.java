package com.Airplane.AirplaneApp.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long bookingId;
    private String ticketNumber;
    private String firstName;
    private String lastName;
    private String nationalId;
    private String phoneNumber;
    private String seatNumber;
    private Long seatId; // اضافه کردن seatId
    private String flightNumber;
    private LocalDateTime departureTime; // اضافه کردن departureTime
    private LocalDateTime arrivalTime; // اضافه کردن arrivalTime
    private BigDecimal price;
    private LocalDateTime bookingTime; // اضافه کردن bookingTime
}