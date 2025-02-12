package com.Airplane.AirplaneApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightCrewDTO {
    private Long flightCrewId;
    private Long flightId;
    private Set<Long> crewMemberIds;
    private String status;
    private String remarks;
}