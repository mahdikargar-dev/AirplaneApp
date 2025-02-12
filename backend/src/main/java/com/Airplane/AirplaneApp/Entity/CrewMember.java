package com.Airplane.AirplaneApp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "crew_member")
@Data
public class CrewMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long crewMemberId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String employeeId;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private String passportNumber;

    @Column(nullable = false)
    private LocalDate passportExpiryDate;

    @ManyToOne
    @JoinColumn(name = "crew_type_id", nullable = false)
    private CrewType crewType;

    @Column(nullable = false)
    private String status; // ACTIVE, ON_LEAVE, INACTIVE

    private String medicalCertificateNumber;

    private LocalDate medicalCertificateExpiry;

    @ManyToMany(mappedBy = "crewMembers")
    private Set<FlightCrew> flightCrews;
}
