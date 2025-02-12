package com.Airplane.AirplaneApp.Service;

import com.Airplane.AirplaneApp.DTO.BookingDTO;

public interface BookingService {
    BookingDTO bookTicket(BookingDTO bookingDTO);

    BookingDTO getBookingDetails(Long bookingId);
}