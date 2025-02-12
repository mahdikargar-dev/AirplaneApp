package com.Airplane.AirplaneApp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @NotBlank(message = "Flight number is required")
    @Column(nullable = false, unique = true)
    private String flightNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Aircraft aircraft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departureAirportId", referencedColumnName = "airportId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Airport departureAirport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrivalAirportId", referencedColumnName = "airportId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Airport arrivalAirport;

    @NotNull(message = "Scheduled departure time is required")
    @Column(nullable = false)
    private LocalDateTime scheduledDepartureTime;

    @NotNull(message = "Scheduled arrival time is required")
    @Column(nullable = false)
    private LocalDateTime scheduledArrivalTime;

    @NotNull(message = "Flight status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightStatus status = FlightStatus.SCHEDULED;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    private String gate;
    private String remarks;

    @PrePersist
    @PreUpdate
    private void validateTimes() {
        if (scheduledArrivalTime != null && scheduledDepartureTime != null &&
                scheduledArrivalTime.isBefore(scheduledDepartureTime)) {
            throw new IllegalStateException("Arrival time must be after departure time");
        }
    }
}
