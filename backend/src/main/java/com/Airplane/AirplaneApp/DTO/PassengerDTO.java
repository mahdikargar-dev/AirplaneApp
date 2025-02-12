package com.Airplane.AirplaneApp.DTO;

import lombok.Data;

@Data
public class PassengerDTO {
    private Long passengerId;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private String phoneNumber;
    private String email;
    private String passportNumber;
}