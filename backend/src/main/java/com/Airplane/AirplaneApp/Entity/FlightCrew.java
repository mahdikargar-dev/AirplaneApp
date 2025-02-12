package com.Airplane.AirplaneApp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Table(name = "flight_crew")
@Data
public class FlightCrew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightCrewId;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @ManyToMany
    @JoinTable(
            name = "flight_crew_member",
            joinColumns = @JoinColumn(name = "flight_crew_id"),
            inverseJoinColumns = @JoinColumn(name = "crew_member_id")
    )
    private Set<CrewMember> crewMembers;

    @Column(nullable = false)
    private String status; // ASSIGNED, CHECKED_IN, COMPLETED

    private String remarks;
}