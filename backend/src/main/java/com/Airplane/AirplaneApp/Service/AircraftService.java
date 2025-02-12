package com.Airplane.AirplaneApp.Service;

import com.Airplane.AirplaneApp.DTO.AircraftDTO;
import java.util.List;

public interface AircraftService {
    AircraftDTO addAircraft(AircraftDTO aircraft);
    List<AircraftDTO> getAllAircraft();
    AircraftDTO getAircraftById(Long id);
    AircraftDTO updateAircraft(AircraftDTO aircraft); // ✅ این متد باید وجود داشته باشد
    void deleteAircraft(Long id);
    List<AircraftDTO> getAvailableAircraft();
}

