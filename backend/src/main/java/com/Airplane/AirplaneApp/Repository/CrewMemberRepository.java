package com.Airplane.AirplaneApp.Repository;

import com.Airplane.AirplaneApp.Entity.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
    Optional<CrewMember> findByEmployeeId(String employeeId);
    boolean existsByEmployeeId(String employeeId);
}