package com.Airplane.AirplaneApp.Service.impl;

import com.Airplane.AirplaneApp.DTO.FlightDTO;
import com.Airplane.AirplaneApp.DTO.SeatDTO;
import com.Airplane.AirplaneApp.Entity.Seat;
import com.Airplane.AirplaneApp.Exception.ConcurrentBookingException;
import com.Airplane.AirplaneApp.Exception.ResourceNotFoundException;
import com.Airplane.AirplaneApp.Mapper.SeatMapper;
import com.Airplane.AirplaneApp.Repository.SeatRepository;
import com.Airplane.AirplaneApp.Service.FlightService;
import com.Airplane.AirplaneApp.Service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final FlightService flightService;

    @Transactional
    public SeatDTO createAndBookSeat(Long flightId, String seatNumber) {
        FlightDTO flightDTO = flightService.getFlight(flightId);
        if (flightDTO == null) {
            throw new ResourceNotFoundException("Flight not found with id: " + flightId);
        }

        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setFlightId(flightId);
        seatDTO.setSeatNumber(seatNumber);
        seatDTO.setAvailable(false);
        seatDTO.setBooked(true);

        return createSeat(seatDTO);
    }

    @Override
    @Transactional
    public SeatDTO createSeat(SeatDTO seatDTO) {
        Seat seat = seatMapper.toEntity(seatDTO);
        seat.setAvailable(seatDTO.getAvailable() != null ? seatDTO.getAvailable() : false);
        seat.setBooked(seatDTO.getBooked() != null ? seatDTO.getBooked() : false);

        Seat savedSeat = seatRepository.save(seat);
        return seatMapper.toDTO(savedSeat);
    }

    @Override
    @Transactional
    public void releaseSeat(Long seatId) throws ConcurrentBookingException {
        try {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + seatId));

            seat.setAvailable(true);
            seat.setBooked(false);
            seatRepository.save(seat);
        } catch (OptimisticLockingFailureException ex) {
            throw new ConcurrentBookingException("Unable to release seat due to concurrent modification.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SeatDTO getSeat(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + id));
        return seatMapper.toDTO(seat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatDTO> getSeatsByFlight(Long flightId) {
        return seatMapper.toDTOList(seatRepository.findByFlightId(flightId));
    }
}
