package com.Airplane.AirplaneApp.Repository;

import com.Airplane.AirplaneApp.Entity.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CrewRepository extends JpaRepository<CrewMember, Long> {
    List<CrewMember> findByCrewType_CodeAndStatus(String crewTypeCode, String status);
    CrewMember findByEmployeeId(String employeeId);
    List<CrewMember> findByStatus(String status);
}