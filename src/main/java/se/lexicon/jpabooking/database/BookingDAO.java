package se.lexicon.jpabooking.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.jpabooking.model.entity.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingDAO extends JpaRepository<Booking, String> {
    @Query("SELECT b FROM Booking b WHERE b.dateTime BETWEEN :startTime AND :endTime")
    List<Booking> findByDateTimeBetween(@Param("startTime") LocalDateTime start, @Param("endTime") LocalDateTime end);

    @Query("SELECT b FROM Booking b WHERE b.dateTime < :timeEnd")
    List<Booking> findByDateTimeBefore(@Param("timeEnd") LocalDateTime end);

    @Query("SELECT b FROM Booking b WHERE b.dateTime > :timeStart")
    List<Booking> findByDateTimeAfter(@Param("timeStart") LocalDateTime start);

    @Query("SELECT b FROM Booking b WHERE b.administratorId = :administratorId")
    List<Booking> findByAdministratorId(@Param("administratorId") String administratorId);

    @Query("SELECT b FROM Booking b WHERE UPPER(b.vaccineType) = UPPER(:vaccineType)")
    List<Booking> findByVaccineType(@Param("vaccineType") String vaccineType);

    @Query("SELECT b FROM Booking b WHERE b.vacant = :vacant")
    List<Booking> findByVacantStatus(@Param("vacant") boolean vacantStatus);

    @Query("SELECT b FROM Booking b WHERE UPPER(b.premises.address.city) = UPPER(:city) AND b.vacant = true")
    List<Booking> findAvailableTimesInCity(@Param("city") String city);
}
