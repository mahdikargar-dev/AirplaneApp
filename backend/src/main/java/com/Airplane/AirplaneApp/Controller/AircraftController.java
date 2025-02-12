    package com.Airplane.AirplaneApp.Controller;

    import com.Airplane.AirplaneApp.DTO.AircraftDTO;
    import com.Airplane.AirplaneApp.Service.AircraftService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import lombok.AllArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("api/aircraft")
    @AllArgsConstructor
    @Tag(name = "ثبت هواپیمای جدید", description = "مدیریت هواپیما")
    public class AircraftController {

        private final AircraftService aircraftService;

        @PostMapping("addAircraft")
        @Operation(summary = "ثبت هواپیما", description = "ثبت با دیتای جدید")
        public ResponseEntity<AircraftDTO> addAircraft(@RequestBody AircraftDTO aircraftDTO) {
            AircraftDTO savedAircraftDTO = aircraftService.addAircraft(aircraftDTO);
            return new ResponseEntity<>(savedAircraftDTO, HttpStatus.CREATED);
        }

        @GetMapping("listAll")
        @Operation(summary = "نمایش تمامی هواپیما ها", description = "Retrieve a list of all aircraft")
        public ResponseEntity<List<AircraftDTO>> getAllAircraft() {
            List<AircraftDTO> allAircraft = aircraftService.getAllAircraft();
            return new ResponseEntity<>(allAircraft, HttpStatus.OK);
        }

        @GetMapping("available")
        @Operation(summary = "نمایش هواپیمای موجود", description = "Retrieve a list of all available aircraft")
        public ResponseEntity<List<AircraftDTO>> getAvailableAircraft() {
            List<AircraftDTO> availableAircraft = aircraftService.getAvailableAircraft();
            return new ResponseEntity<>(availableAircraft, HttpStatus.OK);
        }

        @GetMapping("{id}")
        @Operation(summary = "دریافت هواپیما با ID", description = "Retrieve an aircraft by its ID")
        public ResponseEntity<AircraftDTO> getAircraftById(@PathVariable("id") Long aircraftId) {
            AircraftDTO aircraftDTO = aircraftService.getAircraftById(aircraftId);
            return new ResponseEntity<>(aircraftDTO, HttpStatus.OK);
        }

        @PutMapping("updateAircraft/{id}")
        @Operation(summary = "Update an aircraft", description = "Update an existing aircraft with the provided details")
        public ResponseEntity<AircraftDTO> updateAircraft(@PathVariable("id") Long aircraftId,
                                                          @RequestBody AircraftDTO aircraftDTO) {
            aircraftDTO.setAircraftId(aircraftId);
            AircraftDTO updatedAircraft = aircraftService.updateAircraft(aircraftDTO);
            return new ResponseEntity<>(updatedAircraft, HttpStatus.OK);
        }

        @DeleteMapping("deleteAircraft/{id}")
        @Operation(summary = "Delete an aircraft", description = "Delete an aircraft by its ID")
        public ResponseEntity<Void> deleteAircraft(@PathVariable("id") Long aircraftId) {
            aircraftService.deleteAircraft(aircraftId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }