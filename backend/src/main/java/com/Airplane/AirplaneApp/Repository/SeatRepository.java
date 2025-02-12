    package com.Airplane.AirplaneApp.Repository;
 import com.Airplane.AirplaneApp.Entity.Seat;
    import jakarta.persistence.LockModeType;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Lock;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.List;
    import java.util.Optional;
    public interface SeatRepository extends JpaRepository<Seat, Long> {
        @Query("SELECT s FROM Seat s WHERE s.flight.flightId = :flightId")
        List<Seat> findByFlightId(@Param("flightId") Long flightId);

        @Lock(LockModeType.OPTIMISTIC)
        @Query("SELECT s FROM Seat s WHERE s.id = :id")
        Optional<Seat> findById(@Param("id") Long id);
    }
