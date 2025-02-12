package com.Airplane.AirplaneApp.Repository;

import com.Airplane.AirplaneApp.Entity.CrewType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrewTypeRepository extends JpaRepository<CrewType, Long> {
    Optional<CrewType> findByCode(String code);
    boolean existsByCode(String code);
}