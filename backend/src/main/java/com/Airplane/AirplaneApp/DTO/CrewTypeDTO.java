package com.Airplane.AirplaneApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewTypeDTO {
    private Long crewTypeId;
    private String code;
    private String name;
    private String category;
    private String description;
    private Integer requiredHoursOfRest;
    private Integer maxFlightHoursPerMonth;
}
