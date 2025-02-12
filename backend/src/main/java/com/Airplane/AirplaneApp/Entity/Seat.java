package com.Airplane.AirplaneApp.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version  // Add optimistic locking
    private Long version;
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(name = "status")
    private String status = "AVAILABLE";

    private String seatNumber;
    private Integer rowNumber;
    private String columnLetter;
    private String position; // WINDOW, MIDDLE, AISLE
    private String seatType; // ECONOMY, BUSINESS, FIRST_CLASS
    private Boolean available;
    private Boolean booked;

    public enum SeatStatus {
        AVAILABLE,
        BOOKED,
        RESERVED
    }

    // Manually add getters if Lombok is not generating them
    public Boolean isAvailable() {
        return available;
    }

    public Boolean isBooked() {
        return booked;
    }
}