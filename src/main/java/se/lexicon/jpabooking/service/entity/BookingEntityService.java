package se.lexicon.jpabooking.service.entity;

import se.lexicon.jpabooking.model.dto.form.BookingForm;
import se.lexicon.jpabooking.model.entity.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingEntityService extends GenericEntityService<Booking, BookingForm>{
    List<Booking> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Booking> findByDateTimeBefore(LocalDateTime end);
    List<Booking> findByDateTimeAfter(LocalDateTime start);
    List<Booking> findByAdministratorId(String administratorId);
    List<Booking> findByVaccineType(String vaccineType);
    List<Booking> findByVacantStatus(boolean vacantStatus);
    List<Booking> findAvailableTimesInCity(String city);
}
