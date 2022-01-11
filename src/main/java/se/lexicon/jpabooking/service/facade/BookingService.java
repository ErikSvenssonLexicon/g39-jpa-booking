package se.lexicon.jpabooking.service.facade;

import se.lexicon.jpabooking.model.dto.form.BookingForm;
import se.lexicon.jpabooking.model.dto.view.BookingDTO;
import se.lexicon.jpabooking.service.entity.GenericEntityService;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService extends GenericEntityService<BookingDTO, BookingForm> {
    List<BookingDTO> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<BookingDTO> findByDateTimeBefore(LocalDateTime end);
    List<BookingDTO> findByDateTimeAfter(LocalDateTime start);
    List<BookingDTO> findByAdministratorId(String administratorId);
    List<BookingDTO> findByVaccineType(String vaccineType);
    List<BookingDTO> findByVacantStatus(boolean vacantStatus);
    List<BookingDTO> findAvailableTimesInCity(String city);
}
