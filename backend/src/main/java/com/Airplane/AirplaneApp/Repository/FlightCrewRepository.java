package com.Airplane.AirplaneApp.Repository;

import com.Airplane.AirplaneApp.Entity.FlightCrew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FlightCrewRepository extends JpaRepository<FlightCrew, Long> {
    Optional<FlightCrew> findByFlight_FlightId(Long flightId);
}
