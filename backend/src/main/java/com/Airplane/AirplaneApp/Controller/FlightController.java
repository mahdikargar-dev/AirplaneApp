        package com.Airplane.AirplaneApp.Controller;

        import com.Airplane.AirplaneApp.DTO.FlightDTO;
        import com.Airplane.AirplaneApp.Entity.FlightStatus;
        import com.Airplane.AirplaneApp.Service.FlightService;
        import jakarta.validation.Valid;
        import lombok.RequiredArgsConstructor;
        import org.springframework.format.annotation.DateTimeFormat;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.time.Duration;
        import java.time.LocalDateTime;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.stream.Collectors;

        @RestController
        @RequestMapping("/api/flights")
        @RequiredArgsConstructor

        public class FlightController {
            private final FlightService flightService;
            @PostMapping
            public ResponseEntity<FlightDTO> createFlight(@Valid @RequestBody FlightDTO flightDTO) {
                FlightDTO createdFlight = flightService.createFlight(flightDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
            }
            @GetMapping("/{id}/seats")
            public ResponseEntity<Integer> getAircraftSeats(@PathVariable Long id) {
                FlightDTO flight = flightService.getFlight(id);
                Integer seats = flight.getAircraft().getCapacity();
                return ResponseEntity.ok(seats);
            }
            @GetMapping("/{id}")
            public ResponseEntity<FlightDTO> getFlight(@PathVariable Long id) {
                return ResponseEntity.ok(flightService.getFlight(id));
            }

            @GetMapping("/number/{flightNumber}")
            public ResponseEntity<FlightDTO> getFlightByNumber(@PathVariable String flightNumber) {
                return ResponseEntity.ok(flightService.getFlightByNumber(flightNumber));
            }
            @GetMapping("/routes/{id}")
            public ResponseEntity<Map<String, Object>> getFlightDetailsForBooking(@PathVariable Long id) {
                FlightDTO flight = flightService.getFlight(id);
                Map<String, Object> response = new HashMap<>();
                response.put("flightNumber", flight.getFlightNumber());
                response.put("departureAirportName", flight.getDepartureAirport().getName());
                response.put("arrivalAirportName", flight.getArrivalAirport().getName());
                response.put("scheduledDepartureTime", flight.getScheduledDepartureTime());
                response.put("scheduledArrivalTime", flight.getScheduledArrivalTime());
                response.put("basePrice", flight.getBasePrice());
                response.put("capacity", flight.getAircraft().getCapacity());
                response.put("status", flight.getStatus());

                return ResponseEntity.ok(response);
            }

            @GetMapping("/search")
            public ResponseEntity<List<Map<String, Object>>> searchFlights(
                    @RequestParam String departureCode, // Keep parameter name same for backward compatibility
                    @RequestParam String arrivalCity) {
                List<FlightDTO> flights = flightService.searchFlights(departureCode, arrivalCity); // Now passing city names
                List<Map<String, Object>> result = flights.stream()
                        .map(this::convertToSearchResult)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(result);
            }

            private Map<String, Object> convertToSearchResult(FlightDTO flight) {
                Map<String, Object> result = new HashMap<>();
                result.put("flightId", flight.getFlightId());
                result.put("flightNumber", flight.getFlightNumber());
                result.put("departureTime", flight.getScheduledDepartureTime());
                result.put("arrivalTime", flight.getScheduledArrivalTime());
                result.put("duration", Duration.between(
                        flight.getScheduledDepartureTime(),
                        flight.getScheduledArrivalTime()).toMinutes());
                result.put("status", flight.getStatus());
                result.put("basePrice", flight.getBasePrice());
                return result;
            }

            @GetMapping("/routes/origins")
            public ResponseEntity<List<String>> getOriginCities() {
                return ResponseEntity.ok(flightService.getOriginCities());
            }


            @GetMapping("/routes/destinations")
            public ResponseEntity<List<String>> getDestinationCities(@RequestParam String originCode) {
                return ResponseEntity.ok(flightService.getDestinationCities(originCode));
            }

            @GetMapping("/schedule")
            public ResponseEntity<List<FlightDTO>> getFlightSchedule(
                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
                return ResponseEntity.ok(flightService.getFlightSchedule(start, end));
            }

            @PatchMapping("/{id}/status")
            public ResponseEntity<FlightDTO> updateFlightStatus(
                    @PathVariable Long id,
                    @RequestParam FlightStatus status) {
                return ResponseEntity.ok(flightService.updateFlightStatus(id, status));
            }

            @ExceptionHandler(Exception.class)
            public ResponseEntity<Map<String, String>> handleExceptions(Exception e) {
                Map<String, String> response = new HashMap<>();
                response.put("error", e.getMessage());

                HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
                if (e instanceof IllegalArgumentException || e instanceof IllegalStateException) {
                    status = HttpStatus.BAD_REQUEST;
                }

                return ResponseEntity.status(status).body(response);
            }
        }