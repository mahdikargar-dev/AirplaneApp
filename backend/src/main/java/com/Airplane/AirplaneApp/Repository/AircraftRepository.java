package com.Airplane.AirplaneApp.Repository;

import com.Airplane.AirplaneApp.Entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    List<Aircraft> findByStatus(String status);
}