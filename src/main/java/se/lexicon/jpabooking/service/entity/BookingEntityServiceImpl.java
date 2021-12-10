package se.lexicon.jpabooking.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.database.BookingDAO;
import se.lexicon.jpabooking.exception.AppResourceNotFoundException;
import se.lexicon.jpabooking.model.dto.form.BookingForm;
import se.lexicon.jpabooking.model.entity.Booking;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BookingEntityServiceImpl implements BookingEntityService{

    private final BookingDAO bookingDAO;

    @Autowired
    public BookingEntityServiceImpl(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Override
    public List<Booking> findByDateTimeBetween(LocalDateTime start, LocalDateTime end) {
        return bookingDAO.findByDateTimeBetween(start,end);
    }

    @Override
    public List<Booking> findByDateTimeBefore(LocalDateTime end) {
        return bookingDAO.findByDateTimeBefore(end);
    }

    @Override
    public List<Booking> findByDateTimeAfter(LocalDateTime start) {
        return bookingDAO.findByDateTimeAfter(start);
    }

    @Override
    public List<Booking> findByAdministratorId(String administratorId) {
        return bookingDAO.findByAdministratorId(administratorId);
    }

    @Override
    public List<Booking> findByVaccineType(String vaccineType) {
        return bookingDAO.findByVaccineType(vaccineType);
    }

    @Override
    public List<Booking> findByVacantStatus(boolean vacantStatus) {
        return bookingDAO.findByVacantStatus(vacantStatus);
    }

    @Override
    public List<Booking> findAvailableTimesInCity(String city) {
        return bookingDAO.findAvailableTimesInCity(city);
    }

    @Override
    public Booking create(BookingForm bookingForm) {
        if(bookingForm == null) throw new IllegalArgumentException("BookingForm bookingForm was null");
        Booking booking = new Booking();
        booking.setDateTime(bookingForm.getDateTime());
        booking.setPrice(bookingForm.getPrice());
        booking.setAdministratorId(bookingForm.getAdministratorId());
        booking.setVaccineType(bookingForm.getVaccineType());
        booking.setVacant(true);
        return bookingDAO.save(booking);
    }

    @Override
    public Booking findById(String id) {
        return bookingDAO.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find booking with id " + id));
    }

    @Override
    public List<Booking> findAll() {
        return bookingDAO.findAll();
    }

    @Override
    public Booking update(String id, BookingForm bookingForm) {
        Booking booking = findById(id);
        if(!id.equals(bookingForm.getId())){
            throw new IllegalArgumentException("id didn't match found " + BookingForm.class.getName()+".id");
        }
        booking.setDateTime(bookingForm.getDateTime());
        booking.setPrice(bookingForm.getPrice());
        booking.setAdministratorId(bookingForm.getAdministratorId());
        booking.setVaccineType(bookingForm.getVaccineType());
        booking.setVacant(bookingForm.isVacant());
        booking = bookingDAO.save(booking);
        return booking;
    }

    @Override
    public void delete(String id) {
        Booking booking = findById(id);
        booking.setPatient(null);
        booking.setPremises(null);
        bookingDAO.delete(id);
    }
}
