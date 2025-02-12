package com.Airplane.AirplaneApp.Service.impl;

import com.Airplane.AirplaneApp.DTO.BookingDTO;
import com.Airplane.AirplaneApp.Entity.Booking;
import com.Airplane.AirplaneApp.Entity.Flight;
import com.Airplane.AirplaneApp.Exception.ResourceNotFoundException;
import com.Airplane.AirplaneApp.Mapper.BookingMapper;
import com.Airplane.AirplaneApp.Repository.BookingRepository;
import com.Airplane.AirplaneApp.Repository.FlightRepository;
import com.Airplane.AirplaneApp.Service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final BookingMapper bookingMapper;

    private String generateUniqueTicketNumber() {
        return String.format("%08d", (int) (Math.random() * 1000000));
    }

    @Override
    @Transactional
    public BookingDTO bookTicket(BookingDTO bookingDTO) {
        Flight flight = flightRepository.findByFlightNumber(bookingDTO.getFlightNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with number: " + bookingDTO.getFlightNumber()));

        Booking booking = bookingMapper.toEntity(bookingDTO, flight);
        booking.setBookingTime(LocalDateTime.now()); // تنظیم زمان رزرو

        if (booking.getPrice() == null || booking.getSeatNumber() == null || booking.getFirstName() == null) {
            throw new IllegalArgumentException("Required booking fields are missing");
        }

        String ticketNumber;
        do {
            ticketNumber = generateUniqueTicketNumber();
        } while (bookingRepository.existsByTicketNumber(ticketNumber));
        booking.setTicketNumber(ticketNumber);
        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toDTO(savedBooking);
    }

    @Override
    public BookingDTO getBookingDetails(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
        return bookingMapper.toDTO(booking);
    }

}