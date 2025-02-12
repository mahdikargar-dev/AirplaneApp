package com.Airplane.AirplaneApp.Mapper;

import com.Airplane.AirplaneApp.DTO.PassengerDTO;
import com.Airplane.AirplaneApp.Entity.Passenger;

public class PassengerMapper {

    public static PassengerDTO mapToPassengerDTO(Passenger passenger) {
        if (passenger == null) {
            return null;
        }

        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setPassengerId(passenger.getPassengerId());
        passengerDTO.setFirstName(passenger.getFirstName());
        passengerDTO.setLastName(passenger.getLastName());
        passengerDTO.setNationalCode(passenger.getNationalCode());
        passengerDTO.setPhoneNumber(passenger.getPhoneNumber());
        passengerDTO.setEmail(passenger.getEmail());
        passengerDTO.setPassportNumber(passenger.getPassportNumber());

        return passengerDTO;
    }

    // تبدیل یک PassengerDTO به Passenger entity
    public static Passenger mapToPassengerEntity(PassengerDTO passengerDTO) {
        if (passengerDTO == null) {
            return null;
        }

        Passenger passenger = new Passenger();
        passenger.setPassengerId(passengerDTO.getPassengerId());
        passenger.setFirstName(passengerDTO.getFirstName());
        passenger.setLastName(passengerDTO.getLastName());
        passenger.setNationalCode(passengerDTO.getNationalCode());
        passenger.setPhoneNumber(passengerDTO.getPhoneNumber());
        passenger.setEmail(passengerDTO.getEmail());
        passenger.setPassportNumber(passengerDTO.getPassportNumber());

        return passenger;
    }
}
