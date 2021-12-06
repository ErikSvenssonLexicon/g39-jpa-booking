package se.lexicon.jpabooking.database.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.lexicon.jpabooking.model.entity.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingDAO extends JpaRepository<Booking, String> {

    List<Booking> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Booking> findByDateTimeBefore(LocalDateTime end);
    List<Booking> findByDateTimeAfter(LocalDateTime start);
    List<Booking> findByAdministratorId(String administratorId);
    List<Booking> findByVaccineType(String vaccineType);
    List<Booking> findByVacant(boolean vacant);
    @Query("SELECT b FROM Booking b WHERE UPPER(b.premises.address.city) = UPPER(:city) AND b.vacant = true")
    List<Booking> findByAvailableTimesInCity(String city);


}
