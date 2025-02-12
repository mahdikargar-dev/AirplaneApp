package com.Airplane.AirplaneApp.Service;

import com.Airplane.AirplaneApp.DTO.PassengerDTO;
import java.util.List;

public interface PassengerService {
    PassengerDTO addPassenger(PassengerDTO passengerDTO);
    List<PassengerDTO> getAllPassengers();
    PassengerDTO getPassengerById(Long id);
    PassengerDTO updatePassenger(PassengerDTO passengerDTO);
    void deletePassenger(Long id);
}