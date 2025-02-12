package com.Airplane.AirplaneApp.Service;

import com.Airplane.AirplaneApp.DTO.SeatDTO;
import com.Airplane.AirplaneApp.Exception.ConcurrentBookingException;

import java.util.List;

public interface SeatService {
    SeatDTO createAndBookSeat(Long flightId, String seatNumber); // Add this method
    SeatDTO createSeat(SeatDTO seatDTO);
    void releaseSeat(Long seatId) throws ConcurrentBookingException;
    SeatDTO getSeat(Long id);
    List<SeatDTO> getSeatsByFlight(Long flightId);

}