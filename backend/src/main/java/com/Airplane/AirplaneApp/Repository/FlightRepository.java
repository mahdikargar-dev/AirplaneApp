package com.Airplane.AirplaneApp.Repository;

import com.Airplane.AirplaneApp.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);

    List<Flight> findByDepartureAirport_LocationCityAndArrivalAirport_LocationCity(
            String departureCity, String arrivalCity);

    List<Flight> findByScheduledDepartureTimeBetween(
            LocalDateTime start, LocalDateTime end);

    @Query("SELECT DISTINCT f.departureAirport.locationCity FROM Flight f")
    List<String> findDistinctDepartureCities();

    @Query("SELECT DISTINCT f.arrivalAirport.locationCity FROM Flight f " +
            "WHERE f.departureAirport.locationCity = :departureCode")
    List<String> findDistinctArrivalCitiesByDepartureCode(@Param("departureCode") String departureCode);
}