package com.Airplane.AirplaneApp.Repository;

import com.Airplane.AirplaneApp.Entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    boolean existsByNationalCode(String nationalCode);
    boolean existsByPassportNumber(String passportNumber);
}