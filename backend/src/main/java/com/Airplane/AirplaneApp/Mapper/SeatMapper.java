package com.Airplane.AirplaneApp.Mapper;

import com.Airplane.AirplaneApp.DTO.SeatDTO;
import com.Airplane.AirplaneApp.Entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    @Mapping(source = "flight.flightId", target = "flightId")
    SeatDTO toDTO(Seat seat);

    @Mapping(source = "flightId", target = "flight.flightId")
    Seat toEntity(SeatDTO seatDTO);

    List<SeatDTO> toDTOList(List<Seat> seats);
    List<Seat> toEntityList(List<SeatDTO> seatDTOs);
}