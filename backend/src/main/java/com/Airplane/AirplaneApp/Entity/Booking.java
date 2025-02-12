package com.Airplane.AirplaneApp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(nullable = false, unique = true, length = 8)
    private String ticketNumber;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String nationalId;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private Long seatId; // اضافه کردن seatId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(nullable = false)
    private LocalDateTime bookingTime; // اضافه کردن bookingTime

    @Column(name = "status", nullable = false)
    private String status = "CONFIRMED"; // provide a default value

    @Column(nullable = false)
    private BigDecimal price;
}