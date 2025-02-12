package com.Airplane.AirplaneApp.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDTO {
    private Long id;
    private Long flightId;
    private String seatNumber;
    private Integer rowNumber;
    private String columnLetter;
    private String position;
    private String seatType;
    private Boolean available;
    private Boolean booked;
}
