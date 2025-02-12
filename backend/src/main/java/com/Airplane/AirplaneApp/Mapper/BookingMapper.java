package com.Airplane.AirplaneApp.Mapper;

import com.Airplane.AirplaneApp.DTO.BookingDTO;
import com.Airplane.AirplaneApp.Entity.Booking;
import com.Airplane.AirplaneApp.Entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public Booking toEntity(BookingDTO dto, Flight flight) {
        Booking booking = new Booking();
        booking.setTicketNumber(dto.getTicketNumber()); // اضافه کردن شماره بلیط
        booking.setFirstName(dto.getFirstName());
        booking.setLastName(dto.getLastName());
        booking.setNationalId(dto.getNationalId());
        booking.setPhoneNumber(dto.getPhoneNumber());
        booking.setSeatNumber(dto.getSeatNumber());
        booking.setSeatId(dto.getSeatId()); // اضافه کردن seatId
        booking.setFlight(flight);
        booking.setBookingTime(dto.getBookingTime()); // اضافه کردن bookingTime
        booking.setPrice(dto.getPrice());
        booking.setStatus("CONFIRMED"); // یا وضعیت پیش‌فرض دیگر
        return booking;
    }

    public BookingDTO toDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setTicketNumber(booking.getTicketNumber()); // اضافه کردن شماره بلیط
        dto.setBookingId(booking.getBookingId());
        dto.setFirstName(booking.getFirstName());
        dto.setLastName(booking.getLastName());
        dto.setNationalId(booking.getNationalId());
        dto.setPhoneNumber(booking.getPhoneNumber());
        dto.setSeatNumber(booking.getSeatNumber());
        dto.setSeatId(booking.getSeatId()); // اضافه کردن seatId
        dto.setFlightNumber(booking.getFlight().getFlightNumber());
        dto.setDepartureTime(booking.getFlight().getScheduledDepartureTime()); // اضافه کردن departureTime
        dto.setArrivalTime(booking.getFlight().getScheduledArrivalTime()); // اضافه کردن arrivalTime
        dto.setPrice(booking.getPrice());
        dto.setBookingTime(booking.getBookingTime()); // اضافه کردن bookingTime
        return dto;
    }
}