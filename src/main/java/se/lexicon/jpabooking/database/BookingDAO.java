package se.lexicon.jpabooking.database;

import se.lexicon.jpabooking.model.entity.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingDAO extends DAOGenericCRUD<Booking, String> {
    List<Booking> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Booking> findByDateTimeBefore(LocalDateTime end);
    List<Booking> findByDateTimeAfter(LocalDateTime start);
    List<Booking> findByAdministratorId(String administratorId);
    List<Booking> findByVaccineType(String vaccineType);
    List<Booking> findByVacantStatus(boolean vacantStatus);
    List<Booking> findAvailableTimesInCity(String city);
}
