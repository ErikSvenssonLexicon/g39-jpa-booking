package se.lexicon.jpabooking.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.dto.form.BookingForm;
import se.lexicon.jpabooking.model.dto.view.BookingDTO;
import se.lexicon.jpabooking.service.entity.BookingEntityService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService{

    private final BookingEntityService bookingEntityService;
    private final DTOService dtoService;

    @Autowired
    public BookingServiceImpl(BookingEntityService bookingEntityService, DTOService dtoService) {
        this.bookingEntityService = bookingEntityService;
        this.dtoService = dtoService;
    }

    @Override
    public BookingDTO create(BookingForm bookingForm) {
        return dtoService.toFullBookingDTO(bookingEntityService.create(bookingForm));
    }

    @Override
    public BookingDTO findById(String id) {
        return dtoService.toFullBookingDTO(bookingEntityService.findById(id));
    }

    @Override
    public List<BookingDTO> findAll() {
        return bookingEntityService.findAll().stream()
                .map(dtoService::toSmallBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO update(String id, BookingForm bookingForm) {
        return dtoService.toFullBookingDTO(bookingEntityService.update(id, bookingForm));
    }

    @Override
    public void delete(String id) {
        bookingEntityService.delete(id);
    }

    @Override
    public List<BookingDTO> findByDateTimeBetween(LocalDateTime start, LocalDateTime end) {
        return bookingEntityService.findByDateTimeBetween(start, end).stream()
                .map(dtoService::toSmallBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> findByDateTimeBefore(LocalDateTime end) {
        return bookingEntityService.findByDateTimeBefore(end).stream()
                .map(dtoService::toSmallBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> findByDateTimeAfter(LocalDateTime start) {
        return bookingEntityService.findByDateTimeAfter(start).stream()
                .map(dtoService::toSmallBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> findByAdministratorId(String administratorId) {
        return bookingEntityService.findByAdministratorId(administratorId).stream()
                .map(dtoService::toSmallBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> findByVaccineType(String vaccineType) {
        return bookingEntityService.findByVaccineType(vaccineType).stream()
                .map(dtoService::toSmallBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> findByVacantStatus(boolean vacantStatus) {
        return bookingEntityService.findByVacantStatus(vacantStatus).stream()
                .map(dtoService::toSmallBookingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> findAvailableTimesInCity(String city) {
        return bookingEntityService.findAvailableTimesInCity(city).stream()
                .map(dtoService::toSmallBookingDTO)
                .collect(Collectors.toList());
    }
}
