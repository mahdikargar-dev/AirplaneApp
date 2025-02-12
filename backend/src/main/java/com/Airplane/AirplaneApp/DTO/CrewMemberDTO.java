package com.Airplane.AirplaneApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewMemberDTO {
    private Long crewMemberId;
    private String firstName;
    private String lastName;
    private String employeeId;
    private LocalDate dateOfBirth;
    private String nationality;
    private String passportNumber;
    private LocalDate passportExpiryDate;
    private Long crewTypeId;
    private String status;
    private String medicalCertificateNumber;
    private LocalDate medicalCertificateExpiry;
}
