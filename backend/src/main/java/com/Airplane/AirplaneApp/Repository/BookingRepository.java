package com.Airplane.AirplaneApp.Repository;

import com.Airplane.AirplaneApp.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.flight.flightId = :flightId")
    List<Booking> findByFlightId(@Param("flightId") Long flightId);
    boolean existsByTicketNumber(String ticketNumber);


}
